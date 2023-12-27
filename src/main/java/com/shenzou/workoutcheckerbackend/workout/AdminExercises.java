package com.shenzou.workoutcheckerbackend.workout;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminExercises {
    private String name;
    private String force;
    private String level;
    private String mechanic;
    private String equipment;
    private String[] primaryMuscles;
    private String[] secondaryMuscles;
    private String[] instructions;
    private String category;
}
