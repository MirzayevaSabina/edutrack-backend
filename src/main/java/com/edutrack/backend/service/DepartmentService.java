
package com.edutrack.backend.service;

import com.edutrack.backend.dto.DepartmentRequestDto;
import com.edutrack.backend.dto.DepartmentResponseDto;
import com.edutrack.backend.entity.Department;
import com.edutrack.backend.mapper.DepartmentMapper;
import com.edutrack.backend.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    public DepartmentService(
            DepartmentRepository departmentRepository,
            DepartmentMapper departmentMapper
    ) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    public DepartmentResponseDto saveDepartment(DepartmentRequestDto dto) {
        String departmentName = dto.getName().trim();

        if (departmentRepository.existsByName(departmentName)) {
            throw new IllegalArgumentException(
                    "Department with this name already exists"
            );
        }

        Department department = departmentMapper.toEntity(dto);
        department.setName(departmentName);

        Department savedDepartment = departmentRepository.save(department);

        return departmentMapper.toResponseDto(savedDepartment);
    }

    public List<DepartmentResponseDto> getAllDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(departmentMapper::toResponseDto)
                .toList();
    }

    public DepartmentResponseDto updateDepartment(
            Long id,
            DepartmentRequestDto dto
    ) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Department not found with id: " + id
                ));

        String departmentName = dto.getName().trim();

        boolean nameExists = departmentRepository.existsByName(departmentName);

        if (nameExists && !department.getName().equalsIgnoreCase(departmentName)) {
            throw new IllegalArgumentException(
                    "Department with this name already exists"
            );
        }

        department.setName(departmentName);

        Department updatedDepartment = departmentRepository.save(department);

        return departmentMapper.toResponseDto(updatedDepartment);
    }

    public void deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Department not found with id: " + id
                ));

        if (department.getStudents() != null
                && !department.getStudents().isEmpty()) {
            throw new IllegalArgumentException(
                    "Cannot delete department because students are assigned to it"
            );
        }

        departmentRepository.delete(department);
    }
}
