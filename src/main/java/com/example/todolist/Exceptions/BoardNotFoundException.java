package com.example.todolist.Exceptions;

public class BoardNotFoundException extends RuntimeException{
    public BoardNotFoundException(String boardId){
        super(boardId);
    }
}
