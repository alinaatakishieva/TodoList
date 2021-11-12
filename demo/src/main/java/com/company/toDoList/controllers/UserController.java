package com.company.toDoList.controllers;


import com.company.toDoList.dto.UserCreateDto;
import com.company.toDoList.dto.UserDto;
import com.company.toDoList.dto.UserUpdateDto;
import com.company.toDoList.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable Long id) {
        if (id == null) {
            throw new EntityNotFoundException("User with id " + id + " not found");
        }

        return userService.findById(id);
    }

    @PostMapping
    public UserDto create(@RequestBody UserCreateDto userCreateDto) {
        return userService.create(userCreateDto);
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable("id") Long id, @RequestBody UserUpdateDto userUpdateDto) {
        if (id == null) {
            throw new EntityNotFoundException("User with id " + id + " not found");
        }

        return userService.update(id, userUpdateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        if (id == null) {
            throw new EntityNotFoundException("User with id " + id + "not found");
        }

        userService.deleteById(id);
    }

}
