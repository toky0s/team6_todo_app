package com.example.todolist.Models.Requests.UserRoom;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class JoinedRoomRequest {
    @JsonProperty("room-id")
    private Integer roomId;

    @JsonProperty("password")
    private String password;
}
