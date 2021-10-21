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
        UserEntity user = userService.findById(userId);
        if(user == null){
            throw new EntityNotFoundException("User not found");
        }
        List<TodoEntity> todos = todoService.findByUser(user);
        return todos
                .stream()
                .map(it -> new TodoDto(it.getId(), it.getTask(), it.isDone()))
                .collect(Collectors.toList());
    }

    @PostMapping
    public TodoDto create(@PathVariable Long userId, @RequestBody TodoCreateDto todoCreateDto) {
        UserEntity user = userService.findById(userId);
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }
        TodoEntity todoEntity = new TodoEntity();
        todoEntity.setTask(todoCreateDto.getTask());
        todoEntity.setDone(false);
        todoEntity.setUser(user);
        TodoEntity createdTodoEntity = todoService.create(todoEntity);
        return new TodoDto(createdTodoEntity.getId(), createdTodoEntity.getTask(), createdTodoEntity.isDone());
    }

    @PutMapping("/{id}")
    public TodoDto update(@PathVariable("id") Long id, @PathVariable Long userId, @RequestBody TodoUpdateDto todoUpdateDto) {
        TodoEntity todo = todoService.findById(id);
        if (todo == null) {
            throw new EntityNotFoundException("Todo list not found");
        }
        UserEntity user = userService.findById(userId);
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }
        todo.setTask(todoUpdateDto.getTask());
        todo.setDone(todoUpdateDto.isDone());

        TodoEntity updatingTodo = todoService.update(todo);
        
        return new TodoDto(updatingTodo.getId(), updatingTodo.getTask(), updatingTodo.isDone());
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable("id") Long id) {
        todoService.deleteTodoListById(id);
    }
}
