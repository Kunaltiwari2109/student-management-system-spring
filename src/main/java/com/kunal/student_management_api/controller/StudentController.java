package com.kunal.student_management_api.controller;
import com.kunal.student_management_api.dto.StudentRequest;
import com.kunal.student_management_api.dto.StudentResponse;
import com.kunal.student_management_api.model.Student;
import com.kunal.student_management_api.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import org.springframework.data.domain.Page;

@RestController
@CrossOrigin(origins = "*")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public List<Student> getStudents() {

        return studentService.getAllStudents();
    }

    @GetMapping("/students/sorted/marks")
    public List<Student> getStudentsSortedByMarks() {
        return studentService.getStudentsSortedByMarks();
    }

    @GetMapping("/students/page")
    public Page<Student> getStudentsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return studentService.getStudentsPage(page, size);
    }

    @GetMapping("/students/page/sorted")
    public Page<Student> getStudentsPageSortedByMarks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return studentService.getStudentsPageSortedByMarks(page, size);
    }

    @PostMapping("/students")
public ResponseEntity<StudentResponse> addStudent(
        @Valid @RequestBody StudentRequest request) {

    StudentResponse savedStudent = studentService.addStudent(request);

    return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
}

    @GetMapping("/students/{id}")
    public Student getStudentById(@PathVariable int id) {

        return studentService.getStudentById(id);
    }

    @DeleteMapping("/students/{id}")
    public String deleteStudent(@PathVariable int id) {

        studentService.deleteStudent(id);

        return "Student deleted successfully";
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable int id,
            @Valid @RequestBody Student student) {

        Student updatedStudent = studentService.updateStudent(id, student);

        return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
    }

    @GetMapping("/students/search")
    public List<Student> searchStudentsByName(@RequestParam String name) {
        return studentService.searchStudentsByName(name);
    }

    @GetMapping("/students/marks")
    public List<Student> getStudentsByMinimumMarks(
            @RequestParam double min) {

        return studentService.getStudentsByMinimumMarks(min);
    }

    @GetMapping("/students/marks/range")
    public List<Student> getStudentsByMarksRange(
            @RequestParam double min,
            @RequestParam double max) {

             if (min > max) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Minimum marks cannot be greater than maximum marks"
                );
            }

        return studentService.getStudentsByMarksRange(min, max);
    }

    @GetMapping("/students/count")
    public long getStudentCount() {
        return studentService.getStudentCount();
    }

    @GetMapping("/students/above-marks")
    public List<Student> getStudentsAboveMarks(@RequestParam double min) {
        return studentService.getStudentsAboveMarks(min);
    }
}