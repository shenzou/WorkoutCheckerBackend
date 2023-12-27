package com.shenzou.workoutcheckerbackend.workout;

import com.shenzou.workoutcheckerbackend.user.User;
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
