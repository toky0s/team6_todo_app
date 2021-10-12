package com.example.todolist.Controllers;

import com.example.todolist.Models.Entities.CustomUserDetail;
import com.example.todolist.Models.Entities.User;
import com.example.todolist.Models.Requests.UserRoom.JoinedRoomRequest;
import com.example.todolist.Models.Requests.UserRoom.KickRoomRequest;
import com.example.todolist.Models.Requests.UserRoom.LeaveRoomRequest;
import com.example.todolist.Models.Responses.JoinedRoomResponse;
import com.example.todolist.Models.Responses.KickRoomResponse;
import com.example.todolist.Models.Responses.LeaveRoomResponse;
import com.example.todolist.Services.UserRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserRoomController {

    @Autowired
    private UserRoomService userRoomService;

    @PostMapping("/user-room")
    public JoinedRoomResponse joinARoom(@RequestBody JoinedRoomRequest joinedRoomRequest){
        return userRoomService.joinARoom(joinedRoomRequest);
    }

    @DeleteMapping("/user-room/leave")
    public LeaveRoomResponse leaveRoom(@RequestBody LeaveRoomRequest leaveRoomRequest){
        return userRoomService.leaveRoom(leaveRoomRequest);
    }

    @DeleteMapping("/user-room/kick")
    public KickRoomResponse kickFromRoom(@RequestBody KickRoomRequest kickRoomRequest){
        return userRoomService.kick(kickRoomRequest.getUserId(), kickRoomRequest.getRoomId());
    }
}