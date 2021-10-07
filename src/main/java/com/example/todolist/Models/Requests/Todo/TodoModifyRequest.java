package com.example.todolist.Models.Requests.Todo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TodoModifyRequest {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("title")
    private String name;

    @JsonProperty("detail")
    private String detail;

    @JsonProperty("status")
    private Integer status;
}
