package com.shenzou.workoutcheckerbackend.workout.serie;

import com.shenzou.workoutcheckerbackend.authentication.user.User;
import com.shenzou.workoutcheckerbackend.workout.exercise.Exercise;
import com.shenzou.workoutcheckerbackend.workout.exercise.UserExercise;
import com.shenzou.workoutcheckerbackend.workout.seance.Seance;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "serie")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Serie {
    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;
    @ManyToOne
    @JoinColumn(name = "seanceid")
    private Seance seance;
    @ManyToOne
    @JoinColumn(name = "exerciseid")
    private Exercise exercise;
    @ManyToOne
    @JoinColumn(name = "userexerciseid")
    private UserExercise userExercise;
    private Integer repetitions;
    private Integer rest;
    @Id
    @GeneratedValue
    private Integer id;
}
