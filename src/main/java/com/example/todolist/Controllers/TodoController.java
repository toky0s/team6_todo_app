package com.example.todolist.Controllers;

import com.example.todolist.Models.Requests.Todo.TodoDeleteRequest;
import com.example.todolist.Models.Requests.Todo.TodoModifyRequest;
import com.example.todolist.Models.Requests.Todo.TodoRequest;
import com.example.todolist.Models.Responses.TodoResponse;
import com.example.todolist.Services.TodoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/tables/{tableId}/todos")
    public List<TodoResponse> getTodoResponses(@PathVariable Integer tableId){
        return todoService.getCreatedTodosByBoardId(tableId);
    }

    @PostMapping("/todo")
    public TodoResponse createTodo(@RequestBody TodoRequest todoRequest){
        return todoService.createNewTodo(todoRequest);
    }

    @DeleteMapping("/todo")
    public TodoResponse deleteTodo(@RequestBody TodoDeleteRequest todoDeleteRequest) {
        return todoService.deleteTodo(todoDeleteRequest.getId());
    }

    @PutMapping("/todo/")
    public TodoResponse modifyTodo(@RequestBody TodoModifyRequest todoModifyRequest){
        return todoService.modifyTodo(todoModifyRequest);
    }
}
