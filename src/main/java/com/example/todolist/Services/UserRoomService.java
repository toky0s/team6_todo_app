package com.example.todolist.Services;

import com.example.todolist.Exceptions.PasswordInvalidException;
import com.example.todolist.Exceptions.RoomNotFoundException;
import com.example.todolist.Exceptions.UserRoomReallyExistException;
import com.example.todolist.Models.Entities.CustomUserDetail;
import com.example.todolist.Models.Entities.Room;
import com.example.todolist.Models.Entities.User;
import com.example.todolist.Models.Entities.UserRoom;
import com.example.todolist.Models.Requests.UserRoom.JoinedRoomRequest;
import com.example.todolist.Models.Responses.JoinedRoomResponse;
import com.example.todolist.Models.Requests.UserRoom.LeaveRoomRequest;
import com.example.todolist.Models.Responses.LeaveRoomResponse;
import com.example.todolist.Models.Responses.UserResponse;
import com.example.todolist.Repositories.RoomRepository;
import com.example.todolist.Repositories.UserRoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserRoomService {
    @Autowired
    private UserRoomRepository userRoomRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    public JoinedRoomResponse joinARoom(JoinedRoomRequest joinedRoomRequest){
        CustomUserDetail customUserDetail = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = customUserDetail.getUser();
        Optional<UserRoom> userRoom = userRoomRepository.findUserRoomByUserIdAndRoomId(user.getId(), joinedRoomRequest.getRoomId());
        if(userRoom.isPresent()){
            throw new UserRoomReallyExistException(userRoom.get().getRoom().getName());
        }

        Optional<Room> optionalRoom = roomRepository.findById(joinedRoomRequest.getRoomId());
        if (optionalRoom.isPresent()){
            Room room = optionalRoom.get();
            if (joinedRoomRequest.getPassword().equals(room.getPassword())){
                UserRoom newUserRoom = modelMapper.map(joinedRoomRequest, UserRoom.class);
                newUserRoom.setUser(user);
                userRoomRepository.save(newUserRoom);
                JoinedRoomResponse joinedRoomResponse = new JoinedRoomResponse();
                joinedRoomResponse.setRoomId(joinedRoomRequest.getRoomId());
                joinedRoomResponse.setName(room.getName());
                joinedRoomResponse.setStatus(true);
                return joinedRoomResponse;
            }
            else {
                throw new PasswordInvalidException();
            }
        }
        else{
            throw new RoomNotFoundException(joinedRoomRequest.getRoomId().toString());
        }
    }

    public LeaveRoomResponse leaveRoom(LeaveRoomRequest leaveRoomRequest){
        Optional<Room> optionalRoom = roomRepository.findById(leaveRoomRequest.getId());
        if (optionalRoom.isPresent()){
            CustomUserDetail customUserDetail = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = customUserDetail.getUser();
            Optional<UserRoom> optionalUserRoom = userRoomRepository.findUserRoomByUserIdAndRoomId(user.getId(), leaveRoomRequest.getId());
            if (optionalUserRoom.isPresent()){
                UserRoom userRoom = optionalUserRoom.get();
                Optional<Room> room = roomRepository.findById(userRoom.getId());
                userRoomRepository.delete(userRoom);
                LeaveRoomResponse leaveRoomResponse = new LeaveRoomResponse();
                leaveRoomResponse.setId(leaveRoomRequest.getId());
                leaveRoomResponse.setName(room.get().getName());
            }
        }
        throw new RoomNotFoundException(leaveRoomRequest.getId().toString());
    }

    public List<UserResponse> getUsersJoinedRoom(Room room) {
        List<UserRoom> userRooms = userRoomRepository.findUserRoomByRoomId(room.getId());
        List<UserResponse> users = userRooms.stream()
                .map(userRoom -> userService.getUserResponseById(userRoom.getId()))
                .collect(Collectors.toList());
        return users;
    }
}
