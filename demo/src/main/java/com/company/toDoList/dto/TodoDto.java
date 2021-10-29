package com.company.toDoList.dto;

import com.company.toDoList.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TodoDto {
    private Long id;
    private String task;
    private TaskStatus status;
}
