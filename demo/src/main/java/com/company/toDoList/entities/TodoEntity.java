package com.company.toDoList.entities;

import com.company.toDoList.enums.TaskStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "todos")
@Data
public class TodoEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "task")
    public String task;

    @Enumerated
    @Column(name = "status")
    public TaskStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    public UserEntity user;

    @Column(name = "start_of_executing")
    public LocalDateTime startOfExecuting;

    @Column(name = "end_of_executing")
    public LocalDateTime executingCompleted;
}