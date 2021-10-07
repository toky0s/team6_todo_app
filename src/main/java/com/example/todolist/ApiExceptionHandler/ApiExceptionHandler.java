package com.example.todolist.ApiExceptionHandler;

import com.example.todolist.Exceptions.*;
import com.example.todolist.Models.Responses.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(BoardNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage BoardException(Exception ex, WebRequest request){
        return new ErrorMessage(30404,"Bảng không tổn tại", false);
    }

    @ExceptionHandler(UserReadlyExistExeption.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ErrorMessage UserException(Exception ex, WebRequest request){
        return new ErrorMessage(10409,"Người dùng đã tồn tại", false);
    }

    @ExceptionHandler(UserIsInvalidException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage UserIsInvalidException(Exception ex, WebRequest request){
        return new ErrorMessage(10404, "Người dùng không hợp lệ", false);
    }

    @ExceptionHandler(TodoNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage TodoNotFoundException(Exception ex, WebRequest request){
        return new ErrorMessage(40404, "Todo không tồn tại", false);
    }

    @ExceptionHandler(UserRoomReallyExistException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ErrorMessage UserRoomReallyExistException(Exception ex, WebRequest request){
        return new ErrorMessage(50409, "Bạn đã tham gia room này", false);
    }

    @ExceptionHandler(PasswordInvalidException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ErrorMessage PasswordInvalidException(Exception ex, WebRequest request){
        return new ErrorMessage(90403, "Nhập sai mật khẩu", false);
    }

    @ExceptionHandler(RoomNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage RoomNotFoundException(Exception ex, WebRequest request){
        return new ErrorMessage(50404, "Room không tồn tại", false);
    }

    @ExceptionHandler(YouAreRootException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ErrorMessage YouAreRootException(Exception ex, WebRequest request){
        return new ErrorMessage(50409, "Bạn đã là chủ phòng", false);
    }
}
