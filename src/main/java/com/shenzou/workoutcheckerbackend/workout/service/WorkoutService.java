package com.shenzou.workoutcheckerbackend.workout.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shenzou.workoutcheckerbackend.authentication.user.User;
import com.shenzou.workoutcheckerbackend.exception.FileNotFoundException;
import com.shenzou.workoutcheckerbackend.workout.AdminExercises;
import com.shenzou.workoutcheckerbackend.workout.exercise.*;
import com.shenzou.workoutcheckerbackend.workout.muscle.Muscle;
import com.shenzou.workoutcheckerbackend.workout.muscle.MuscleRepository;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkoutService {

    private final ServletContext context;
    private final ExerciseRepository exerciseRepository;
    private final CustomExerciseRepository customExerciseRepository;
    private final MuscleRepository muscleRepository;

    public List<Exercise> findExercises(String muscle) {

        if(muscle != null && !muscle.isEmpty()){
            return exerciseRepository.findAllByMusclesNameContains(muscle)
                    .orElseThrow();
        }
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

    public UserExercise createCustomExercise(ExerciseRequest request) {
        Set<Muscle> primaryMuscles = null;
        if(request.getMusclesIds() != null){
            primaryMuscles = new HashSet<>(muscleRepository.findAllById(request.getMusclesIds()));

        }
        Set<Muscle> secondaryMuscles = null;
        if(request.getSecondaryMusclesIds() != null){
            secondaryMuscles = new HashSet<>(muscleRepository.findAllById(request.getSecondaryMusclesIds()));

        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer connectedUser = Optional.ofNullable(((User) authentication.getPrincipal()).getId())
                .orElseThrow();

        UserExercise exercise = UserExercise.builder()
                .name(request.getName())
                .description(request.getDescription())
                .videolink(request.getVideoLink())
                .icon(request.getIcon())
                .muscles(primaryMuscles)
                .secondarymuscles(secondaryMuscles)
                .userId(connectedUser)
                .build();

        return customExerciseRepository.save(exercise);
    }

    public List<Muscle> createMuscles() throws FileNotFoundException {
        ObjectMapper mapper = new ObjectMapper();
        List<AdminExercises> exercises;
        try {
            URL url = this.getClass().getClassLoader().getResource("exercises.json");
            if(url == null){
                throw new FileNotFoundException();
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

    public List<Exercise> createExercises() throws FileNotFoundException {
        ObjectMapper mapper = new ObjectMapper();
        List<AdminExercises> exercises;
        try {
            URL url = this.getClass().getClassLoader().getResource("exercises.json");
            if(url == null){
                throw new FileNotFoundException();
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

                    Set<Muscle> primary = primaryMuscles.stream()
                            .flatMap(s1 -> muscles.stream()
                                    .filter(muscle -> s1.equalsIgnoreCase(muscle.getName()))
                            )
                            .collect(Collectors.toSet());
                    Set<Muscle> secondary = secondaryMuscles.stream()
                            .flatMap(s1 -> muscles.stream()
                                    .filter(muscle -> s1.equalsIgnoreCase(muscle.getName()))
                            )
                            .collect(Collectors.toSet());

                    return Exercise.builder()
                            .name(s.getName())
                            .description(String.join("\n", s.getInstructions())
                                    .codePoints()
                                    .limit(255)
                                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                                    .toString())
                            .muscles(primary)
                            .secondarymuscles(secondary)
                            .build();
                })
                .toList();

        return exerciseRepository.saveAll(exerciseList);
    }
}
