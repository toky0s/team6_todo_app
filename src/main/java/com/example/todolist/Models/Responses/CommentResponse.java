package com.example.todolist.Models.Responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CommentResponse {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("content")
    private String content;

    @JsonProperty("user-id")
    private Integer userId;

    @JsonProperty("todo-id")
    private Integer todoId;
}
