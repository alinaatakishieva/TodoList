package com.company.toDoList.controllers;


import com.company.toDoList.models.Todo;
import com.company.toDoList.models.User;
import com.company.toDoList.service.TodoService;
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
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User findById(Long id) {
        User user = userService.findById(id);
        if (user == null) {
            throw new EntityNotFoundException("Not found");
        }
        return user;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public User updateUserForm(@PathVariable("id") Long id, @RequestBody User user) {
        User updatingUser = userService.findById(id);
        if (user == null) {
            throw new EntityNotFoundException("Not found");
        }
        updatingUser.setFirstname(user.getFirstname());
        updatingUser.setLastname(user.getLastname());
        return userService.update(updatingUser);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
    }
}
