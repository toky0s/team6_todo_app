package com.example.todolist.Models.Requests.Board;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BoardRequest {
    @JsonProperty("name")
    private String name;
    @JsonProperty("room-id")
    private String roomId;
}
