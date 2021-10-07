package com.example.todolist.Models.Responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TodoResponse {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("title")
    private String name;

    @JsonProperty("detail")
    private String detail;

    @JsonProperty("publicDate")
    private String publicDate;

    @JsonProperty("status")
    private Boolean status;

    @JsonProperty("boardId")
    private Integer boardId;

    @JsonProperty("createUserId")
    private Integer createUserId;

    @JsonProperty("modifyUserId")
    private Integer modifyUserId;

    @JsonProperty("createdUsername")
    private String createdUsername;

    @JsonProperty("modifiedUsername")
    private String modifiedUsername;
}
