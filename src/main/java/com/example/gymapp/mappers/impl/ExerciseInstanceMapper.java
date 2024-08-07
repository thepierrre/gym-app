package com.example.gymapp.mappers.impl;

import com.example.gymapp.domain.dto.ExerciseInstanceDto;
import com.example.gymapp.domain.dto.InstanceWorkingSetDto;
import com.example.gymapp.domain.entities.ExerciseInstanceEntity;
import com.example.gymapp.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.stream.Collectors;

@Component
public class ExerciseInstanceMapper implements Mapper<ExerciseInstanceEntity, ExerciseInstanceDto> {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public ExerciseInstanceDto mapToDto(ExerciseInstanceEntity exerciseInstanceEntity) {
        ExerciseInstanceDto exerciseInstanceDto = modelMapper.
                map(exerciseInstanceEntity, ExerciseInstanceDto.class);

        exerciseInstanceDto.setWorkingSets(exerciseInstanceDto.getWorkingSets().stream()
                .sorted(Comparator.comparing(InstanceWorkingSetDto::getCreationTimedate)).collect(Collectors.toList()));

        return exerciseInstanceDto;
    }

    @Override
    public ExerciseInstanceEntity mapFromDto(ExerciseInstanceDto exerciseInstanceDto) {
        return modelMapper.map(exerciseInstanceDto, ExerciseInstanceEntity.class);
    }
}
