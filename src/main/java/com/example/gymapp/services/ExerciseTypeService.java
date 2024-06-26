package com.example.gymapp.services;

import com.example.gymapp.domain.dto.ExerciseTypeDto;
import com.example.gymapp.domain.entities.CategoryEntity;
import com.example.gymapp.domain.entities.ExerciseTypeEntity;
import com.example.gymapp.domain.entities.RoutineEntity;
import com.example.gymapp.domain.entities.UserEntity;
import com.example.gymapp.exceptions.ConflictException;
import com.example.gymapp.mappers.impl.CategoryMapper;
import com.example.gymapp.mappers.impl.ExerciseTypeMapper;
import com.example.gymapp.repositories.CategoryRepository;
import com.example.gymapp.repositories.ExerciseTypeRepository;
import com.example.gymapp.repositories.RoutineRepository;
import com.example.gymapp.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ExerciseTypeService {

    @Autowired
    ExerciseTypeRepository exerciseTypeRepository;

    @Autowired
    ExerciseTypeMapper exerciseTypeMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    RoutineRepository routineRepository;


    public ExerciseTypeDto createExercise(ExerciseTypeDto exerciseTypeDto, String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(
                        "User with the username \"%s\" not found.", username)));

        Optional<ExerciseTypeEntity> existingExerciseType = exerciseTypeRepository.findByUserAndName(user, exerciseTypeDto.getName());

        if (existingExerciseType.isPresent()) {
            throw new ConflictException(
                    "Exercise with the name '" + exerciseTypeDto.getName() + "' already exists.");
        }

        ExerciseTypeEntity exerciseTypeEntity = exerciseTypeMapper.mapFromDto(exerciseTypeDto);
        exerciseTypeEntity.setUser(user);

        if (exerciseTypeDto.getCategories() != null && !exerciseTypeDto.getCategories().isEmpty()) {
            List<CategoryEntity> categories = exerciseTypeDto.getCategories().stream()
                    .map(categoryDto -> categoryRepository.findById(categoryDto.getId())
                            .orElseThrow(() -> new EntityNotFoundException(String.format(
                                    "Category with the ID %s not found.", categoryDto.getId().toString()))))
                    .collect(Collectors.toList());
            exerciseTypeEntity.setCategories(categories);
        } else {
            exerciseTypeEntity.setCategories(new ArrayList<>());
        }

        ExerciseTypeEntity savedEntity = exerciseTypeRepository.save(exerciseTypeEntity);
        user.getExerciseTypes().add(savedEntity);
        userRepository.save(user);

        return exerciseTypeMapper.mapToDto(savedEntity);
    }

    public List<ExerciseTypeDto> findAllForUser(String username) {
        List<ExerciseTypeEntity> exerciseTypes = exerciseTypeRepository.findByUserUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with the username \"%s\" not found.", username)));

        if (!exerciseTypes.isEmpty()) {
            return exerciseTypes.stream()
                    .map(exerciseTypeMapper::mapToDto)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    @Transactional
    public void deleteById(UUID exerciseTypeId) {
        ExerciseTypeEntity exerciseType= exerciseTypeRepository.findById(exerciseTypeId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "Exercise type with the ID %s not found.", exerciseTypeId.toString())));

        for (RoutineEntity routine : exerciseType.getRoutines()) {
            routine.getExerciseTypes().remove(exerciseType);
            routineRepository.save(routine);
        }

        for (CategoryEntity category : exerciseType.getCategories()) {
            category.getExerciseTypes().remove(exerciseType);
            categoryRepository.save(category);
        }

        exerciseType.getUser().getExerciseTypes().remove(exerciseType);

        exerciseTypeRepository.deleteById(exerciseTypeId);
    }


    public ExerciseTypeDto updateById(UUID id, ExerciseTypeDto exerciseTypeDto, String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(
                        "User with the username \"%s\" not found.", username)));

        Optional<ExerciseTypeEntity> existingExerciseType = Optional.ofNullable(exerciseTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "Exercise type with the ID %s not found.", id.toString()))));

        Optional<ExerciseTypeEntity> existingExerciseTypeWithName = exerciseTypeRepository.findByUserAndName(user, exerciseTypeDto.getName());

        if (existingExerciseTypeWithName.isPresent() && !existingExerciseTypeWithName.get().getId().equals(id)) {
            throw new ConflictException(
                    "Exercise with the name '" + exerciseTypeDto.getName() + "' already exists.");
        }

        existingExerciseType.get().setName(exerciseTypeDto.getName());
        existingExerciseType.get().setId(exerciseTypeDto.getId());
        existingExerciseType.get().setUser(existingExerciseType.get().getUser());
        existingExerciseType.get().setRoutines(existingExerciseType.get().getRoutines());

        List<CategoryEntity> newCategories = exerciseTypeDto.getCategories().stream()
                .map(categoryDto -> categoryMapper.mapFromDto(categoryDto)).toList();

        existingExerciseType.get().getCategories().clear();
        existingExerciseType.get().getCategories().addAll(newCategories);

        ExerciseTypeEntity updatedExercise = exerciseTypeRepository.save(existingExerciseType.get());

        return exerciseTypeMapper.mapToDto(updatedExercise);
    }
}
