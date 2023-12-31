package com.shenzou.workoutcheckerbackend.workout.muscle;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "muscle")
public class Muscle {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
}
