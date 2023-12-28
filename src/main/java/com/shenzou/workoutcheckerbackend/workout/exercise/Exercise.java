package com.shenzou.workoutcheckerbackend.workout.exercise;

import com.shenzou.workoutcheckerbackend.workout.muscle.Muscle;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "exercise")
public class Exercise {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private String name;
    private String description;
    private String videolink;
    private String icon;
    @ManyToMany
    private Set<Muscle> muscles;
    @ManyToMany
    private Set<Muscle> secondarymuscles;
}
