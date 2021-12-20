package com.company.toDoList.controllers;


import com.company.toDoList.dto.UserCreateDto;
import com.company.toDoList.dto.UserDto;
import com.company.toDoList.dto.UserUpdateDto;
import com.company.toDoList.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize(("hasAnyAuthority('ROLE_ADMIN')"))
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    public UserDto findById(@PathVariable Long id) {
        if (id == null) {
            throw new EntityNotFoundException("User with id " + id + " not found");
        }

        return userService.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public UserDto create(@RequestBody UserCreateDto userCreateDto) {
        return userService.create(userCreateDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER')")
    public UserDto update(@PathVariable("id") Long id, @RequestBody UserUpdateDto userUpdateDto) {
        if (id == null) {
            throw new EntityNotFoundException("User with id " + id + " not found");
        }

        return userService.update(id, userUpdateDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public void deleteUser(@PathVariable("id") Long id) {
        if (id == null) {
            throw new EntityNotFoundException("User with id " + id + "not found");
        }

        userService.deleteById(id);
    }

//    @PostMapping("/{id}/addRole/{id}")
//    public UserEntity addRole(@PathVariable("id") Long id, @RequestBody Set<Long> roleIds){
//        if (id == null){
//            throw new IllegalArgumentException("User not found");
//        }
//
//        return userService.addRole(id, roleIds);
//    }

}

