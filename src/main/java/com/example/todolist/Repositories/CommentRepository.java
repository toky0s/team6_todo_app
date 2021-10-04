package com.example.todolist.Repositories;

import com.example.todolist.Models.Entities.Comment;
import com.example.todolist.Models.Entities.Todo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer> {
    List<Comment> findCommentsByTodo(Todo todo);
}
