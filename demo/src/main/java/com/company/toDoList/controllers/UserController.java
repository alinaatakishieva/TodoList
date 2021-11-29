package com.company.toDoList.controllers;


import com.company.toDoList.dto.UserCreateDto;
import com.company.toDoList.dto.UserDto;
import com.company.toDoList.dto.UserUpdateDto;
import com.company.toDoList.entities.RoleEntity;
import com.company.toDoList.entities.UserEntity;
import com.company.toDoList.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

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
    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MANAGER"})
    public UserDto findById(@PathVariable Long id) {
        if (id == null) {
            throw new EntityNotFoundException("User with id " + id + " not found");
        }

        return userService.findById(id);
    }

    @PostMapping
    @RolesAllowed("hasRole('ROLE_ADMIN')")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserDto create(@RequestBody UserCreateDto userCreateDto) {
        return userService.create(userCreateDto);
    }

    @PutMapping("/{id}")
    @RolesAllowed("hasRole('ROLE_ADMIN')")
    @PreAuthorize("hasRole(['ROLE_ADMIN', 'ROLE_MANAGER'])")
    public UserDto update(@PathVariable("id") Long id, @RequestBody UserUpdateDto userUpdateDto) {
        if (id == null) {
            throw new EntityNotFoundException("User with id " + id + " not found");
        }

        return userService.update(id, userUpdateDto);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed("hasRole('ROLE_ADMIN')")
    public void deleteUser(@PathVariable("id") Long id) {
        if (id == null) {
            throw new EntityNotFoundException("User with id " + id + "not found");
        }

        userService.deleteById(id);
    }

    @PostMapping("/{id}/addRole")
    public UserEntity addRole(@PathVariable("id") Long id, @RequestBody Set<RoleEntity> role){
        if (id == null){
            throw new IllegalArgumentException("User not found");
        }

        return userService.addRole(id, role);
    }

}

