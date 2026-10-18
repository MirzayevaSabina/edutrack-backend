package com.edutrack.backend.repository;

import com.edutrack.backend.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Long> {
    List<Student> findByFirstNameContainingIgnoreCase(String firstName);

    @Query("SELECT s FROM Student s WHERE s.firstName = :firstName")
    List<Student> findStudentsByFirstName(@Param("firstName") String firstName);

    List<Student> findByDepartmentId(Long departmentId);

}
