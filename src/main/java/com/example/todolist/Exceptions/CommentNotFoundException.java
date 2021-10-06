package com.example.todolist.Exceptions;

public class CommentNotFoundException extends RuntimeException{
    public CommentNotFoundException(String commentId) {
        super(commentId);
    }
}
