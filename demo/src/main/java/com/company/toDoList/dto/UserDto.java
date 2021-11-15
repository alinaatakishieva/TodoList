package com.company.toDoList.dto;

import com.company.toDoList.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {
    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private Roles role;
    private List<TodoDto> todos;
}
