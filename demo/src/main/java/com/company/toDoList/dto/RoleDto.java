package com.company.toDoList.dto;

import com.company.toDoList.entities.PermissionEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoleDto {
    private Long id;
    private String name;
    private Collection<PermissionEntity> permissions;
}
