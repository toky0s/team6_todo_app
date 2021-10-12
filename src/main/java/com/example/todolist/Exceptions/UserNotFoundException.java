package com.example.todolist.Exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Integer userId){
        super(userId.toString());
    }
}
