package com.example.hrm.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NetworkRequest {
    @NotBlank(message = "NOT NULL")
    private String ssid;

    @NotBlank(message = "NOT NULL")
    private String macRouter;

    @NotBlank(message = "NOT NULL")
    private String ipPublic;
}
