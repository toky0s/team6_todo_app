package com.example.todolist.Models.Responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class JoinedRoomResponse {
    @JsonProperty("room-id")
    private Integer roomId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("status")
    private Boolean status;
}
