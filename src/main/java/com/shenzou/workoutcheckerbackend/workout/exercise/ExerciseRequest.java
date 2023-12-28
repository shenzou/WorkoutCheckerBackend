package com.shenzou.workoutcheckerbackend.workout.exercise;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExerciseRequest {
    @NotBlank
    private String name;
    @Length(max = 254)
    private String description;
    private String videoLink;
    private String icon;
    private List<Integer> musclesIds;
    private List<Integer> secondaryMusclesIds;

}
