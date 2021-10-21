package com.company.toDoList.controllers;


import com.company.toDoList.dto.TodoDto;
import com.company.toDoList.dto.UserCreateDto;
import com.company.toDoList.dto.UserDto;
import com.company.toDoList.dto.UserUpdateDto;
import com.company.toDoList.entities.UserEntity;
import com.company.toDoList.service.TodoService;
import com.company.toDoList.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final TodoService todoService;

    @Autowired
    public UserController(UserService userService, TodoService todoService) {
        this.userService = userService;
        this.todoService = todoService;
    }

    @GetMapping
    public List<UserDto> findAll() {
        return userService.findAll()
                .stream()
                .map(userEntity -> new UserDto(userEntity.getId(), userEntity.getFirstname(), userEntity.getLastname(), convertToTodoDto(userEntity)))
                .collect(Collectors.toList());
    }

    private List<TodoDto> convertToTodoDto(UserEntity userEntity) {
        return todoService.findByUser(userEntity)
                .stream()
                .map(todoEntity -> new TodoDto(todoEntity.getId(), todoEntity.getTask(), todoEntity.isDone()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable Long id) {
        UserEntity userEntity = userService.findById(id);
        if (userEntity == null) {
            throw new EntityNotFoundException("Not found");
        }
        return new UserDto(userEntity.getId(), userEntity.getFirstname(), userEntity.getLastname(), convertToTodoDto(userEntity));
    }

    @PostMapping
    public UserDto create(@RequestBody UserCreateDto userCreateDto) {
        UserEntity user = new UserEntity();
        user.setFirstname(userCreateDto.getFirstname());
        user.setLastname(userCreateDto.getLastname());

        UserEntity createdUser = userService.createUser(user);

        return new UserDto(createdUser.getId(), createdUser.getFirstname(), createdUser.getLastname(), convertToTodoDto(user));
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable("id") Long id, @RequestBody UserUpdateDto userUpdateDto) {
        UserEntity user = userService.findById(id);
        if (user == null) {
            throw new EntityNotFoundException("Not found");
        }
        user.setFirstname(userUpdateDto.getFirstname());
        user.setLastname(userUpdateDto.getLastname());
        UserEntity updatingUser = userService.update(user);

        return new UserDto(updatingUser.getId(), updatingUser.getFirstname(), updatingUser.getLastname(), convertToTodoDto(user));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
    }

}
