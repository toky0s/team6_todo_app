package com.example.todolist.Models.Requests.Comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CommentModifyRequest {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("content")
    private String content;

}
