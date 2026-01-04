package com.example.hrm.Impl;

import com.example.hrm.projection.RequirementCountProjection;

public class RequirementCountProjectionImpl implements RequirementCountProjection {
    private final Integer totalDetailRequirement;
    private final Integer pending;
    private final Integer approved;
    private final Integer rejected;

    public RequirementCountProjectionImpl(Integer totalDetailRequirement,
                                          Integer pending,
                                          Integer approved,
                                          Integer rejected) {
        this.totalDetailRequirement = totalDetailRequirement;
        this.pending = pending;
        this.approved = approved;
        this.rejected = rejected;
    }

    @Override
    public Integer getTotalDetailRequirement() {
        return totalDetailRequirement;
    }

    @Override
    public Integer getPending() {
        return pending;
    }

    @Override
    public Integer getApproved() {
        return approved;
    }

    @Override
    public Integer getRejected() {
        return rejected;
    }
}
