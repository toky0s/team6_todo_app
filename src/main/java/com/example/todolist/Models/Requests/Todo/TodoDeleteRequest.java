package com.example.todolist.Models.Requests.Todo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TodoDeleteRequest {

    @JsonProperty("id")
    private Integer id;
}
