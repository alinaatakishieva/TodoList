package com.company.toDoList.service;

import com.company.toDoList.dto.TodoCreateDto;
import com.company.toDoList.dto.TodoDto;
import com.company.toDoList.dto.TodoUpdateDto;
import com.company.toDoList.entities.TodoEntity;
import com.company.toDoList.entities.UserEntity;
import com.company.toDoList.enums.TaskStatus;
import com.company.toDoList.repository.TodoRepo;
import com.company.toDoList.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoService {

    private final TodoRepo todoRepo;
    private final UserRepo userRepo;

    @Autowired
    public TodoService(TodoRepo todoRepo, UserRepo userRepo) {
        this.todoRepo = todoRepo;
        this.userRepo = userRepo;
    }

    public TodoDto create(Long id, TodoCreateDto todoCreateDto) {
        UserEntity user = userRepo.findById(id).orElse(null);

        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }

        TodoEntity todo = new TodoEntity();
        todo.setTask(todoCreateDto.getTask());
        todo.setUser(user);
        todo.setStatus(TaskStatus.CREATED);

        TodoEntity createdTodoEntity = todoRepo.save(todo);

        return new TodoDto(createdTodoEntity.getId(), createdTodoEntity.getTask(), createdTodoEntity.getStatus());
    }

    public TodoDto update(Long id, Long userId, TodoUpdateDto todoUpdateDto) {
        TodoEntity todo = todoRepo.findById(id).orElse(null);

        if (todo == null) {
            throw new EntityNotFoundException("Todo list not found");
        }

        UserEntity user = userRepo.findById(userId).orElse(null);

        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }

        todo.setTask(todoUpdateDto.getTask());
        todo.setStatus(todoUpdateDto.getStatus());

        TodoEntity updatingTodo = todoRepo.save(todo);

        return new TodoDto(updatingTodo.getId(), updatingTodo.getTask(), updatingTodo.getStatus());
    }

    public void deleteTodoListById(Long id) {
        todoRepo.deleteById(id);
    }

    public List<TodoDto> findByUserId(Long id) {
        UserEntity user = userRepo.findById(id).orElse(null);

        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }

        List<TodoEntity> todos = todoRepo.findAllByUser(user);
        return todos
                .stream()
                .map(it -> new TodoDto(it.getId(), it.getTask(), it.getStatus()))
                .collect(Collectors.toList());
    }

    public TodoDto startBeingInProcess(Long id){
        TodoEntity todo = todoRepo.findById(id).orElse(null);

        if (todo == null){
            throw new EntityNotFoundException("Task not found");
        }

        if (todo.getStatus().equals(TaskStatus.IN_PROGRESS) || todo.getStatus().equals(TaskStatus.DONE)){
            throw new IllegalArgumentException("Task status should be new created");
        }

        todo.setStatus(TaskStatus.IN_PROGRESS);
        todo.startOfExecuting = LocalDateTime.now();

        TodoEntity todoInProgress = todoRepo.save(todo);

        return new TodoDto(todoInProgress.getId(), todoInProgress.getTask(), todoInProgress.getStatus());
    }

    public TodoDto finishBeingInProcess(Long id){
        TodoEntity todo = todoRepo.findById(id).orElse(null);

        if (todo == null){
            throw new EntityNotFoundException("Task not found");
        }

        if (todo.getStatus().equals(TaskStatus.CREATED) || todo.getStatus().equals(TaskStatus.DONE)){
            throw new IllegalArgumentException("Task status should be in process");
        }

        todo.setStatus(TaskStatus.DONE);
        todo.executingCompleted = LocalDateTime.now();

        TodoEntity todoInProgress = todoRepo.save(todo);

        return new TodoDto(todoInProgress.getId(), todoInProgress.getTask(), todoInProgress.getStatus());
    }
}