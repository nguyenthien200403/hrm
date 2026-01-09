package com.example.hrm.mapper;

import com.example.hrm.dto.AttendanceDTO;
import com.example.hrm.model.Attendance;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {
    AttendanceDTO toDTO(Attendance attendance);
    List<Attendance> toDTOs(List<Attendance> attendances);
}
