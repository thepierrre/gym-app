package com.example.gymapp.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrainingRoutineDto {

    private UUID id;

    private String name;

    private List<ExerciseTypeDto> exerciseTypes;

    @NotNull(message = "User cannot be null.")
    private UserDto user;

    private List<WorkoutDto> workouts;

}
