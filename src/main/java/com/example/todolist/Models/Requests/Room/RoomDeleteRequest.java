package com.example.todolist.Models.Requests.Room;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RoomDeleteRequest {
    @JsonProperty("id")
    private int id;
}
