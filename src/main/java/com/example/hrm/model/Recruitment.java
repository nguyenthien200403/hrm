package com.example.hrm.model;

import jakarta.persistence.*;


import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name="TuyenDung")
public class Recruitment {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "ho_ten")
    private String name;

    @Column(name = "ngay_tao")
    private LocalDate date;
    @Column(name = "trang_thai")
    private Boolean status = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Recruitment{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", status=" + status +
                '}';
    }
}
