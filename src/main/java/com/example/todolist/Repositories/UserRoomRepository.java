package com.example.todolist.Repositories;

import com.example.todolist.Models.Entities.UserRoom;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRoomRepository extends CrudRepository<UserRoom, Integer> {
    List<UserRoom> findUserRoomByUserId(Integer userId);

    Optional<UserRoom> findUserRoomByUserIdAndRoomId(Integer userId, Integer roomId);
}
