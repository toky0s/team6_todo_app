package com.example.todolist.Repositories;

import com.example.todolist.Models.Entities.Board;
import com.example.todolist.Models.Entities.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends CrudRepository<Board, Integer> {
    List<Board> findBoardsByRoom_Id(Integer id);

    Optional<Board> findBoardByRoom(Room room);
}
