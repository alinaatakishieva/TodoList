package com.company.toDoList.dto;

import com.company.toDoList.entities.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserUpdateDto {
    private String firstname;
    private String lastname;
    private Set<RoleEntity> roles;
}
