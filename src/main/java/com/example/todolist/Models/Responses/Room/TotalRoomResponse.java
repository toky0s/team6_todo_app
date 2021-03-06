package com.example.todolist.Models.Responses.Room;

import com.example.todolist.Models.Responses.Room.RoomResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TotalRoomResponse {
    @JsonProperty("created-rooms")
    private List<RoomResponse> createdRooms;

    @JsonProperty("joined-rooms")
    private  List<RoomResponse> joinedRooms;
}
