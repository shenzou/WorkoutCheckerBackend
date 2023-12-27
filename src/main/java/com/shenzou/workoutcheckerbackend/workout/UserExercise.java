package com.shenzou.workoutcheckerbackend.workout;

import com.shenzou.workoutcheckerbackend.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "custom_exercise")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class UserExercise {
    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;
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
