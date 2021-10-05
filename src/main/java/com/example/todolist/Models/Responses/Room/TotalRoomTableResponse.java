package com.example.todolist.Models.Responses.Room;

import com.example.todolist.Models.Responses.Board.BoardResponse;
import com.example.todolist.Models.Responses.Board.TotalBoardItemResponse;
import com.example.todolist.Models.Responses.UserResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TotalRoomTableResponse {
    @JsonProperty("roomInfo")
    private RoomResponse roomResponse;
    @JsonProperty("members")
    private List<UserResponse> userResponses;
    @JsonProperty("boards")
    private List<TotalBoardItemResponse> totalBoardItemResponses;
}
