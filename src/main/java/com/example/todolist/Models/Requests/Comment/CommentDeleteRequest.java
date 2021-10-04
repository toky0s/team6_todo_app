package com.example.todolist.Models.Requests.Comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CommentDeleteRequest {
    @JsonProperty("id")
    private Integer id;
}
