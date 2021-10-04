package com.example.todolist.Models.Requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RequestUser {

    @JsonProperty("name")
    private String name;

    @JsonProperty("password")
    private String password;
}
