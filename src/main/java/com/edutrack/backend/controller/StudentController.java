package com.edutrack.backend.controller;

import com.edutrack.backend.dto.StudentRequestDto;
import com.edutrack.backend.dto.StudentResponseDto;
import com.edutrack.backend.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @GetMapping
    public Page<StudentResponseDto> getStudents(Pageable pageable) {
        return studentService.getAllStudents(pageable);
    }

    @PostMapping
    public StudentResponseDto addStudent(@Valid @RequestBody StudentRequestDto dto) {
        return studentService.saveStudent(dto);
    }

    @PutMapping("/{id}")
    public StudentResponseDto updateStudent(@PathVariable Long id,
                                            @Valid @RequestBody StudentRequestDto dto) {

        return studentService.updateStudent(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

    @GetMapping("/{id}")
    public StudentResponseDto getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @GetMapping("/search")
    public List<StudentResponseDto> searchStudents(
            @RequestParam String firstName) {

        return studentService.searchByFirstName(firstName);
    }

    @GetMapping("/query")
    public List<StudentResponseDto> searchStudentsByQuery(
            @RequestParam String firstName) {

        return studentService.searchByFirstName(firstName);
    }

    @GetMapping("/departments/{departmentId}/students")
    public List<StudentResponseDto> getStudentsByDepartment(
            @PathVariable Long departmentId) {

        return studentService.getStudentsByDepartment(departmentId);
    }

}

