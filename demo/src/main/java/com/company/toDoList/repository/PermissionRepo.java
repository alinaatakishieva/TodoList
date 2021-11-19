package com.company.toDoList.repository;

import com.company.toDoList.entities.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PermissionRepo extends JpaRepository<PermissionEntity, Long> {
}
