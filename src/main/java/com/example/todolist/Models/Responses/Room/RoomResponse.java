package com.example.todolist.Models.Responses.Room;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class RoomResponse {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("password")
    private String password;

    @JsonProperty("userId")
    private Integer userId;

    @JsonProperty("username")
    private String username;
}
