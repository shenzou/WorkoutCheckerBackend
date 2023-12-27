package com.shenzou.workoutcheckerbackend.workout.controller;

import com.shenzou.workoutcheckerbackend.workout.service.WorkoutService;
import com.shenzou.workoutcheckerbackend.workout.exercise.ExerciseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/workout")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminWorkoutController {

    private final WorkoutService workoutService;

    @PostMapping("/exercise")
    public ResponseEntity<?> createExercise(@RequestBody ExerciseRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(workoutService.createExercise(request));
    }

    @PostMapping("/muscles")
    public ResponseEntity<?> createMuscles() {
        return ResponseEntity.status(HttpStatus.CREATED).body(workoutService.createMuscles());
    }

    @PostMapping("/exercises")
    public ResponseEntity<?> createExercises() {
        return ResponseEntity.status(HttpStatus.CREATED).body(workoutService.createExercises());
    }



}
