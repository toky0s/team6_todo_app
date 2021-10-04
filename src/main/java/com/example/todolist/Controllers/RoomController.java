package com.example.todolist.Controllers;

import com.example.todolist.Models.Entities.CustomUserDetail;
import com.example.todolist.Models.Entities.User;
import com.example.todolist.Models.Requests.Room.RoomDeleteRequest;
import com.example.todolist.Models.Requests.Room.RoomModifyRequest;
import com.example.todolist.Models.Requests.Room.RoomRequest;
import com.example.todolist.Models.Responses.RoomResponse;
import com.example.todolist.Services.RoomService;
import com.example.todolist.Utils.JwtTokenProvider;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoomController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private RoomService roomService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/rooms")
    public List<RoomResponse> getCreatedRoomsByUser(){
        CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        return roomService.getRoomsByUser(user);
    }

    @PostMapping("/room")
    public RoomResponse createRoom(@RequestBody RoomRequest roomRequest){
        CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return roomService.createRoom(roomRequest, userDetails.getUser());
    }

    @PutMapping("/room")
    public RoomResponse modifyRoom(@RequestBody RoomModifyRequest roomModifyRequest){
        CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return roomService.modifyRoom(roomModifyRequest, userDetails.getUser());
    }

    @DeleteMapping("/room")
    public RoomResponse deleteRoom(@RequestBody RoomDeleteRequest roomDeleteRequest){
        CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return roomService.deleteRoom(roomDeleteRequest, userDetails.getUser());
    }
}
