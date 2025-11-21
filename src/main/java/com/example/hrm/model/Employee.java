package com.example.hrm.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;


@Entity
@Table(name = "NhanVien")
public class Employee {
    @Id
    @Column(name = "id_nhan_vien", unique = true)
    private String id;

    @Column(name = "ho_ten")
    private String name;

    @Column(name = "gioi_tinh")
    private Boolean gender;

    @Column(name = "ngay_sinh")
    private LocalDate birthDate;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "quoc_tich")
    private String nation;

    @Column(name = "dan_toc")
    private String ethnic;

    @Column(name = "so_dien_thoai", unique = true)
    private String phone;

    @Column(name = "chung_minh_thu", unique = true)
    private String identification;

    @Column(name = "noi_cap")
    private String issuePlace;

    @Column(name = "ngay_cap")
    private LocalDate issueDate;

    @Column(name = "tam_tru")
    private String tempAddress;

    @Column(name = "thuong_tru")
    private String permanent ;

    @Column(name = "so_thich")
    private String habit;

    @Column(name = "tinh_trang_hon_nhan")
    private String statusMarital;

    @Column(name = "muc_luong")
    private String salary;

    @Column(name = "trang_thai")// 0:nghỉ việc - 1:hoạt động - 2:đang đợi duyệt - 3:từ chối
    private String status = "2";

    @Column(name = "id_phong_ban")
    private String idDepart;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getEthnic() {
        return ethnic;
    }

    public void setEthnic(String ethnic) {
        this.ethnic = ethnic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getIssuePlace() {
        return issuePlace;
    }

    public void setIssuePlace(String issuePlace) {
        this.issuePlace = issuePlace;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public String getTempAddress() {
        return tempAddress;
    }

    public void setTempAddress(String tempAddress) {
        this.tempAddress = tempAddress;
    }

    public String getPermanent() {
        return permanent;
    }

    public void setPermanent(String permanent) {
        this.permanent = permanent;
    }

    public String getHabit() {
        return habit;
    }

    public void setHabit(String habit) {
        this.habit = habit;
    }

    public String getStatusMarital() {
        return statusMarital;
    }

    public void setStatusMarital(String statusMarital) {
        this.statusMarital = statusMarital;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdDepart() {
        return idDepart;
    }

    public void setIdDepart(String idDepart) {
        this.idDepart = idDepart;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", birthDate=" + birthDate +
                ", email='" + email + '\'' +
                ", nation='" + nation + '\'' +
                ", ethnic='" + ethnic + '\'' +
                ", phone='" + phone + '\'' +
                ", identification='" + identification + '\'' +
                ", issuePlace='" + issuePlace + '\'' +
                ", issueDay=" + issueDate +
                ", tempAddress='" + tempAddress + '\'' +
                ", permanent='" + permanent + '\'' +
                ", habit='" + habit + '\'' +
                ", statusMarital='" + statusMarital + '\'' +
                ", salary='" + salary + '\'' +
                ", status='" + status + '\'' +
                ", idDepart='" + idDepart + '\'' +
                '}';
    }
}

