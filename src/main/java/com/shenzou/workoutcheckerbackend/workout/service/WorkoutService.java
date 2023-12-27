package com.shenzou.workoutcheckerbackend.workout.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shenzou.workoutcheckerbackend.workout.AdminExercises;
import com.shenzou.workoutcheckerbackend.workout.exercise.Exercise;
import com.shenzou.workoutcheckerbackend.workout.exercise.ExerciseRepository;
import com.shenzou.workoutcheckerbackend.workout.exercise.ExerciseRequest;
import com.shenzou.workoutcheckerbackend.workout.muscle.Muscle;
import com.shenzou.workoutcheckerbackend.workout.muscle.MuscleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkoutService {

    private final ExerciseRepository exerciseRepository;
    private final MuscleRepository muscleRepository;

    public List<Exercise> findExercises(String muscle) {
        return exerciseRepository.findAll();
    }

    public Exercise createExercise(ExerciseRequest request) {
        Exercise exercise = Exercise.builder()
                .name(request.getName())
                .description(request.getDescription())
                .videolink(request.getVideoLink())
                .icon(request.getIcon())
                .build();

        return exerciseRepository.save(exercise);
    }

    public List<Muscle> createMuscles() {
        ObjectMapper mapper = new ObjectMapper();
        List<AdminExercises> exercises;
        try {
            URL url = this.getClass().getClassLoader().getResource("exercises.json");
            if(url == null){
                throw new RuntimeException();
            }
            exercises = mapper.readValue(Files.readString(Paths.get(url.toURI())), new TypeReference<>() {});
        } catch(IOException | URISyntaxException e){
            throw new RuntimeException(e);
        }

        List<Muscle> muscles = exercises.stream()
                .map(adminExercises -> {
                    List<String> values = new ArrayList<>();
                    values.addAll(Arrays.asList(adminExercises.getPrimaryMuscles()));
                    values.addAll(Arrays.asList(adminExercises.getSecondaryMuscles()));
                    return values;
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toSet())
                .stream()
                .map(s -> Muscle.builder()
                        .name(s)
                        .build())
                .toList();

        return muscleRepository.saveAll(muscles);
    }

    public List<Muscle> findMuscles() {
        return muscleRepository.findAll();
    }

    public List<Exercise> createExercises() {
        ObjectMapper mapper = new ObjectMapper();
        List<AdminExercises> exercises;
        try {
            URL url = this.getClass().getClassLoader().getResource("exercises.json");
            if(url == null){
                throw new RuntimeException();
            }
            exercises = mapper.readValue(Files.readString(Paths.get(url.toURI())), new TypeReference<>() {});
        } catch(IOException | URISyntaxException e){
            throw new RuntimeException(e);
        }

        List<Muscle> muscles = findMuscles();

        List<Exercise> exerciseList = exercises.stream()
                .map(s -> {
                    List<String> primaryMuscles = new ArrayList<>(Arrays.asList(s.getPrimaryMuscles()));
                    List<String> secondaryMuscles = new ArrayList<>(Arrays.asList(s.getSecondaryMuscles()));

                    List<Muscle> primary = primaryMuscles.stream()
                            .flatMap(s1 -> muscles.stream()
                                    .filter(muscle -> s1.equalsIgnoreCase(muscle.getName()))
                            )
                            .toList();
                    List<Muscle> secondary = secondaryMuscles.stream()
                            .flatMap(s1 -> muscles.stream()
                                    .filter(muscle -> s1.equalsIgnoreCase(muscle.getName()))
                            )
                            .toList();

                    return Exercise.builder()
                            .name(s.getName())
                            .description(String.join("\n", s.getInstructions())
                                    .codePoints()
                                    .limit(255)
                                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                                    .toString())
                            .muscles(primary)
                            .secondaryMuscles(secondary)
                            .build();
                })
                .toList();

        return exerciseRepository.saveAll(exerciseList);
    }
}
