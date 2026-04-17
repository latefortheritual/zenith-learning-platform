package com.zenith.zenith.controller;

import com.zenith.zenith.dto.CourseDTO;
import com.zenith.zenith.entity.User;
import com.zenith.zenith.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourse(
            @PathVariable UUID id,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(courseService.getCourseWithGraph(id, user.getId()));
    }

    @PostMapping("/{id}/enroll")
    public ResponseEntity<Void> enroll(
            @PathVariable UUID id,
            @AuthenticationPrincipal User user) {
        courseService.enrollStudent(id, user.getId());
        return ResponseEntity.ok().build();
    }
}