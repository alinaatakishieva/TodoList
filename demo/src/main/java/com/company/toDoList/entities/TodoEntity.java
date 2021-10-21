package com.company.toDoList.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "todo")
@Data
public class TodoEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "task")
    public String task;

    @Column(name = "is_done")
    public boolean done;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    public UserEntity user;
}
