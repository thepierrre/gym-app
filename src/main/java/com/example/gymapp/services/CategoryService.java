package com.example.gymapp.services;

import com.example.gymapp.domain.dto.CategoryDto;
import com.example.gymapp.domain.dto.ExerciseInstanceDto;
import com.example.gymapp.domain.entities.CategoryEntity;
import com.example.gymapp.domain.entities.ExerciseTypeEntity;
import com.example.gymapp.exceptions.ConflictException;
import com.example.gymapp.mappers.impl.CategoryMapper;
import com.example.gymapp.repositories.CategoryRepository;
import com.example.gymapp.repositories.ExerciseTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ExerciseTypeRepository exerciseTypeRepository;

    public CategoryDto createCategory(CategoryDto categoryDto) {

            if (categoryRepository.existsByName(categoryDto.getName())) {
                throw new ConflictException(
                        "Category with the name '" + categoryDto.getName() + "' already exists.");
            }

            CategoryEntity createdCategory = categoryRepository.save(categoryMapper.mapFromDto(categoryDto));
            return categoryMapper.mapToDto(createdCategory);

    }


    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream().map(categoryMapper::mapToDto).toList();
    }

    public void deleteAll() {
        categoryRepository.deleteAll();
    }

    public void deleteById(UUID categoryId) {

        CategoryEntity category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "Category with the ID %s not found.", categoryId.toString())));

        for (ExerciseTypeEntity exerciseType : category.getExerciseTypes()) {
            exerciseType.getCategories().remove(category);
            exerciseTypeRepository.save(exerciseType);
        }

        categoryRepository.deleteById(categoryId);
    }
}
