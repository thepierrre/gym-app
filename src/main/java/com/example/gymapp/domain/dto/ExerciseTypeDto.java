package com.example.gymapp.domain.dto;

import com.example.gymapp.domain.entities.ExerciseTypeEntity;
import com.example.gymapp.domain.entities.RoutineExerciseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExerciseTypeDto {

    private UUID id;

    private Boolean isDefault;

    @NotBlank(message = "Exercise name cannot be empty.")
    private String name;

    private String equipment;

    @JsonIgnore
    private UserDto user;

    @JsonIgnoreProperties("exerciseTypes")
    private List<CategoryDto> categories = new ArrayList<>();

    @JsonIgnore
    private List<RoutineExerciseDto> routineExercises = new ArrayList<>();

    public ExerciseTypeDto(String id) {
        this.id = UUID.fromString(id);
    }

}
