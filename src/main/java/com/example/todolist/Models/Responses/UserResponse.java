package com.example.todolist.Models.Responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserResponse {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("username")
    private String name;

    @JsonProperty("status")
    private Boolean status;
}
