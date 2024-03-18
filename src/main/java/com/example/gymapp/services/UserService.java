package com.example.gymapp.services;
import com.example.gymapp.domain.entities.UserEntity;
import java.util.List;
import java.util.UUID;

public interface UserService {

    UserEntity createUser(UserEntity userEntity);

    List<UserEntity> findAll();

    void deleteById(UUID id);

    void deleteAll();
    UserEntity update(UUID id, UserEntity userEntity);
    boolean isExists(UUID id);


}