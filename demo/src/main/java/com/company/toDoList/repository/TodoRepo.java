package com.company.toDoList.repository;

import com.company.toDoList.dto.TodoDto;
import com.company.toDoList.entities.TodoEntity;
import com.company.toDoList.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepo extends JpaRepository<TodoEntity, Long> {
    List<TodoEntity> findAllByUser(UserEntity userEntity);
}
