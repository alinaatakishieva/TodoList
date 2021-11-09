package com.company.toDoList.service;

import com.company.toDoList.dto.*;
import com.company.toDoList.entities.UserEntity;
import com.company.toDoList.enums.Roles;
import com.company.toDoList.repository.TodoRepo;
import com.company.toDoList.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final TodoRepo todoRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepo userRepo, TodoRepo todoRepo, BCryptPasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.todoRepo = todoRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto findById(Long id) {
        UserEntity user = userRepo.getOne(id);
        if (user == null) {
            throw new EntityNotFoundException("Not found");
        }
        return new UserDto(user.getId(), user.getFirstname(), user.getLastname(), convertToTodoDto(user));
    }

    private List<TodoDto> convertToTodoDto(UserEntity userEntity) {
        return todoRepo.findAllByUser(userEntity)
                .stream()
                .map(todoEntity -> new TodoDto(todoEntity.getId(), todoEntity.getTask(), todoEntity.getStatus()))
                .collect(Collectors.toList());
    }

    public List<UserDto> findAll() {
        return userRepo.findAll()
                .stream()
                .map(userEntity -> new UserDto(userEntity.getId(), userEntity.getFirstname(), userEntity.getLastname(), convertToTodoDto(userEntity)))
                .collect(Collectors.toList());
    }

    public UserDto create(UserCreateDto userCreateDto) {
        UserEntity user = new UserEntity();
        user.setFirstname(userCreateDto.getFirstname());
        user.setLastname(userCreateDto.getLastname());
        user.setUsername(userCreateDto.getUsername());
        user.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        user.setRole(Roles.USER);

        UserEntity createdUser = userRepo.save(user);
        return new UserDto(createdUser.getId(), createdUser.getFirstname(), createdUser.getLastname(), convertToTodoDto(user));
    }

    public void deleteById(Long id) {
        userRepo.deleteById(id);
    }

    public UserDto update(Long id, UserUpdateDto userUpdateDto) {
        UserEntity user = userRepo.getOne(id);
        if (user == null) {
            throw new EntityNotFoundException("Not found");
        }
        user.setFirstname(userUpdateDto.getFirstname());
        user.setLastname(userUpdateDto.getLastname());
        user.setRole(userUpdateDto.getRole());
        UserEntity updatingUser = userRepo.save(user);

        return new UserDto(updatingUser.getId(), updatingUser.getFirstname(), updatingUser.getLastname(), convertToTodoDto(user));
    }
}
