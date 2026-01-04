package com.example.hrm.projection;

public interface RequirementCountProjection {
    Integer getTotalDetailRequirement();
    Integer getPending();
    Integer getApproved();
    Integer getRejected();
}
