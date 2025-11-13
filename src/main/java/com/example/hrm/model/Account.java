package com.example.hrm.model;

import jakarta.persistence.*;


import java.util.Date;

@Entity
@Table(name="TaiKhoan")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tai_khoan")
    private Long id;

    @Column(name = "ten_dang_nhap")
    private String nameAccount;

    @Column(name = "mat_khau")
    private String password;

    @Column(name = "ngay_tao")
    private Date createByDate;

    @Column(name = "trang_thai")
    private Boolean active;

    @Column(name = "dang_nhap_lan_dau")
    private Boolean firstLogin;

    @Column(name = "id_vai_tro")
    private String role;

    @Column(name = "id_nhan_vien")
    private String idEmployee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameAccount() {
        return nameAccount;
    }

    public void setNameAccount(String nameAccount) {
        this.nameAccount = nameAccount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateByDate() {
        return createByDate;
    }

    public void setCreateByDate(Date createByDate) {
        this.createByDate = createByDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(Boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(String idEmployee) {
        this.idEmployee = idEmployee;
    }

    @Override
    public String toString() {
        return "Account{" +
                "nameAccount='" + nameAccount + '\'' +
                ", active=" + active +
                ", role='" + role + '\'' +
                ", idEmployee='" + idEmployee + '\'' +
                '}';
    }
}
