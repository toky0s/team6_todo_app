package com.example.todolist.Models.Requests.Todo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TodoRequest {
    @JsonProperty("title")
    private String name;

    @JsonProperty("detail")
    private String detail;

    @JsonProperty("publicDate")
    private String publicDate;

    @JsonProperty("status")
    private Boolean status;

    @JsonProperty("boardId")
    private Integer boardId;
}
