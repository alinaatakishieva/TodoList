package com.company.toDoList.dto;

import com.company.toDoList.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserUpdateDto {
    private String firstname;
    private String lastname;
    private Roles role;
}
