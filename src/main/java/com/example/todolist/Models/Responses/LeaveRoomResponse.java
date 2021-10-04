package com.example.todolist.Models.Responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LeaveRoomResponse {
    @JsonProperty("room-id")
    private Integer id;
    @JsonProperty("name")
    private String name;
}
