package com.example.todolist.Exceptions;

public class UserReadlyExistExeption extends RuntimeException {
    public UserReadlyExistExeption(String userName){
        super(userName);
    }
}
