package com.company.toDoList.service;

import com.company.toDoList.dto.TodoCreateDto;
import com.company.toDoList.dto.TodoDto;
import com.company.toDoList.dto.TodoUpdateDto;
import com.company.toDoList.entities.TodoEntity;
import com.company.toDoList.entities.UserEntity;
import com.company.toDoList.repository.TodoRepo;
import com.company.toDoList.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoService  {

    private final TodoRepo todoRepo;
    private final UserRepo userRepo;

    @Autowired
    public TodoService(TodoRepo todoRepo, UserRepo userRepo) {
        this.todoRepo = todoRepo;
        this.userRepo = userRepo;
    }

    public TodoDto create(Long id, TodoCreateDto todoCreateDto) {
        UserEntity user = userRepo.getOne(id);
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }
        TodoEntity todo = new TodoEntity();
        todo.setTask(todoCreateDto.getTask());
        todo.setDone(false);
        todo.setUser(user);
        TodoEntity createdTodoEntity = todoRepo.save(todo);
        return new TodoDto(createdTodoEntity.getId(), createdTodoEntity.getTask(), createdTodoEntity.isDone());
    }

    public TodoDto update(Long id, Long userId, TodoUpdateDto todoUpdateDto) {
        TodoEntity todo = todoRepo.getOne(id);
        if (todo == null) {
            throw new EntityNotFoundException("Todo list not found");
        }
        UserEntity user = userRepo.getOne(userId);
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }
        todo.setTask(todoUpdateDto.getTask());
        todo.setDone(todoUpdateDto.isDone());

        TodoEntity updatingTodo = todoRepo.save(todo);

        return new TodoDto(updatingTodo.getId(), updatingTodo.getTask(), updatingTodo.isDone());
    }


    public TodoEntity findById(Long id) {
        return todoRepo.getOne(id);
    }

    public void deleteTodoListById(Long id) {
        todoRepo.deleteById(id);
    }

    public List<TodoDto> findByUserId(Long id){
        UserEntity user = userRepo.getOne(id);
        if(user == null){
            throw new EntityNotFoundException("User not found");
        }
        List<TodoEntity> todos = todoRepo.findAllByUser(user);
        return todos
                .stream()
                .map(it -> new TodoDto(it.getId(), it.getTask(), it.isDone()))
                .collect(Collectors.toList());    }
}
