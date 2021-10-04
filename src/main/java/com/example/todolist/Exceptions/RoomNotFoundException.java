package com.example.todolist.Exceptions;

public class RoomNotFoundException extends RuntimeException {
    public RoomNotFoundException(String roomId){
        super(roomId);
    }
}
