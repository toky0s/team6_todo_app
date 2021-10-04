package com.example.todolist.ApiExceptionHandler;

import com.example.todolist.Exceptions.BoardNotFoundException;
import com.example.todolist.Exceptions.TodoNotFoundException;
import com.example.todolist.Exceptions.UserIsInvalidException;
import com.example.todolist.Exceptions.UserReadlyExistExeption;
import com.example.todolist.Models.Responses.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(BoardNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage BoardException(Exception ex, WebRequest request){
        return new ErrorMessage(30404,"Bảng không tổn tại");
    }

    @ExceptionHandler(UserReadlyExistExeption.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ErrorMessage UserException(Exception ex, WebRequest request){
        return new ErrorMessage(10409,"Người dùng đã tồn tại");
    }

    @ExceptionHandler(UserIsInvalidException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage UserIsInvalidException(Exception ex, WebRequest request){
        return new ErrorMessage(10404, "Người dùng không hợp lệ");
    }

    @ExceptionHandler(TodoNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage TodoNotFoundException(Exception ex, WebRequest request){
        return new ErrorMessage(40404, "Todo không tồn tại");
    }
}
