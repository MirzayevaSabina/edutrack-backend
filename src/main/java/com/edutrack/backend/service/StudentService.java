package com.edutrack.backend.service;

import com.edutrack.backend.dto.StudentRequestDto;
import com.edutrack.backend.dto.StudentResponseDto;
import com.edutrack.backend.entity.Department;
import com.edutrack.backend.entity.Student;
import com.edutrack.backend.exception.StudentNotFoundException;
import com.edutrack.backend.mapper.StudentMapper;
import com.edutrack.backend.repository.DepartmentRepository;
import com.edutrack.backend.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final DepartmentRepository departmentRepository;

    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper,
                          DepartmentRepository departmentRepository) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.departmentRepository = departmentRepository;
    }


    public StudentResponseDto saveStudent(StudentRequestDto dto) {

        Student student = studentMapper.toEntity(dto);

        if (dto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));

            student.setDepartment(department);
        }

        Student savedStudent = studentRepository.save(student);

        return studentMapper.toResponseDto(savedStudent);
    }


    public Page<StudentResponseDto> getAllStudents(Pageable pageable) {

        Page<Student> students = studentRepository.findAll(pageable);

        return students.map(studentMapper::toResponseDto);
    }


    public StudentResponseDto updateStudent(Long id, StudentRequestDto dto) {

        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException("Student not found"));

        existingStudent.setFirstName(dto.getFirstName());
        existingStudent.setLastName(dto.getLastName());
        existingStudent.setEmail(dto.getEmail());

        if (dto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));

            existingStudent.setDepartment(department);
        } else {
            existingStudent.setDepartment(null);
        }

        Student updatedStudent = studentRepository.save(existingStudent);

        return studentMapper.toResponseDto(updatedStudent);
    }


    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public StudentResponseDto getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                new StudentNotFoundException("Student not found"));
        return studentMapper.toResponseDto(student);
    }

    public List<StudentResponseDto> searchByFirstName(String firstName) {

        List<Student> students =
                studentRepository.findStudentsByFirstName(firstName);

        return students.stream()
                .map(studentMapper::toResponseDto)
                .toList();
    }

    public List<StudentResponseDto> getStudentsByDepartment(Long departmentId) {

        List<Student> students =
                studentRepository.findByDepartmentId(departmentId);

        return students.stream()
                .map(studentMapper::toResponseDto)
                .toList();
    }
}
