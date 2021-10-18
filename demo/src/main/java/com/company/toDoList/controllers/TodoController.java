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
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;
    private final UserService userService;

    @Autowired
    public TodoController(TodoService todoService, UserService userService) {
        this.todoService = todoService;
        this.userService = userService;
    }

    @GetMapping
    public List<Todo> findAll() {
        return todoService.findAll();
    }

    @GetMapping("/{id}")
    public Todo findById(Long id) {
        Todo todo = todoService.findById(id);
        if (todo == null){
            throw new EntityNotFoundException("Not found");
        }
        return todo;
    }

    @PostMapping
    public Todo createTodo(@RequestBody Todo todo) {
        return todoService.createTodoList(todo);
    }

    @PutMapping("/{id}")
    public Todo updateTodoList(@PathVariable("id") Long id, @RequestBody Todo todo) {
        Todo updatingTodo = todoService.findById(id);
        if (todo == null) {
            throw new EntityNotFoundException("Todo list not found");
        }
        updatingTodo.setTask(todo.getTask());
        updatingTodo.setUser(todo.getUser());
        return todoService.updateTodoList(updatingTodo);
    }

    @PutMapping("/{id}/users/{id}")
    public Todo userTodo(@PathVariable Long todoId, @PathVariable Long userId){
        Todo todo = todoService.findById(todoId);
        User user = userService.findById(userId);
        todo.assignUser(user);
        return todoService.updateTodoList(todo);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable("id") Long id) {
        todoService.deleteTodoListById(id);
    }
}
