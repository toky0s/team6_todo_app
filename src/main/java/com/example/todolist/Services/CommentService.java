package com.example.todolist.Services;

import com.example.todolist.Exceptions.TodoNotFoundException;
import com.example.todolist.Models.Entities.Comment;
import com.example.todolist.Models.Entities.Todo;
import com.example.todolist.Models.Requests.Comment.CommentDeleteRequest;
import com.example.todolist.Models.Requests.Comment.CommentRequest;
import com.example.todolist.Models.Responses.CommentResponse;
import com.example.todolist.Repositories.CommentRepository;
import com.example.todolist.Repositories.TodoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<CommentResponse> getCommentsOfTodo(Integer todoId){
        Optional<Todo> todoOptional = todoRepository.findById(todoId);
        if (todoOptional.isPresent()){
            Todo todo = todoOptional.get();
            List<Comment> comments = commentRepository.findCommentsByTodo(todo);
            return comments.stream().map(comment -> modelMapper.map(comment, CommentResponse.class)).collect(Collectors.toList());
        }
        throw new TodoNotFoundException(todoId.toString());
    }

    public CommentResponse createComment(CommentRequest commentRequest){
        Comment comment = modelMapper.map(commentRequest, Comment.class);
        Comment savedComment = commentRepository.save(comment);
        return modelMapper.map(savedComment, CommentResponse.class);
    }

    public CommentResponse deleteComment(CommentDeleteRequest commentDeleteRequest){
        Optional<Comment> optionalComment = commentRepository.findById(commentDeleteRequest.getId());
        if (optionalComment.isPresent()){
            Comment comment = optionalComment.get();
            commentRepository.delete(comment);
            return modelMapper.map(comment, CommentResponse.class);
        }
        else {
            throw new TodoNotFoundException(commentDeleteRequest.getId().toString());
        }
    }
}
