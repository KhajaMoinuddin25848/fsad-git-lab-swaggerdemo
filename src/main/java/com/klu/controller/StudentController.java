package com.klu.controller;

import com.klu.model.Student;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/students")
@Tag(name = "Student API", description = "CRUD operations for students")
public class StudentController {

    private List<Student> students = new ArrayList<>();

    // ===================== GET ALL =====================
    @GetMapping
    @Operation(summary = "Get all students")
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(students);
    }

    // ===================== ADD =====================
    @PostMapping
    @Operation(summary = "Add a new student")
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        students.add(student);
        return ResponseEntity.ok(student);
    }

    // ===================== GET BY ID =====================
    @GetMapping("/{id}")
    @Operation(summary = "Get student by ID")
    public ResponseEntity<Student> getStudentById(@PathVariable int id) {
        return students.stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ===================== UPDATE =====================
    @PutMapping("/{id}")
    @Operation(summary = "Update student")
    public ResponseEntity<Student> updateStudent(@PathVariable int id,
                                                 @RequestBody Student updatedStudent) {
        for (Student s : students) {
            if (s.getId() == id) {
                s.setName(updatedStudent.getName());
                s.setCourse(updatedStudent.getCourse()); // fixed: use course
                return ResponseEntity.ok(s);
            }
        }
        return ResponseEntity.notFound().build();
    }

    // ===================== DELETE =====================
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete student by ID")
    public ResponseEntity<String> deleteStudent(@PathVariable int id) {
        boolean removed = students.removeIf(s -> s.getId() == id);

        if (removed) {
            return ResponseEntity.ok("Deleted Successfully");
        } else {
            return ResponseEntity.status(404).body("Student Not Found");
        }
    }
}
