package com.shenzou.workoutcheckerbackend.workout.exercise;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {

    Optional<List<Exercise>> findAllByMusclesNameContains(String muscle);

}
