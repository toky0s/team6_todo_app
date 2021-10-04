package com.example.todolist.Exceptions;

public class UserIsInvalidException extends RuntimeException {
    public UserIsInvalidException(String username){
        super(username);
    }
}
