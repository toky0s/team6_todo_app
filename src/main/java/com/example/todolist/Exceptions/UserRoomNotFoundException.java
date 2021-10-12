package com.example.todolist.Exceptions;

public class UserRoomNotFoundException extends RuntimeException{
    public UserRoomNotFoundException(Integer userRoomId){
        super(userRoomId.toString());
    }
}
