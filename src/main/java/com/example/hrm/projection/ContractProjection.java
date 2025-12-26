package com.example.hrm.projection;

import java.time.LocalDateTime;

public interface ContractProjection {
    String getId();
    String getTypeContractName();
    String getEmployeeId();
    String getEmployeeName();
    String getPosition();
    LocalDateTime getDateSign();
}
