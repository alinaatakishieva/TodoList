package com.company.toDoList.repository;

import com.company.toDoList.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {
//    UserEntity createUser(UserEntity userEntity);
}
