package com.shenzou.workoutcheckerbackend.workout.exercise;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExerciseRequest {
    private String name;
    private String description;
    private String videoLink;
    private String icon;

}
