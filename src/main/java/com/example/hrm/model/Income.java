package com.example.hrm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BangLuong")
public class Income {
    @Id
    @Column(name = "id_bang_luong")
    private String id;

    @Column(name = "thang")
    private int month;

    @Column(name ="nam")
    private int year;

    @Column(name ="ngay_tao")
    private LocalDateTime dateCreate;

    @Column(name ="luong_cong")
    private BigDecimal workSalary;

    @Column(name = "luong_tang_ca_ngay_thuong")
    private  BigDecimal overtimeDailySalary;

    @Column(name = "luong_tang_ca_ngay_nghi")
    private  BigDecimal overtimeWeekendSalary;

    @Column(name = "luong_tang_ca_ngay_le")
    private  BigDecimal overtimeHolidaySalary;

    @Column(name = "luong_phep")
    private  BigDecimal leaveSalary;

    @Column(name = "thuc_lanh")
    private BigDecimal totalSalary;

    @ManyToOne
    @JoinColumn(name = "id_nhan_vien")
    private Employee employee;

    @Override
    public String toString() {
        return "id='" + id + '\'' +
                ", month=" + month +
                ", year=" + year +
                ", dateCreate=" + dateCreate +
                ", workSalary=" + workSalary +
                ", overtimeDailySalary=" + overtimeDailySalary +
                ", overtimeWeekendSalary=" + overtimeWeekendSalary +
                ", overtimeHolidaySalary=" + overtimeHolidaySalary +
                ", leaveSalary=" + leaveSalary +
                ", totalSalary=" + totalSalary ;
    }
}
