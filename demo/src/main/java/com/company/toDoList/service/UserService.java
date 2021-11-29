package com.company.toDoList.service;

import com.company.toDoList.dto.*;
import com.company.toDoList.entities.RoleEntity;
import com.company.toDoList.entities.UserEntity;
import com.company.toDoList.repository.RoleRepo;
import com.company.toDoList.repository.TodoRepo;
import com.company.toDoList.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final TodoRepo todoRepo;
    private final RoleRepo roleRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepo userRepo, TodoRepo todoRepo, RoleRepo roleRepo, BCryptPasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.todoRepo = todoRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity findByUsername(String username) {
        UserEntity user = userRepo.findByUsername(username);

        if (username == null) {
            throw new UsernameNotFoundException("User with username " + username + " not found");
        }

        return user;
    }

    public UserDto findById(Long id) {
        UserEntity user = userRepo.findById(id).orElse(null);

        if (user == null) {
            throw new EntityNotFoundException("Not found");
        }

        return new UserDto(user.getId(), user.getFirstname(), user.getLastname(), user.getUsername(), user.getPassword(), convertToRoleDto(user), convertToTodoDto(user));
    }

    private List<RoleDto> convertToRoleDto(UserEntity userEntity) {
        return roleRepo.findAllByUsers(userEntity)
                .stream()
                .map(roleEntity -> new RoleDto(roleEntity.getId(), roleEntity.getName(), roleEntity.getPermissions()))
                .collect(Collectors.toList());
    }

    private List<TodoDto> convertToTodoDto(UserEntity userEntity) {
        return todoRepo.findAllByUser(userEntity)
                .stream()
                .map(todoEntity -> new TodoDto(todoEntity.getId(), todoEntity.getTask(), todoEntity.getStatus()))
                .collect(Collectors.toList());
    }

    public List<UserDto> findAll() {
        return userRepo.findAll()
                .stream()
                .map(userEntity -> new UserDto(userEntity.getId(), userEntity.getFirstname(), userEntity.getLastname(), userEntity.getUsername(), userEntity.getPassword(), convertToRoleDto(userEntity), convertToTodoDto(userEntity)))
                .collect(Collectors.toList());
    }

    public UserDto create(UserCreateDto userCreateDto) {
        UserEntity user = new UserEntity();

        user.setFirstname(userCreateDto.getFirstname());
        user.setLastname(userCreateDto.getLastname());
        user.setUsername(userCreateDto.getUsername());
        user.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        user.setRoles(userCreateDto.getRoles());

        UserEntity createdUser = userRepo.save(user);

        return new UserDto(createdUser.getId(), createdUser.getFirstname(), createdUser.getLastname(), createdUser.getUsername(), createdUser.getPassword(), convertToRoleDto(user), convertToTodoDto(user));
    }

    public void deleteById(Long id) {
        userRepo.deleteById(id);

        if (id == null) {
            throw new IllegalArgumentException("User with id " + id + " not found");
        }
    }

    public UserDto update(Long id, UserUpdateDto userUpdateDto) {
        UserEntity user = userRepo.findById(id).orElse(null);

        if (user == null) {
            throw new EntityNotFoundException("Not found");
        }

        user.setFirstname(userUpdateDto.getFirstname());
        user.setLastname(userUpdateDto.getLastname());
        user.setRoles(userUpdateDto.getRoles());

        UserEntity updatingUser = userRepo.save(user);

        return new UserDto(updatingUser.getId(), updatingUser.getFirstname(), updatingUser.getLastname(), updatingUser.getUsername(), updatingUser.getPassword(), convertToRoleDto(user), convertToTodoDto(user));
    }

    public UserEntity addRole(Long userId, Set<RoleEntity> roleId) {
        UserEntity user = userRepo.findById(userId).orElse(null);

        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }

        user.setRoles(roleId);

        UserEntity userWithRole = userRepo.save(user);

        return userWithRole;
    }
}