package com.example.hrm.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;


public class EmployeeDTO {

    @NotBlank(message = "NOT NULL")
    private String name;

    @NotNull(message = "NOT NULL")
    private Boolean gender;

    @NotNull(message = "NOT NULL")
    private LocalDate birthDate;

    @NotBlank(message = "NOT NULL")
    @Email(message = "Email is not in the correct format")
    private String email;

    @NotBlank(message = "NOT NULL")
    private String nation;

    @NotBlank(message = "NOT NULL")
    private String ethnic;

    @NotBlank(message = "NOT NULL")
    @Pattern(regexp = "\\d{10}", message = "LENGTH 10")
    private String phone;

    @NotBlank(message = "NOT NULL")
    @Pattern(regexp = "\\d{12}", message = "LENGTH 12")
    private String identification;

    @NotBlank(message = "NOT NULL")
    private String issuePlace;

    @NotNull(message = "NOT NULL")
    private LocalDate issueDate;

    @NotBlank(message = "NOT NULL")
    private String tempAddress;

    @NotBlank(message = "NOT NULL")
    private String permanent ;


    private String habit;

    @NotBlank(message = "NOT NULL")
    private String statusMarital;


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

    public void setIssueDate(LocalDate issueDay) {
        this.issueDate = issueDay;
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

}
