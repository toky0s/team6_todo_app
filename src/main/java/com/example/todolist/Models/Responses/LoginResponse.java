package com.example.todolist.Models.Responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class LoginResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private String userName;
    private String id;


    public LoginResponse(String accessToken, String userName, String id) {
        this.accessToken = accessToken;
        this.userName = userName;
        this.id = id;
    }
}
