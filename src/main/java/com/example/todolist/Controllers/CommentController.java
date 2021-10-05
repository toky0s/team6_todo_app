package com.example.todolist.Controllers;

import com.example.todolist.Models.Requests.Comment.CommentDeleteRequest;
import com.example.todolist.Models.Requests.Comment.CommentRequest;
import com.example.todolist.Models.Responses.CommentResponse;
import com.example.todolist.Models.Responses.TodoResponse;
import com.example.todolist.Repositories.CommentRepository;
import com.example.todolist.Services.CommentService;
import com.example.todolist.Services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/todo/{id}/comments")
    public List<CommentResponse> getComments(@PathVariable Integer id){
        return commentService.getCommentsOfTodo(id);
    }

    @PostMapping("/comment")
    public CommentResponse createComment(@RequestBody CommentRequest commentRequest){
        return commentService.createComment(commentRequest);
    }

    @DeleteMapping("/comment")
    public CommentResponse deleteComment(@RequestBody CommentDeleteRequest commentDeleteRequest){
        return commentService.deleteComment(commentDeleteRequest);
    }
}
