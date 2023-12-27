package com.shenzou.workoutcheckerbackend.workout;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    @OneToMany
    private List<Muscle> muscles;
    @OneToMany
    private List<Muscle> secondaryMuscles;
}
