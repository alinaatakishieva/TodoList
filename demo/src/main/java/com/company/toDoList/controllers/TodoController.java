package com.company.toDoList.controllers;

import com.company.toDoList.dto.TodoCreateDto;
import com.company.toDoList.dto.TodoDto;
import com.company.toDoList.dto.TodoUpdateDto;
import com.company.toDoList.entities.TodoEntity;
import com.company.toDoList.entities.UserEntity;
import com.company.toDoList.service.TodoService;
import com.company.toDoList.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users/{userId}/todos")
public class TodoController {

    private final TodoService todoService;
    private final UserService userService;

    @Autowired
    public TodoController(TodoService todoService, UserService userService) {
        this.todoService = todoService;
        this.userService = userService;
    }

    @GetMapping
    public List<TodoDto> findByUserId(@PathVariable Long userId) {
       return todoService.findByUserId(userId);
    }

    @PostMapping
    public TodoDto create(@PathVariable Long userId, @RequestBody TodoCreateDto todoCreateDto) {
        return todoService.create(userId, todoCreateDto);
    }

    @PutMapping("/{id}")
    public TodoDto update(@PathVariable("id") Long id, @PathVariable Long userId, @RequestBody TodoUpdateDto todoUpdateDto) {
        return todoService.update(id, userId, todoUpdateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable("id") Long id) {
        todoService.deleteTodoListById(id);
    }
}
