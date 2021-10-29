package com.company.toDoList.entities;

import com.company.toDoList.enums.TaskStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "todo")
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

    @Temporal(TemporalType.TIME)
    @Column(name = "start_of_executing")
    public LocalDateTime startOfExecuting;

    @Temporal(TemporalType.TIME)
    @Column(name = "executing_completed")
    public LocalDateTime executingCompleted;
}