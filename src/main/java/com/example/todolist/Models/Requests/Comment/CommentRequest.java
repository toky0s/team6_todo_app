package com.example.todolist.Models.Requests.Comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CommentRequest {
    @JsonProperty("content")
    private String content;

    @JsonProperty("todo-id")
    private Integer todoId;
}
