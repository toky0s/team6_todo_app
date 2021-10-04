package com.example.todolist.Models.Requests.UserRoom;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LeaveRoomRequest {
    @JsonProperty("room-id")
    private Integer id;
}
