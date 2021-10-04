package com.example.todolist.Repositories;

import com.example.todolist.Models.Entities.Room;
import com.example.todolist.Models.Entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends CrudRepository<Room, Integer> {
    List<Room> findRoomsByUser(User user);
}
