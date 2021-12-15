package com.company.toDoList.service;

import com.company.toDoList.dto.PermissionCreateDto;
import com.company.toDoList.dto.PermissionDto;
import com.company.toDoList.dto.PermissionUpdateDto;
import com.company.toDoList.entities.PermissionEntity;
import com.company.toDoList.repository.PermissionRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionService {

    private final PermissionRepo permissionRepo;

    public PermissionService(PermissionRepo permissionRepo) {
        this.permissionRepo = permissionRepo;
    }

    @Transactional
    public PermissionDto create(PermissionCreateDto permissionCreateDto) {
        PermissionEntity permission = new PermissionEntity();
        permission.setName(permissionCreateDto.getName());

        permissionRepo.save(permission);
        return new PermissionDto(permission.getId(), permission.getName());
    }

    public List<PermissionDto> findAll() {
        return permissionRepo.findAll()
                .stream()
                .map(permissionEntity -> new PermissionDto(permissionEntity.getId(), permissionEntity.getName()))
                .collect(Collectors.toList());
    }

    public PermissionDto findById(Long id) {
        PermissionEntity permission = permissionRepo.findById(id).orElse(null);

        if (id == null) {
            throw new EntityNotFoundException("Permission not found");
        }

        return new PermissionDto(permission.getId(), permission.getName());
    }

    @Transactional
    public PermissionDto update(Long id, PermissionUpdateDto permissionUpdateDto){
        PermissionEntity permission = permissionRepo.findById(id).orElse(null);

        if (id==null){
            throw new EntityNotFoundException("Permission not found");
        }

        permission.setName(permissionUpdateDto.getName());

        PermissionEntity updatedPermission = permissionRepo.save(permission);

        return new PermissionDto(updatedPermission.getId(), updatedPermission.getName());
    }

    @Transactional
    public void delete(Long id){
        permissionRepo.deleteById(id);

        if (id == null){
            throw new IllegalArgumentException("Role with id " + id + " not found");
        }
    }

}
