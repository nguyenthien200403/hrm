package com.example.hrm.service;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.model.Attendance;
import com.example.hrm.model.Employee;
import com.example.hrm.model.TimeTracker;
import com.example.hrm.projection.AttendanceProjection;
import com.example.hrm.repository.AttendanceRepository;
import com.example.hrm.repository.EmployeeRepository;
import com.example.hrm.repository.NetworkRepository;
import com.example.hrm.repository.TimeTrackerRepository;
import com.example.hrm.request.AttendanceRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final NetworkRepository networkRepository;
    private final EmployeeRepository employeeRepository;
    private final TimeTrackerRepository timeTrackerRepository;

    //Kiểm tra nhân viên đã chấm công - Nếu đã chấm công thì trả về id ngày hôm đó
    @Transactional(readOnly = true)
    public GeneralResponse<?> checkDateWork(String id, LocalDate date){
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if(dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY){
            return new GeneralResponse<>(HttpStatus.FORBIDDEN.value(), "Weekend - cannot Check-in.", null);
        }

        TimeTracker timeTracker = timeTrackerRepository.findById(1L)
                .orElseThrow(() -> new IllegalStateException("TimeTracker not found"));
        LocalTime timeNow = LocalTime.now();

        if(timeNow.isAfter(timeTracker.getEndTime())){
            return new GeneralResponse<>(HttpStatus.FORBIDDEN.value(), "The workday is over.", null);
        }

        List<AttendanceProjection> list = attendanceRepository.findByIdEmployeeAndDateWork(id, date);

        if(!list.isEmpty()){
            LocalTime end = list.get(0).getTimeOut();
            if(end != null){
                return new GeneralResponse<>(HttpStatus.BAD_REQUEST.value(), "You have completed Check-out", null);
            }

            String idAttendance = list.get(0).getId();
            return new GeneralResponse<>(HttpStatus.CONFLICT.value(), "You have completed Check-in", idAttendance);

        }

        return new GeneralResponse<>(HttpStatus.OK.value(), "You can Check-in.", null);
    }

    //Check-in
    public GeneralResponse<?> checkIn(String idEmployee, AttendanceRequest request, String msg){
        boolean ipPublic = networkRepository.existsByIpPublic(request.getIpPublic());
        if(!ipPublic){
            return new GeneralResponse<>(HttpStatus.BAD_REQUEST.value(), "Incorrect Internal Network", null);
        }
        if(!request.getFaceId()){
            return new GeneralResponse<>(HttpStatus.BAD_REQUEST.value(), "Incorrect Facial Recognition", null);
        }

        return create(idEmployee, request, msg);

    }

    //Check-out
    public GeneralResponse<?> checkOut(String id, LocalTime endRequest){
        Optional<Attendance> findResult = attendanceRepository.findById(id);
        if(findResult.isEmpty()){
            return new GeneralResponse<>(HttpStatus.BAD_REQUEST.value(), "You must Check-in", null);
        }
        Attendance attendance = findResult.get();

        if(attendance.getTimeOut() != null){
            return new GeneralResponse<>(HttpStatus.BAD_REQUEST.value(), "You have completed Check-out", null);
        }

        LocalTime end = setTimeEnd(endRequest);
        attendance.setTimeOut(end);
        BigDecimal totalTime = totalTime(attendance.getTimeIn(), end);
        attendance.setTotalTime(totalTime);

        attendanceRepository.save(attendance);
        return new GeneralResponse<>(HttpStatus.OK.value(), "Check-Out Successful", totalTime);
    }

    private LocalTime setTimeEnd(LocalTime end) {
        TimeTracker timeTracker = timeTrackerRepository.findById(1L)
                .orElseThrow(() -> new IllegalStateException("TimeTracker not found"));

        LocalTime endTracker = timeTracker.getEndTime();
        return (end.isAfter(endTracker) || end.equals(endTracker)) ? endTracker : end;
    }



    //Create
    public GeneralResponse<?> create(String idEmployee, AttendanceRequest request, String msg){
        Optional<Employee> findResult = employeeRepository.findById(idEmployee);
        if(findResult.isEmpty()){
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(), "Not Found Employee with Id: " + idEmployee, null);
        }

        Employee employee = findResult.get();

        BigDecimal totalTime = totalTime(request.getTimeIn(), request.getTimeOut());

        var attendance = Attendance.builder()
                .id(employee.getId() + "-" + request.getDateWork())
                .dateWork(request.getDateWork())
                .timeIn(request.getTimeIn())
                .timeOut(request.getTimeOut())
                .totalTime(totalTime)
                .note(request.getNote() == null ? "" : request.getNote())
                .idEmployee(employee.getId())
                .build();
        attendanceRepository.save(attendance);
        return new GeneralResponse<>(HttpStatus.OK.value(), msg, null);

    }

    private BigDecimal totalTime(LocalTime start, LocalTime end){
        if(end == null || start == null){
            return BigDecimal.ZERO;
        }
        long minutes = Duration.between(start, end).toMinutes();
        // Ví dụ: trừ đi 120 phút nghỉ trưa
        long adjusted = minutes - 120;
        if(adjusted < 0) adjusted = 0;
        return BigDecimal.valueOf(adjusted / 60.0); // giờ dạng thập phân
    }

    //AutoCheckout: cron job
    @Scheduled(cron = "0 0 20 * * MON-FRI") // chạy 18:00 từ thứ 2 đến thứ 6
    public void autoCheckOut() {
       LocalTime endTime = timeTrackerRepository.findById(1L)
                           .map(TimeTracker :: getEndTime)
                           .orElse(LocalTime.of(19,0));
        List<Attendance> records = attendanceRepository.findByTimeOutIsNull();
        for (Attendance attendance : records) {
            attendance.setTimeOut(endTime);
            attendance.setTotalTime(totalTime(attendance.getTimeIn(), endTime));
            attendanceRepository.save(attendance);
        }
    }



    //Update
    public GeneralResponse<?> update(String id, AttendanceRequest request){
        Optional<Attendance> findResult = attendanceRepository.findById(id);
        if(findResult.isEmpty()){
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(), "Not Found Working Data with Id: " + id, null);
        }
        Attendance attendance = findResult.get();

        attendance.setTimeIn(request.getTimeIn());
        attendance.setTimeOut(request.getTimeOut());
        attendance.setTotalTime(totalTime(request.getTimeIn(), request.getTimeOut()));
        attendance.setNote(request.getNote());

        attendanceRepository.save(attendance);

        return new GeneralResponse<>(HttpStatus.OK.value(), "Successful Update: " + id, null);
    }


    //Đếm số lượng nhân viên chấm công theo ngày
    public GeneralResponse<?> countAttendancesByDateWork(){
        LocalDate date = LocalDate.now();
        int amount = attendanceRepository.countAttendancesByDateWork(date);
        return new GeneralResponse<>(HttpStatus.OK.value(), "Amount Attendances", amount);
    }

    public GeneralResponse<?> countLateAttendancesByDateWork(){
        LocalDate date = LocalDate.now();
        int amount = attendanceRepository.countLateAttendancesByDateWork(date);
        return new GeneralResponse<>(HttpStatus.OK.value(), "Amount Late Attendances", amount);
    }

    //Đếm số lượng nhân viên đi trễ

    //hiển thị danh sách bảng công theo nhân viên và theo tháng
    @Transactional(readOnly = true)
    public GeneralResponse<?> getAllByIdEmployeeAndDateWork(String id){
        LocalDate date = LocalDate.now();
        int mount = date.getMonthValue();
        int year = date.getYear();
        List<AttendanceProjection> list = attendanceRepository.findAllByIdEmployeeAndDateWork(id, mount, year);
        if(list.isEmpty()){
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(), "Not Found Working Data for Employee Id: " + id, null);
        }
        return new GeneralResponse<>(HttpStatus.OK.value(), "Working data for: " + mount + "/" + year, list);
    }

    //getByIdAndDateWork
    @Transactional(readOnly = true)
    public GeneralResponse<?> searchByIdEmployeeAndDateWork(String id, LocalDate date){
       List<AttendanceProjection> list = attendanceRepository.findByIdEmployeeAndDateWork(id, date);
        if(list.isEmpty()){
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(), "Not Found Working Data for Employee Id: " + id, null);
        }
        return new GeneralResponse<>(HttpStatus.OK.value(), "Working data for: ", list);
    }
}
