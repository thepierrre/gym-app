package com.example.gymapp.domain.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "exercise_types")
public class ExerciseTypeEntity {

    @Id
    @UuidGenerator
    private UUID id;

    private String name;

    @OneToMany(mappedBy = "exerciseType", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("exerciseType")
    @JsonIgnore
    private List<ExerciseInstanceEntity> exerciseInstances;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private UserEntity user;

}
