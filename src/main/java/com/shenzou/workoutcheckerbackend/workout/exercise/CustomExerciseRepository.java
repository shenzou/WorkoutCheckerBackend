package com.shenzou.workoutcheckerbackend.workout.exercise;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomExerciseRepository extends JpaRepository<UserExercise, Integer> {

    Optional<List<UserExercise>> findAllByMusclesNameContains(String muscle);

}
