package com.example.todolist.Models.Requests.Board;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BoardModifyRequest {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;
}
