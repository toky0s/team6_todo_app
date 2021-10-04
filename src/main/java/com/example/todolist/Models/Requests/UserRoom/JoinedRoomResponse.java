package com.example.todolist.Models.Requests.UserRoom;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class JoinedRoomResponse {
    @JsonProperty("room-id")
    private Integer roomId;

    @JsonProperty("status")
    private Boolean status;
}
