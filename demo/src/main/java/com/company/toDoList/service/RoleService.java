package com.company.toDoList.service;

import com.company.toDoList.dto.RoleCreateDto;
import com.company.toDoList.dto.RoleDto;
import com.company.toDoList.dto.RoleUpdateDto;
import com.company.toDoList.entities.RoleEntity;
import com.company.toDoList.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private final RoleRepo roleRepo;

    @Autowired
    public RoleService(RoleRepo roleRepo){
        this.roleRepo = roleRepo;
    }

    @Transactional
    public RoleDto createRole(RoleCreateDto roleCreateDto){
        RoleEntity role = new RoleEntity();

        role.setName(roleCreateDto.getName());

        RoleEntity createdRole = roleRepo.save(role);

        return new RoleDto(createdRole.getId(), createdRole.getName(), createdRole.getPermissions());
    }

    public RoleDto findById(Long id){
        RoleEntity role = roleRepo.findById(id).orElse(null);

        if (role == null){
            throw new EntityNotFoundException("Role not found");
        }

        return new RoleDto(role.getId(), role.getName(), role.getPermissions());
    }

    public List<RoleDto> findAll (){
        return roleRepo.findAll()
                .stream()
                .map(roleEntity -> new RoleDto(roleEntity.getId(), roleEntity.getName(), roleEntity.getPermissions()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteRoleById(Long id){
        roleRepo.deleteById(id);

        if (id == null){
            throw new IllegalArgumentException("Role with id " + id + " not found");
        }
    }

    @Transactional
    public RoleDto updateRole(Long id, RoleUpdateDto roleUpdateDto){
        RoleEntity role = roleRepo.findById(id).orElse(null);

        if (role == null){
            throw new EntityNotFoundException("Role not found");
        }

        role.setName(roleUpdateDto.getName());

        RoleEntity updatedRole = roleRepo.save(role);

        return new RoleDto(updatedRole.getId(), updatedRole.getName(), updatedRole.getPermissions());
    }
}
