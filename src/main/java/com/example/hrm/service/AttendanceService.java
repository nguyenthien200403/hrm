package com.example.hrm.service;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.dto.AttendanceDTO;
import com.example.hrm.dto.CountAttendanceDTO;
import com.example.hrm.mapper.AttendanceMapper;
import com.example.hrm.model.Attendance;
import com.example.hrm.model.Employee;
import com.example.hrm.model.TimeTracker;
import com.example.hrm.projection.AttendanceProjection;
import com.example.hrm.repository.*;
import com.example.hrm.request.AttendanceRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final NetworkRepository networkRepository;
    private final EmployeeRepository employeeRepository;
    private final TimeTrackerRepository timeTrackerRepository;
    private final DetailRequirementRepository detailRequirementRepository;
    private final AttendanceMapper attendanceMapper;

    @Value("${time.break}")
    private int timeBreak;

    @Value("${time.threshold}")
    private int timeThreshold;

    @Value("${work.time}")
    private int WORKTIME;

    @Value("${time.skip}")
    private int timeSkip;

    //Kiểm tra nhân viên đã chấm công - Nếu đã chấm công thì trả về id ngày hôm đó
    @Transactional(readOnly = true)
    public GeneralResponse<?> checkDateWork(String id, LocalDate date){
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if(dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY){
            int resultCheck = detailRequirementRepository.checkApprovedOvertime(id, date);
            if(resultCheck == 0){
                return new GeneralResponse<>(HttpStatus.FORBIDDEN.value(), "Weekend - cannot Check-in.", null);
            }
        }

        TimeTracker timeTracker = timeTrackerRepository.findById(1L)
                .orElseThrow(() -> new IllegalStateException("TimeTracker not found"));

        LocalTime timeNow = LocalTime.now();

        // Lùi giờ bắt đầu về 3 tiếng
        LocalTime adjustedStartTime = timeTracker.getStartTime().minusHours(timeSkip);

        // Cộng thêm 3 tiếng sau giờ tan ca
        LocalTime adjustedEndTime = timeTracker.getEndTime().plusHours(timeSkip);

        // Kiểm tra ngoài giờ làm (trước adjustedStartTime hoặc sau endTime)
        if (timeNow.isBefore(adjustedStartTime) || timeNow.isAfter(adjustedEndTime)) {
            return new GeneralResponse<>(HttpStatus.FORBIDDEN.value(), "The workday is over", null);
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


        attendance.setTimeOut(endRequest);
        BigDecimal totalTime = totalTime(attendance.getTimeIn(), endRequest);
        attendance.setTotalTime(totalTime);

        attendanceRepository.save(attendance);
        return new GeneralResponse<>(HttpStatus.OK.value(), "Check-Out Successful", totalTime);
    }

    private LocalTime setTimeEnd(LocalTime endRequest) {
        TimeTracker timeTracker = timeTrackerRepository.findById(1L)
                .orElseThrow(() -> new IllegalStateException("TimeTracker not found"));

        LocalTime endTracker = timeTracker.getEndTime();
        return endRequest.isAfter(endTracker) ? endTracker : endRequest;
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
                .employee(employee)
                .build();
        attendanceRepository.save(attendance);
        return new GeneralResponse<>(HttpStatus.OK.value(), msg, null);

    }

    private BigDecimal totalTime(LocalTime start, LocalTime endRequest){
        if(endRequest == null || start == null){
            return BigDecimal.ZERO;
        }

        LocalTime end = setTimeEnd(endRequest);

        long minutes = Duration.between(start, end).toMinutes();
        // Ví dụ: trừ đi 120 phút nghỉ trưa
        long adjusted = minutes;
        if (minutes >= WORKTIME) {
            adjusted -= timeBreak;
        }
        if (adjusted < 0) adjusted = 0;
        return BigDecimal.valueOf(adjusted / 60.0); // giờ dạng thập phân
    }

    //AutoCheckout: cron job
    public void autoCheckOut() {
        LocalTime endTime = timeTrackerRepository.findById(1L)
                .map(TimeTracker::getEndTime) .orElse(LocalTime.of(19,0));
        List<Attendance> records = attendanceRepository.findByTimeOutIsNull();
        for (Attendance attendance : records) {
            attendance.setTimeOut(endTime);
            attendance.setTotalTime(totalTime(attendance.getTimeIn(), endTime));
        } attendanceRepository.saveAll(records);
    }

    public void autoUpdateStatus() {
        List<Attendance> records = attendanceRepository.findByStatusIsNull();
        if (records.isEmpty()) return;
        TimeTracker tracker = timeTrackerRepository.findById(1L)
                .orElseThrow(() -> new IllegalStateException("TimeTracker not found"));
        LocalTime shiftStart = tracker.getStartTime();
        LocalTime shiftEnd = tracker.getEndTime();
        for (Attendance att : records) {
            if (att.getTimeIn() == null || att.getTimeOut() == null) {
                att.setStatus("Absent"); // hoặc Missing Punch
            } else {
                boolean late = att.getTimeIn().isAfter(shiftStart.plusMinutes(timeThreshold));
                boolean early = att.getTimeOut().isBefore(shiftEnd.minusMinutes(timeThreshold));
                if (late && early) att.setStatus("Đi trễ & Về sớm");
                else if (late) att.setStatus("Đi trễ");
                else if (early) att.setStatus("Về sớm");
                else att.setStatus("Đúng giờ");
            }
        } attendanceRepository.saveAll(records);
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

    //Đếm số lượng nhân viên đi trễ
    public GeneralResponse<?> countLateAttendancesByDateWork(){
        LocalDate date = LocalDate.now();
        int amount = attendanceRepository.countLateAttendancesByDateWork(date, timeThreshold);
        return new GeneralResponse<>(HttpStatus.OK.value(), "Amount Late Attendances", amount);
    }

    public GeneralResponse<?> countAttendancesByAdmin(){
        LocalDate date = LocalDate.now();
        int work = attendanceRepository.countAttendancesByDateWork(date);
        int late = attendanceRepository.countLateAttendancesByDateWork(date, timeThreshold);

        CountAttendanceDTO dto = new CountAttendanceDTO(work, late);

        return new GeneralResponse<>(HttpStatus.OK.value(), "Amount Attendances", dto);
    }


    public GeneralResponse<?> countAttendancesByManager(String employeeId){
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Not Found Employee with Id: " + employeeId));

        String departId = employee.getDepartment().getId();
        LocalDate date = LocalDate.now();
        int work = attendanceRepository.countWorkingByDateManager(date, departId);
        int late = attendanceRepository.countLateAttendancesByDateManager(date, timeThreshold, departId);

        CountAttendanceDTO dto = new CountAttendanceDTO(work, late);

        return new GeneralResponse<>(HttpStatus.OK.value(), "Amount Attendances by " + date, dto);
    }


    //hiển thị danh sách bảng công theo nhân viên và theo tháng
    @Transactional(readOnly = true)
    public GeneralResponse<?> getAllByIdEmployeeAndDateWork(String id, int month, int year){

        List<AttendanceProjection> list = attendanceRepository.findAllByIdEmployeeAndDateWork(id, month, year);

        if(list.isEmpty()){
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(), "Not Found Working Data for Employee Id: " + id, null);
        }

        return new GeneralResponse<>(HttpStatus.OK.value(), "Working data for: " + month + "/" + year, list);
    }

    //getByIdAndDateWork
    @Transactional(readOnly = true)
    public GeneralResponse<?> searchByIdEmployeeAndDateWork(String id, LocalDate date){
       List<AttendanceProjection> list = attendanceRepository.findByIdEmployeeAndDateWork(id, date);
        if(list.isEmpty()){
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(), "Not Found Working Data for Employee Id: " + id, null);
        }
        return new GeneralResponse<>(HttpStatus.OK.value(), "Working data for: " + id, list);
    }



    @Transactional(readOnly = true)
    public GeneralResponse<?> getAllAttendanceByManager(String id, int month, int year, String employeeName){
        List<Attendance> list = attendanceRepository.getAllAttendanceByManager(id, month, year, employeeName);
        if(list.isEmpty()){
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(), "Not Found Working Data for Employee Id: " + id, null);
        }

        List<AttendanceDTO> dto = list.stream().map(attendanceMapper :: toDTO).toList();
        return new GeneralResponse<>(HttpStatus.OK.value(), "Working data ", dto);
    }


    @Transactional(readOnly = true)
    public GeneralResponse<?> getAllAttendanceByAdmin(String id, int month, int year, String employeeName){
        List<Attendance> list = attendanceRepository.getAllAttendanceByAdmin(id, month, year, employeeName);
        if(list.isEmpty()){
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(), "Not Found Working Data for Employee Id: " + id, null);
        }

        List<AttendanceDTO> dto = list.stream().map(attendanceMapper :: toDTO).toList();
        return new GeneralResponse<>(HttpStatus.OK.value(), "Working data ", dto);
    }



}
