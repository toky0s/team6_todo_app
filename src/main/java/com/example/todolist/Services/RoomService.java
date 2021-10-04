package com.example.todolist.Services;

import com.example.todolist.Exceptions.RoomNotFoundException;
import com.example.todolist.Exceptions.UserIsInvalidException;
import com.example.todolist.Models.Entities.Room;
import com.example.todolist.Models.Entities.User;
import com.example.todolist.Models.Requests.Room.RoomDeleteRequest;
import com.example.todolist.Models.Requests.Room.RoomModifyRequest;
import com.example.todolist.Models.Requests.Room.RoomRequest;
import com.example.todolist.Models.Responses.RoomResponse;
import com.example.todolist.Repositories.RoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoomRepository roomRepository;

    public List<RoomResponse> getRoomsByUser(User user){
        List<Room> rooms = roomRepository.findRoomsByUser(user);
        return rooms
                .stream()
                .map(room -> mappingResponse(room))
                .collect(Collectors.toList());
    }

    private RoomResponse mappingResponse(Room room) {
        return modelMapper.map(room, RoomResponse.class);
    }

    public RoomResponse createRoom(RoomRequest roomRequest, User user){
        Room room = modelMapper.map(roomRequest, Room.class);
        room.setUser(user);
        roomRepository.save(room);
        return modelMapper.map(roomRequest, RoomResponse.class);
    }

    public RoomResponse deleteRoom(RoomDeleteRequest roomDeleteRequest, User user){
        Integer roomId = roomDeleteRequest.getId();
        Optional<Room> room = roomRepository.findById(roomId);
        if (room.isPresent()){
            if (Objects.equals(room.get().getUser().getId(), user.getId())){
                roomRepository.delete(room.get());
                return modelMapper.map(room.get(), RoomResponse.class);
            }
            else{
                throw new UserIsInvalidException(user.getName());
            }
        }
        else {
            throw new RoomNotFoundException(roomId.toString());
        }
    }

    public RoomResponse modifyRoom(RoomModifyRequest roomModifyRequest, User user) {
        Integer roomId = roomModifyRequest.getId();
        Optional<Room> room = roomRepository.findById(roomId);
        if (room.isPresent()){
            if (Objects.equals(room.get().getUser().getId(), user.getId())){
                Room newRoom = modelMapper.map(roomModifyRequest, Room.class);
                newRoom.setUser(user);
                roomRepository.save(newRoom);
                return modelMapper.map(newRoom, RoomResponse.class);
            }
            else{
                throw new UserIsInvalidException(user.getName());
            }
        }
        else {
            throw new RoomNotFoundException(roomId.toString());
        }
    }
}
