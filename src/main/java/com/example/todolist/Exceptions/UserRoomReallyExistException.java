package com.example.todolist.Exceptions;

public class UserRoomReallyExistException extends RuntimeException{
    public UserRoomReallyExistException(String roomName){
        super(roomName);
    }
}
