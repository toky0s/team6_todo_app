package com.example.todolist.Models.Requests.Room;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RoomRequest {
    @JsonProperty("name")
    private String name;

    @JsonProperty("password")
    private String password;
}
