package com.example.todolist.Exceptions;

public class TodoNotFoundException extends RuntimeException {
    public TodoNotFoundException(String todoId){
        super(todoId);
    }
}
