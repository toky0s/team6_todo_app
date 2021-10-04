package com.example.todolist.Controllers;

import com.example.todolist.Models.Requests.UserRoom.JoinedRoomRequest;
import com.example.todolist.Models.Requests.UserRoom.JoinedRoomResponse;
import com.example.todolist.Services.UserRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class UserRoomController {

    @Autowired
    private UserRoomService userRoomService;

    @PostMapping("/user-room")
    public JoinedRoomResponse joinARoom(@RequestBody JoinedRoomRequest joinedRoomRequest){
        return userRoomService.joinARoom(joinedRoomRequest);
    }
}
