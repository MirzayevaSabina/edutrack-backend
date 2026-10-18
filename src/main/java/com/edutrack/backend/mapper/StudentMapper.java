package com.edutrack.backend.mapper;

import com.edutrack.backend.dto.StudentRequestDto;
import com.edutrack.backend.dto.StudentResponseDto;
import com.edutrack.backend.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
    public Student toEntity(StudentRequestDto dto) {
        Student student = new Student();

        student.setEmail(dto.getEmail());
        student.setLastName(dto.getLastName());
        student.setFirstName(dto.getFirstName());

        return student;
    }

    public StudentResponseDto toResponseDto(Student student){
        StudentResponseDto responseDto =  new StudentResponseDto();

        responseDto.setId(student.getId());
        responseDto.setFirstName(student.getFirstName());
        responseDto.setLastName(student.getLastName());
        responseDto.setEmail(student.getEmail());
        if (student.getDepartment() != null) {
            responseDto.setDepartmentId(student.getDepartment().getId());
            responseDto.setDepartmentName(student.getDepartment().getName());
        }
        return responseDto;
    }
}
