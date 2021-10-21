package com.company.toDoList.service;

import com.company.toDoList.entities.TodoEntity;
import com.company.toDoList.entities.UserEntity;
import com.company.toDoList.repository.TodoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService  {

    private final TodoRepo todoRepo;

    @Autowired
    public TodoService(TodoRepo todoRepo) {
        this.todoRepo = todoRepo;
    }

    public TodoEntity create(TodoEntity todoEntity) {
        return todoRepo.save(todoEntity);
    }

    public TodoEntity update(TodoEntity todoEntity) {
        return todoRepo.save(todoEntity);
    }

    public List<TodoEntity> findAll() {
        return todoRepo.findAll();
    }

    public TodoEntity findById(Long id) {
        return todoRepo.getOne(id);
    }

    public void deleteTodoListById(Long id) {
        todoRepo.deleteById(id);
    }

    public List<TodoEntity> findByUser(UserEntity userEntity){
        return todoRepo.findAllByUser(userEntity);
    }
}
