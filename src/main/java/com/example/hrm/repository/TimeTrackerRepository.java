package com.example.hrm.repository;

import com.example.hrm.model.TimeTracker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeTrackerRepository extends JpaRepository<TimeTracker, Long> {
}
