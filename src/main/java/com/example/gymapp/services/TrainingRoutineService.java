package com.example.gymapp.services;

import com.example.gymapp.domain.dto.TrainingRoutineDto;
import com.example.gymapp.domain.entities.TrainingRoutineEntity;
import com.example.gymapp.mappers.impl.TrainingRoutineMapper;
import com.example.gymapp.repositories.TrainingRoutineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TrainingRoutineService {

    @Autowired
    private TrainingRoutineRepository trainingRoutineRepository;

    @Autowired
    private TrainingRoutineMapper trainingRoutineMapper;

    public TrainingRoutineDto createTrainingType(TrainingRoutineDto trainingRoutineDto) {

        TrainingRoutineEntity trainingRoutineEntity = trainingRoutineMapper.mapFromDto(trainingRoutineDto);

        trainingRoutineEntity.setExerciseTypes(trainingRoutineEntity.getExerciseTypes());

        TrainingRoutineEntity savedTrainingRoutineEntity = trainingRoutineRepository.save(trainingRoutineEntity);

        return trainingRoutineMapper.mapToDto(savedTrainingRoutineEntity);
    }

    public List<TrainingRoutineDto> findAll() {
        return trainingRoutineRepository.
                findAll().stream().map(trainingRoutineMapper::mapToDto).
                toList(); }

    public void deleteById(UUID id) {
        trainingRoutineRepository.deleteById(id);
    }

    public void deleteAll() {
        trainingRoutineRepository.deleteAll();
    }
}
