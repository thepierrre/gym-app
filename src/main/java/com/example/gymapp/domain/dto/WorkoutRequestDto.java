package com.example.gymapp.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutRequestDto {

    private UUID id;

    @JsonIgnoreProperties({"routines", "password", "email", "workouts"})
    private UserDto user;

    private String routineName;

}

