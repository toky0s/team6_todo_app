package com.example.todolist.Services;

import com.example.todolist.Exceptions.CommentNotFoundException;
import com.example.todolist.Exceptions.TodoNotFoundException;
import com.example.todolist.Models.Entities.Comment;
import com.example.todolist.Models.Entities.CustomUserDetail;
import com.example.todolist.Models.Entities.Todo;
import com.example.todolist.Models.Entities.User;
import com.example.todolist.Models.Requests.Comment.CommentDeleteRequest;
import com.example.todolist.Models.Requests.Comment.CommentRequest;
import com.example.todolist.Models.Responses.CommentResponse;
import com.example.todolist.Repositories.CommentRepository;
import com.example.todolist.Repositories.TodoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
            return comments.stream().map(comment -> commentMapper(comment)).collect(Collectors.toList());
        }
        throw new TodoNotFoundException(todoId.toString());
    }

    public CommentResponse createComment(CommentRequest commentRequest){
        Optional<Todo> optionalTodo = todoRepository.findById(commentRequest.getTodoId());
        if (optionalTodo.isPresent()){
            Comment comment = new Comment();
            CustomUserDetail customUserDetail = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            Todo todo = optionalTodo.get();

            User user = customUserDetail.getUser();
            comment.setContent(commentRequest.getContent());
            comment.setUser(user);
            comment.setTodo(todo);
            Comment savedComment = commentRepository.save(comment);
            return modelMapper.map(savedComment, CommentResponse.class);
        }
        else{
            throw new TodoNotFoundException(commentRequest.getTodoId().toString());
        }
    }

    public CommentResponse deleteComment(CommentDeleteRequest commentDeleteRequest){
        Optional<Comment> optionalComment = commentRepository.findById(commentDeleteRequest.getId());
        if (optionalComment.isPresent()){
            Comment comment = optionalComment.get();
            commentRepository.delete(comment);
            return modelMapper.map(comment, CommentResponse.class);
        }
        else {
            throw new CommentNotFoundException(commentDeleteRequest.getId().toString());
        }
    }

    private CommentResponse commentMapper(Comment comment){
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setContent(comment.getContent());
        commentResponse.setId(comment.getId());
        commentResponse.setUsername(comment.getUser().getName());
        commentResponse.setTodoId(comment.getTodo().getId());
        commentResponse.setUserId(comment.getUser().getId());
        return commentResponse;
    }
}
