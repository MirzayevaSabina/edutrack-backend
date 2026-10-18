package com.edutrack.backend.mapper;

import com.edutrack.backend.dto.DepartmentRequestDto;
import com.edutrack.backend.dto.DepartmentResponseDto;
import com.edutrack.backend.entity.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {
    public Department toEntity(DepartmentRequestDto dto){

        Department department = new Department();

        department.setName(dto.getName());

        return department;
    }

    public DepartmentResponseDto toResponseDto(Department department){

        DepartmentResponseDto dto = new DepartmentResponseDto();

        dto.setId(department.getId());
        dto.setName(department.getName());

        return dto;
    }
}
