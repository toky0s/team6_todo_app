package com.example.todolist.Models.Responses.Board;

import com.example.todolist.Models.Responses.TodoResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TotalBoardItemResponse {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("title")
    private String name;
    @JsonProperty("Task")
    private List<TodoResponse> todos;
}
