package com.example.todolist.Models.Responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TodoResponse {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("detail")
    private String detail;

    @JsonProperty("publicDate")
    private String publicDate;

    @JsonProperty("status")
    private Integer status;

    @JsonProperty("boardId")
    private Integer boardId;

    @JsonProperty("createUserId")
    private Integer createUserId;

    @JsonProperty("modifyUserId")
    private Integer modifyUserId;
}
