package com.company.toDoList.entities;

import com.company.toDoList.enums.Roles;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstname")
    public String firstname;

    @Column(name = "lastname")
    public String lastname;

    @Column(name = "username")
    public String username;

    @Column(name = "password")
    public String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    public Roles role;
}
