package com.company.toDoList.repository;

import com.company.toDoList.entities.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepo extends JpaRepository<FileEntity, Long> {
}
