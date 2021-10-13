package com.company.toDoList.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "todo")
@Data
public class Todo {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "task")
    private String task;
    @Column(name = "is_done")
    private boolean done;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
