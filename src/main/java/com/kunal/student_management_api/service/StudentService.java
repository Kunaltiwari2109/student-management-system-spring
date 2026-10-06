package com.kunal.student_management_api.service;

import com.kunal.student_management_api.dto.StudentResponse;
import com.kunal.student_management_api.dto.StudentRequest;
import com.kunal.student_management_api.exception.StudentNotFoundException;
import com.kunal.student_management_api.model.Student;
import com.kunal.student_management_api.repository.StudentRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(int id) {
        return studentRepository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException("Student not found"));
    }

    public List<Student> getStudentsSortedByMarks() {
        return studentRepository.findAll(
                Sort.by(Sort.Direction.DESC, "marks")
        );
    }

    public StudentResponse addStudent(StudentRequest request) {

    Student student = new Student();
    Integer maximumId = studentRepository.findMaximumId();

    student.setId(maximumId == null ? 1 : maximumId + 1);
    student.setName(request.getName());
    student.setMarks(request.getMarks());

    Student savedStudent = studentRepository.save(student);

    return new StudentResponse(
            savedStudent.getId(),
            savedStudent.getName(),
            savedStudent.getMarks()
    );
}

    public boolean deleteStudent(int id) {

        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException("Student not found");
        }

        studentRepository.deleteById(id);
        return true;
    }

    public Student updateStudent(int id, Student updatedStudent) {

        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException("Student not found"));

        existingStudent.setName(updatedStudent.getName());
        existingStudent.setMarks(updatedStudent.getMarks());

        return studentRepository.save(existingStudent);
    }

    public Page<Student> getStudentsPage(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return studentRepository.findAll(pageable);
    }

    public Page<Student> getStudentsPageSortedByMarks(int page, int size) {
    Pageable pageable = PageRequest.of(
            page,
            size,
            Sort.by(Sort.Direction.DESC, "marks")
    );

    return studentRepository.findAll(pageable);
    }

    public List<Student> searchStudentsByName(String name) {
    return studentRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Student> getStudentsByMinimumMarks(double min) {
    return studentRepository.findByMarksGreaterThanEqual(min);
    }

    public List<Student> getStudentsByMarksRange(double min, double max) {

        return studentRepository.findByMarksBetween(min, max);
    }

    public long getStudentCount() {
    return studentRepository.count();
    }

    public List<Student> getStudentsAboveMarks(double min) {
    return studentRepository.findStudentsAboveMarks(min);
    }
}