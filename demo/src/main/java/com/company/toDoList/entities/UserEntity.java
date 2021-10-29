package com.company.toDoList.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.management.relation.Role;
import javax.persistence.*;
import java.util.Date;

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

//    @Enumerated
    @Column(name = "role")
    public Role roles;

    @Column(name = "username")
    public String username;

    @Column(name = "password")
    public String password;

//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
//    @Column(name = "created")
//    public Date createdAt;
//
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
//    @Column(name = "updated")
//    public Date updatedAt;
}
