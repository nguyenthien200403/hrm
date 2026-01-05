package com.example.hrm.scheduler;

import com.example.hrm.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AttendanceScheduler {
    @Autowired
    private AttendanceService attendanceService;

    @Scheduled(cron = "0 0 21 * * MON-FRI")
    @Transactional
    public void autoCheckOutJob() {
        attendanceService.autoCheckOut();
    }

    // chạy mỗi ngày lúc 23h
    @Scheduled(cron = "0 0 23 * * ?")
    @Transactional public void autoUpdateStatusJob() {
        attendanceService.autoUpdateStatus();
    }
}
