package com.example.hrm.scheduler;

import com.example.hrm.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class IncomeScheduler {
    @Autowired
    private IncomeService service;


    @Scheduled(cron = "0 59 23 3 * ?")
    public void runMonthlyIncome() {
        LocalDate now = LocalDate.now();
        int currentMonth = now.getMonthValue();
        int currentYear = now.getYear(); // Tính cho tháng trước
        int payrollMonth; int payrollYear;

        if (currentMonth == 1) {
            payrollMonth = 12;
            payrollYear = currentYear - 1;
        }
        else {
            payrollMonth = currentMonth - 1; payrollYear = currentYear;
        }
        service.calculateIncomeForAllEmployees(payrollMonth, payrollYear);
        System.out.println("Income calculated and saved for all employees: " + payrollMonth + "/" + payrollYear);
    }
}
