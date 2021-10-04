package com.example.todolist.Exceptions;

public class PasswordInvalidException extends RuntimeException{
    public PasswordInvalidException(){
        super("Password không hợp lệ");
    }
}
