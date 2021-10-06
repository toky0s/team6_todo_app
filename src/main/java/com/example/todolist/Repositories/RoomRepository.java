package com.example.todolist.Repositories;

import com.example.todolist.Models.Entities.Room;
import com.example.todolist.Models.Entities.User;
import com.example.todolist.Models.Entities.UserRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    List<Room> findRoomsByUser(User user);

    @Transactional
    void deleteRoomById(Integer id);
}
