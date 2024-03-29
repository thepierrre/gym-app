package com.example.gymapp.controllers;

import com.example.gymapp.domain.dto.UserDto;
import com.example.gymapp.domain.dto.WorkoutDto;
import com.example.gymapp.domain.entities.WorkoutEntity;
import com.example.gymapp.mappers.impl.WorkoutMapper;
import com.example.gymapp.services.WorkoutService;
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
public class WorkoutController {

    @Autowired
    WorkoutService workoutService;

    @Autowired
    WorkoutMapper workoutMapper;

    @GetMapping(path = "/workouts")
    public List<WorkoutDto> findAll() {
        return workoutService.findAll();
    }

    @PostMapping(path = "/workouts")
    public ResponseEntity<WorkoutDto> createWorkout(@Valid @RequestBody WorkoutDto workoutDto) {
        WorkoutDto createdWorkout = workoutService.createWorkout(workoutDto);
        return new ResponseEntity<>(createdWorkout, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/workouts/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") UUID id) {
        workoutService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @DeleteMapping(path = "/workouts")
    public ResponseEntity<Void> deleteAll() {
        workoutService.deleteAll();
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
