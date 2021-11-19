package com.company.toDoList.controllers;

import com.company.toDoList.dto.PermissionCreateDto;
import com.company.toDoList.dto.PermissionDto;
import com.company.toDoList.dto.PermissionUpdateDto;
import com.company.toDoList.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
public class PermissionController {

    private final PermissionService permissionService;

    @Autowired
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping
    public PermissionDto create(@RequestBody PermissionCreateDto permissionCreateDto) {
        return permissionService.create(permissionCreateDto);
    }

    @GetMapping
    public List<PermissionDto> findAll() {
        return permissionService.findAll();
    }

    @GetMapping("{id}")
    public PermissionDto findById(@PathVariable("id") Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Permission with id " + id + " not found");
        }

        return permissionService.findById(id);
    }

    @PutMapping("{id}")
    public PermissionDto update(@PathVariable("id") Long id, @RequestBody PermissionUpdateDto permissionUpdateDto) {
        if (id == null) {
            throw new IllegalArgumentException("Permission with id " + id + " not found");
        }

        return permissionService.update(id, permissionUpdateDto);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Permission with id " + id + " not found");
        }

        permissionService.delete(id);
    }
}
