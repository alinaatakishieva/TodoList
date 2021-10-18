package com.company.toDoList.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstname")
    public String firstname;

    @Column(name = "lastname")
    public String lastname;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    public Set<Todo> todos = new HashSet<>();

    public void assignTodo(Todo todo) {
        this.todos = (Set<Todo>) todo;
    }
}
