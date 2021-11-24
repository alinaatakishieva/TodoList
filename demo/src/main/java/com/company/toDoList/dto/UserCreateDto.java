package com.company.toDoList.dto;

import com.company.toDoList.entities.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserCreateDto {
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private Set<RoleEntity> roles;
}
