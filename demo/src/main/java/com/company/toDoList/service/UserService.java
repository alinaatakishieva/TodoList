package com.company.toDoList.service;

import com.company.toDoList.dto.TodoDto;
import com.company.toDoList.dto.UserCreateDto;
import com.company.toDoList.dto.UserDto;
import com.company.toDoList.dto.UserUpdateDto;
import com.company.toDoList.entities.UserEntity;
import com.company.toDoList.repository.TodoRepo;
import com.company.toDoList.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final TodoRepo todoRepo;

    @Autowired
    public UserService(UserRepo userRepo, TodoRepo todoRepo) {
        this.userRepo = userRepo;
        this.todoRepo = todoRepo;
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
                .map(todoEntity -> new TodoDto(todoEntity.getId(), todoEntity.getTask(), todoEntity.isDone()))
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
        UserEntity updatingUser = userRepo.save(user);

        return new UserDto(updatingUser.getId(), updatingUser.getFirstname(), updatingUser.getLastname(), convertToTodoDto(user));
    }
}
