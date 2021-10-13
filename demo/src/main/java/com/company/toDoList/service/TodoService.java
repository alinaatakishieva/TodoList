package com.company.toDoList.service;

import com.company.toDoList.models.Todo;
import com.company.toDoList.repository.TodoRepo;
import com.company.toDoList.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService  {
    @Autowired
    private TodoRepo todoRepo;

    public TodoService(TodoRepo todoRepo) {
        this.todoRepo = todoRepo;
    }

    public Todo createTodoList(Todo todo) {
        return todoRepo.save(todo);
    }


    public Todo updateTodoList(Todo todo) {
        return null;
    }


    public List<Todo> findAll() {
        return todoRepo.findAll();
    }

    public Todo findById(Long id) {
        return todoRepo.getOne(id);
    }


    public void deleteTodoListById(Long id) {
        todoRepo.deleteById(id);
    }
}
