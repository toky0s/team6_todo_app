package com.example.todolist.Repositories;

import com.example.todolist.Models.Entities.Todo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends CrudRepository<Todo, Integer> {
    List<Todo> findTodosByBoard_Id(Integer id);
}
