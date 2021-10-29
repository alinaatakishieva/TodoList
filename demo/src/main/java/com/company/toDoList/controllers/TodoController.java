package com.company.toDoList.controllers;

import com.company.toDoList.dto.TodoCreateDto;
import com.company.toDoList.dto.TodoDto;
import com.company.toDoList.dto.TodoUpdateDto;
import com.company.toDoList.entities.TodoEntity;
import com.company.toDoList.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/users/{userId}/todos")
public class TodoController {

    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
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
    public TodoDto update(@PathVariable Long userId, @PathVariable("id") Long id, @RequestBody TodoUpdateDto todoUpdateDto) {
        return todoService.update(userId, id, todoUpdateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable("id") Long id) {
        todoService.deleteTodoListById(id);
    }

    @PostMapping("/id/start")
    public TodoDto executionStart(@PathVariable("id") Long id) {
        return todoService.startBeingInProcess(id);
    }

    @PostMapping("/id/finish")
    public TodoDto executionFinish(@PathVariable("id") Long id) {
        return todoService.finishBeingInProcess(id);
    }
}