package com.shenzou.workoutcheckerbackend.workout.controller;

import com.shenzou.workoutcheckerbackend.workout.exercise.Exercise;
import com.shenzou.workoutcheckerbackend.workout.exercise.ExerciseRequest;
import com.shenzou.workoutcheckerbackend.workout.exercise.UserExercise;
import com.shenzou.workoutcheckerbackend.workout.muscle.Muscle;
import com.shenzou.workoutcheckerbackend.workout.service.WorkoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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

    //TODO: Add user specific exercises retrieval

    @GetMapping("/muscles")
    public ResponseEntity<List<Muscle>> getMuscles() {
        return ResponseEntity.ok(workoutService.findMuscles());
    }

    @PostMapping("/exercise")
    public ResponseEntity<UserExercise> createCustomExercise(@RequestBody @Valid ExerciseRequest exerciseRequest){
        return ResponseEntity.created(URI.create("")).body(workoutService.createCustomExercise(exerciseRequest));
    }

}
