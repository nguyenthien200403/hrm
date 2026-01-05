package com.example.hrm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class IncomeService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void calculateIncomeForAllEmployees(int month, int year){
        jdbcTemplate.update("EXEC sp_CalculateIncomeAllEmployeesByMonth @thang=?, @nam=?", month, year);
    }
}
