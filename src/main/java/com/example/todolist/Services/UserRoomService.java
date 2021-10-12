package com.example.todolist.Services;

import com.example.todolist.Exceptions.*;
import com.example.todolist.Models.Entities.CustomUserDetail;
import com.example.todolist.Models.Entities.Room;
import com.example.todolist.Models.Entities.User;
import com.example.todolist.Models.Entities.UserRoom;
import com.example.todolist.Models.Requests.UserRoom.JoinedRoomRequest;
import com.example.todolist.Models.Responses.*;
import com.example.todolist.Models.Requests.UserRoom.LeaveRoomRequest;
import com.example.todolist.Models.Responses.Room.RoomResponse;
import com.example.todolist.Repositories.RoomRepository;
import com.example.todolist.Repositories.UserRoomRepository;
import io.swagger.models.auth.In;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.awt.desktop.SystemSleepEvent;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
    private RoomService roomService;

    @Autowired
    private ModelMapper modelMapper;

    public JoinedRoomResponse joinARoom(JoinedRoomRequest joinedRoomRequest){
        CustomUserDetail customUserDetail = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = customUserDetail.getUser();
        Optional<UserRoom> userRoom = userRoomRepository.findUserRoomByUserIdAndRoomId(user.getId(), joinedRoomRequest.getRoomId());
        if(userRoom.isPresent()){
            throw new UserRoomReallyExistException(userRoom.get().getRoom().getName());
        }

        List<RoomResponse> createdByUserRooms = roomService.getRoomsCreatedByUser(user);
        List<Integer> createdByUserRoomIds = createdByUserRooms.stream().map(roomResponse -> roomResponse.getId()).collect(Collectors.toList());
        Optional<Room> optionalRoom = roomService.getRoomById(joinedRoomRequest.getRoomId());
        if(optionalRoom.isPresent()){
            Room room = optionalRoom.get();
            Boolean isCreatedByUser = createdByUserRoomIds.contains(room.getId());
            if (isCreatedByUser){
                throw new YouAreRootException(room.getId().toString());
            }
        }

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
                .map(userRoom -> userService.getUserResponseById(userRoom.getUser().getId()))
                .collect(Collectors.toList());
        return users;
    }

    public KickRoomResponse kick(Integer userId, Integer userRoomId) {
        CustomUserDetail customUserDetail = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = customUserDetail.getUser();
        Optional<UserRoom> optionalUserRoom = userRoomRepository.findById(userRoomId);
        if (optionalUserRoom.isPresent()){
            UserRoom userRoom = optionalUserRoom.get();
            if (userRoom.getRoom().getUser().getId() == user.getId()){
                Optional<UserRoom> optionalKickedUserRoom = userRoomRepository.findUserRoomByUserIdAndRoomId(userId, userRoomId);
                if (optionalUserRoom.isPresent()){
                    userRoomRepository.delete(optionalKickedUserRoom.get());
                    User kickedUser =  userService.getUserById(userId);
                    KickRoomResponse kickRoomResponse = new KickRoomResponse();
                    kickRoomResponse.setUserId(kickedUser.getId());
                    kickRoomResponse.setUsername(kickedUser.getName());
                    return kickRoomResponse;
                }
                else{
                    throw new UserRoomNotFoundException(userRoomId);
                }
            }
            else{
                throw new UserIsInvalidException(userRoomId);
            }
        }
        else {
            throw new UserRoomNotFoundException(userRoomId);
        }
    }
}
