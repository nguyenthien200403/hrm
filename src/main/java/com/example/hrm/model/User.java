package com.example.hrm.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.time.LocalDate;
import java.util.Collection;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TaiKhoan")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tai_khoan")
    private Long id;

    @Column(name = "ten_dang_nhap", unique = true)
    @NotBlank(message = "Not Null")
    private String name;

    @Column(name = "mat_khau")
    @NotBlank(message = "Not Null")
    private String password;

    @Column(name = "ngay_tao")
    private LocalDate date = LocalDate.now();

    @Column(name = "trang_thai")
    private final Boolean status = true;

    @Column(name = "dang_nhap_lan_dau")
    private final Boolean firstLogin = false;

    @ManyToOne
    @JoinColumn(name = "id_vai_tro")
    private Role role;

    @OneToOne
    @JoinColumn(name = "id_nhan_vien")
    private Employee employee;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.getNameRole().trim().toUpperCase()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
