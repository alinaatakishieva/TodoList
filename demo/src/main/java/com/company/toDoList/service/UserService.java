package com.company.toDoList.service;

import com.company.toDoList.entities.UserEntity;
import com.company.toDoList.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class UserService {

    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public UserEntity findById(Long id) {
        return userRepo.getOne(id);
    }

    public List<UserEntity> findAll() {
        return userRepo.findAll();
    }

    public UserEntity createUser(UserEntity userEntity) {
        return userRepo.save(userEntity);
    }

    public void deleteById(Long id) {
        userRepo.deleteById(id);
    }

    public UserEntity update(UserEntity userEntity) {
        return userRepo.save(userEntity);
    }
}
