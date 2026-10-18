
package com.edutrack.backend.controller;

import com.edutrack.backend.dto.DepartmentRequestDto;
import com.edutrack.backend.dto.DepartmentResponseDto;
import com.edutrack.backend.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public DepartmentResponseDto addDepartment(
            @Valid @RequestBody DepartmentRequestDto dto) {
        return departmentService.saveDepartment(dto);
    }

    @GetMapping
    public List<DepartmentResponseDto> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @PutMapping("/{id}")
    public DepartmentResponseDto updateDepartment(
            @PathVariable Long id,
            @Valid @RequestBody DepartmentRequestDto dto) {
        return departmentService.updateDepartment(id, dto);
    }

    @DeleteMapping("/{id}")
    public String deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return "Department deleted successfully";
    }
}
