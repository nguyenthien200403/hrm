package com.example.hrm.repository;

import com.example.hrm.model.Network;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NetworkRepository extends JpaRepository<Network, Long> {
    // Kiểm tra trùng SSID + MAC
    boolean existsBySsidAndMacRouter(String ssid, String macRouter);

    // Kiểm tra trùng SSID + MAC nhưng loại trừ id (dùng cho update)
    boolean existsBySsidAndMacRouterAndIdNot(String ssid, String macRouter, Long id);

    boolean existsByIpPublic(String ipPublic);
}
