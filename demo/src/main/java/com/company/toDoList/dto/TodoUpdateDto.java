package com.company.toDoList.dto;

import com.company.toDoList.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoUpdateDto {
    private String task;
    private boolean done;
    private TaskStatus status;
}
