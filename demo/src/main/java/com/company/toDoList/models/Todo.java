package com.company.toDoList.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    public String task;

    @Column(name = "is_done")
    public boolean done;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    public User user;

    public void assignUser(User user) {
        this.user = user;
    }
}
