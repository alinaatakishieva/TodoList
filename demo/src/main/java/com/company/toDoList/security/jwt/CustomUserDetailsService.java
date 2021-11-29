package com.company.toDoList.security.jwt;

import com.company.toDoList.entities.UserEntity;
import com.company.toDoList.repository.UserRepo;
import com.company.toDoList.service.UserService;
import net.bytebuddy.implementation.bytecode.constant.DefaultValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {


    private final UserService userService;
    private final UserRepo userRepo;

    @Autowired
    public CustomUserDetailsService(UserService userService, UserRepo userRepo) {
        this.userService = userService;
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> optionalUsers = Optional.ofNullable(userService.findByUsername(username));

        optionalUsers
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return optionalUsers
                .map(CustomUserDetails::new).get();
    }

//    @Override
//    public Optional<UserDetails> loadUserByUsername(String username) throws UsernameNotFoundException{
//        UserEntity user = userRepo
//                .findByUsername(username);
//        if (user == null){
//            throw new UsernameNotFoundException("User not found");
//        }
//    }
}
