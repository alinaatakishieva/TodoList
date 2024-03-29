package com.company.toDoList;

import com.company.toDoList.properties.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(FileStorageProperties.class)
public class ToDoListApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToDoListApplication.class, args);
    }
}
