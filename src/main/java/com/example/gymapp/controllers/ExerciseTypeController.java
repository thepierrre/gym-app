package com.example.gymapp.controllers;


import com.example.gymapp.domain.dto.ExerciseTypeDto;
import com.example.gymapp.domain.entities.ExerciseTypeEntity;
import com.example.gymapp.mappers.Mapper;
import com.example.gymapp.services.ExerciseTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@RestController
public class ExerciseTypeController {

    @Autowired
    private ExerciseTypeService exerciseTypeService;

    @Autowired
    private Mapper<ExerciseTypeEntity, ExerciseTypeDto> exerciseTypeMapper;

    @GetMapping(path = "/exercise-types")
    public List<ExerciseTypeEntity> findAll() {
        return exerciseTypeService.findAll();
    }

    @PostMapping(path = "/exercise-types")
    public ResponseEntity<ExerciseTypeDto> createExercise(@Valid @RequestBody ExerciseTypeDto exerciseTypeDto) {
        ExerciseTypeDto createdExerciseType = exerciseTypeService.createExercise(exerciseTypeDto);
        return new ResponseEntity<>(createdExerciseType, HttpStatus.CREATED);
    }


    @DeleteMapping(path = "/exercise-types/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") UUID id) {
        exerciseTypeService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/exercise-types")
    public ResponseEntity<Void> deleteAll() {
        exerciseTypeService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(path = "/exercises/{id}")
    public ResponseEntity<ExerciseTypeDto> update(
            @PathVariable("id") UUID id,
            @RequestBody ExerciseTypeDto exerciseTypeDto
    ) {
        if(!exerciseTypeService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ExerciseTypeDto updatedExerciseType = exerciseTypeService.update(id, exerciseTypeDto);
        return new ResponseEntity<>(updatedExerciseType, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
