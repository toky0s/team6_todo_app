package com.example.todolist.Exceptions;

public class YouAreRootException extends RuntimeException{
    public YouAreRootException(String roomId){
        super(roomId);
    }
}
