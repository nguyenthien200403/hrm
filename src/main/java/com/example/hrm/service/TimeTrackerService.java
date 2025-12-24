package com.example.hrm.service;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.dto.TimeTrackerDTO;
import com.example.hrm.model.TimeTracker;
import com.example.hrm.repository.TimeTrackerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.time.LocalTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TimeTrackerService {
    private final TimeTrackerRepository repository;

    public GeneralResponse<?> getTimeTracker(){
        Optional<TimeTracker> findResult = repository.findById(1L);

        if(findResult.isEmpty()){
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(), "Working hours have not been configured yet.", null);
        }
        TimeTrackerDTO dto = new TimeTrackerDTO(findResult.get());
        return new GeneralResponse<>(HttpStatus.OK.value(), "Working hours", dto);
    }

    public GeneralResponse<?> setupTimeTracker(TimeTrackerDTO dto) {
        TimeTracker timeTracker = repository.findById(1L)
                .map(existing -> {
                    existing.setStartTime(dto.getStart());
                    existing.setEndTime(dto.getEnd());
                    existing.setNote(dto.getNote());
                    return existing;
                })
                .orElseGet(() -> TimeTracker.builder()
                        .id(1L)
                        .startTime(dto.getStart())
                        .endTime(dto.getEnd())
                        .note(dto.getNote())
                        .build()
                );

        repository.save(timeTracker);

        return new GeneralResponse<>(HttpStatus.OK.value(),"Setup Successful TimeTracker", null);
    }


}
