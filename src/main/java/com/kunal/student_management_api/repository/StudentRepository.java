package com.kunal.student_management_api.repository;

import com.kunal.student_management_api.model.Student;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    List<Student> findByNameContainingIgnoreCase(String name);

    List<Student> findByMarksGreaterThanEqual(double min);

    List<Student> findByMarksBetween(double min, double max);

    @Query("SELECT MAX(s.id) FROM Student s")
    Integer findMaximumId();

    @Query("SELECT s FROM Student s WHERE s.marks > :min")
    List<Student> findStudentsAboveMarks(@Param("min") double min);

}