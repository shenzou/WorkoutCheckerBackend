package com.shenzou.workoutcheckerbackend.workout.controller;

import com.shenzou.workoutcheckerbackend.workout.service.WorkoutService;
import com.shenzou.workoutcheckerbackend.workout.exercise.Exercise;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/workout")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;

    @GetMapping("/exercises")
    public ResponseEntity<List<Exercise>> getExercises(@RequestParam(required = false) String muscle) {
        return ResponseEntity.ok(workoutService.findExercises(muscle));
    }

}
