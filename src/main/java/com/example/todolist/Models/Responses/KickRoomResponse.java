package com.example.todolist.Models.Responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KickRoomResponse {
    @JsonProperty("user-id")
    private Integer userId;

    @JsonProperty("username")
    private String username;
}
