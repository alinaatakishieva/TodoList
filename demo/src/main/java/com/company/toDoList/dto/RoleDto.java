package com.company.toDoList.dto;

import com.company.toDoList.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoleDto {
    private Long id;
    private String name;
}
