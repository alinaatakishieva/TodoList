package com.company.toDoList.controllers;

import com.company.toDoList.dto.RoleCreateDto;
import com.company.toDoList.dto.RoleDto;
import com.company.toDoList.dto.RoleUpdateDto;
import com.company.toDoList.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public RoleDto createRole(@PathVariable RoleCreateDto roleCreateDto) {
        return roleService.createRole(roleCreateDto);
    }

    @GetMapping("/{id}")
    public RoleDto findById(@PathVariable Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Role with id " + id + " not found");
        }

        return roleService.findById(id);
    }

    @GetMapping
    public List<RoleDto> findAll() {
        return roleService.findAll();
    }

    @PutMapping("/{id}")
    public RoleDto update(@PathVariable("id") Long id, @RequestBody RoleUpdateDto roleUpdateDto) {
        if (id == null) {
            throw new IllegalArgumentException("Role with id " + id + " not found");
        }

        return roleService.updateRole(id, roleUpdateDto);
    }

    @DeleteMapping("/{id}")
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Role with id " + id + " not found");
        }

        roleService.deleteRoleById(id);
    }
}