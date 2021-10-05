package com.example.todolist.Services;

import com.example.todolist.Exceptions.BoardNotFoundException;
import com.example.todolist.Exceptions.TodoNotFoundException;
import com.example.todolist.Models.Entities.Board;
import com.example.todolist.Models.Entities.CustomUserDetail;
import com.example.todolist.Models.Entities.Todo;
import com.example.todolist.Models.Entities.User;
import com.example.todolist.Models.Requests.Todo.TodoModifyRequest;
import com.example.todolist.Models.Requests.Todo.TodoRequest;
import com.example.todolist.Models.Responses.TodoResponse;
import com.example.todolist.Repositories.BoardRepository;
import com.example.todolist.Repositories.TodoRepository;
import com.example.todolist.Repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<TodoResponse> getCreatedTodosByBoardId(Integer boardId){
        List<Todo> todos = todoRepository.findTodosByBoard_Id(boardId);
        return todos.stream()
                .map(todo -> modelMapper.map(todo, TodoResponse.class))
                .collect(Collectors.toList());
    }

    public TodoResponse createNewTodo(TodoRequest todoRequest){
        Optional<Board> board = boardRepository.findById(todoRequest.getBoardId());
        if (board.isPresent()){
            Todo todo = modelMapper.map(todoRequest, Todo.class);
            CustomUserDetail customUserDetail = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = customUserDetail.getUser();
            todo.setCreateUser(user);
            todo.setModifyUser(user);
            todoRepository.save(todo);
            return modelMapper.map(todo, TodoResponse.class);
        }
        throw new BoardNotFoundException(todoRequest.getBoardId().toString());
    }

    public TodoResponse deleteTodo(Integer todoId) {
        Optional<Todo> todo = todoRepository.findById(todoId);
        if (todo.isPresent()){
            todoRepository.deleteById(todoId);
            return modelMapper.map(todo, TodoResponse.class);
        }
        else{
            throw new TodoNotFoundException(todoId.toString());
        }
    }

    public TodoResponse modifyTodo(TodoModifyRequest todoModifyRequest){
        Integer todoRequestId = todoModifyRequest.getId();
        Optional<Todo> todo = todoRepository.findById(todoRequestId);
        if (todo.isPresent()){
            Todo newTodo =  modelMapper.map(todoModifyRequest, Todo.class);
            CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User modifyUser = userDetails.getUser();
            newTodo.setModifyUser(modifyUser);
            todoRepository.save(newTodo);
            return modelMapper.map(newTodo, TodoResponse.class);
        }
        else {
            throw new TodoNotFoundException(todoRequestId.toString());
        }
    }
}
