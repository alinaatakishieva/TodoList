package com.company.toDoList.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TaskStatus {
    @JsonProperty("in progress")
    IN_PROGRESS,

    @JsonProperty("done")
    DONE,
    
    @JsonProperty("created")
    CREATED
}
