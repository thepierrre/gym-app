package com.example.gymapp.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutDto {

    private Long id;

//    private UserEntity user_id;

//    private TrainingTypeEntity training_type_id;
    private LocalDateTime date;


}
