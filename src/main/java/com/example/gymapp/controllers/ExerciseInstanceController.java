package com.example.gymapp.controllers;

import com.example.gymapp.domain.dto.ExerciseInstanceDto;
import com.example.gymapp.domain.entities.ExerciseInstanceEntity;
import com.example.gymapp.mappers.Mapper;
import com.example.gymapp.services.ExerciseInstanceService;
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
public class ExerciseInstanceController {

    @Autowired
    ExerciseInstanceService exerciseInstanceService;

    @Autowired
    private Mapper<ExerciseInstanceEntity, ExerciseInstanceDto> exerciseInstanceMapper;

    @GetMapping(path = "/exercise-instances")
    public List<ExerciseInstanceDto> findAll() {
        return exerciseInstanceService.findAll();
    }

    @PostMapping(path = "/exercise-instances")
    public ResponseEntity<ExerciseInstanceDto> createExerciseInstance(@Valid @RequestBody ExerciseInstanceDto exerciseInstanceDto) {
        ExerciseInstanceDto createdExerciseInstance = exerciseInstanceService.createExerciseInstance(exerciseInstanceDto);
        return new ResponseEntity<>(createdExerciseInstance, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/exercise-instances")
    public ResponseEntity<Void> deleteAll() {
        exerciseInstanceService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/exercise-instances/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") UUID id) {
        exerciseInstanceService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
