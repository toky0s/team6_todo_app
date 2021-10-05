package com.example.todolist.Models.Requests.Todo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TodoRequest {
    @JsonProperty("name")
    private String name;

    @JsonProperty("detail")
    private String detail;

    @JsonProperty("publicDate")
    private String publicDate;

    @JsonProperty("status")
    private Integer status;

    @JsonProperty("boardId")
    private Integer boardId;
}
