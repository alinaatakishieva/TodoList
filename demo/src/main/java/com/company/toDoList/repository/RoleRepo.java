package com.company.toDoList.repository;

import com.company.toDoList.entities.RoleEntity;
import com.company.toDoList.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepo extends JpaRepository<RoleEntity, Long> {
    List<RoleEntity> findAllByUsers(UserEntity userEntity);
}
