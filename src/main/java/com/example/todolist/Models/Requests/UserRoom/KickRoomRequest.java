package com.example.todolist.Models.Requests.UserRoom;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KickRoomRequest {

    @JsonProperty("user-id")
    private Integer userId;

    @JsonProperty("room-id")
    private Integer roomId;
}
