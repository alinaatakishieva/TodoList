package com.company.toDoList.controllers;

import com.company.toDoList.models.Todo;
import com.company.toDoList.models.User;
import com.company.toDoList.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class TodoController {

    @Autowired
    private TodoService todoService;


    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/todo")
    public String findAll(Model model){
        List<Todo> todo = todoService.findAll();
        model.addAttribute("todo", todo);
        return "todo-list";
    }

    @GetMapping("/task-create")
    public String createTaskForm(Todo todo){
        return "user-task";
    }

    @PostMapping("/task-create")
    public String createTask(Todo todo){
        todoService.createTodoList(todo);
        return "redirect:/todo";
    }

    @GetMapping("todo-update/{id}")
    public String updateTodoForm(@PathVariable("id") Long id, Model model){
        Todo todo = todoService.findById(id);
        model.addAttribute("todo", todo);
        return "/todo-update";
    }

    @PostMapping("todo-update")
    public String updateTodo(Todo todo){
        todoService.updateTodoList(todo);
        return "redirect:/todo";
    }

    @GetMapping("/todo-delete/{id}")
    public String deleteTodo(@PathVariable("id") Long id){
        todoService.deleteTodoListById(id);
        return "redirect:/todo";
    }

}
