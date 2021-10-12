package com.example.todolist.Exceptions;

public class UserIsInvalidException extends RuntimeException {
    public UserIsInvalidException(Integer userId){
        super(userId.toString());
    }
}
