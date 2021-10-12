package com.example.todolist.Services;

import com.example.todolist.Exceptions.RoomNotFoundException;
import com.example.todolist.Exceptions.UserIsInvalidException;
import com.example.todolist.Models.Entities.Room;
import com.example.todolist.Models.Entities.User;
import com.example.todolist.Models.Entities.UserRoom;
import com.example.todolist.Models.Requests.Room.RoomDeleteRequest;
import com.example.todolist.Models.Requests.Room.RoomModifyRequest;
import com.example.todolist.Models.Requests.Room.RoomRequest;
import com.example.todolist.Models.Responses.Board.BoardResponse;
import com.example.todolist.Models.Responses.Board.TotalBoardItemResponse;
import com.example.todolist.Models.Responses.Room.RoomResponse;
import com.example.todolist.Models.Responses.Room.TotalRoomTableResponse;
import com.example.todolist.Models.Responses.TodoResponse;
import com.example.todolist.Models.Responses.Room.TotalRoomResponse;
import com.example.todolist.Models.Responses.UserResponse;
import com.example.todolist.Repositories.RoomRepository;
import com.example.todolist.Repositories.UserRoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.DoubleToIntFunction;
import java.util.stream.Collectors;

@Service
public class RoomService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserRoomService userRoomService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private TodoService todoService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRoomRepository userRoomRepository;

    public List<RoomResponse> getRoomsCreatedByUser(User user){
        List<Room> createdRooms = roomRepository.findRoomsByUser(user);
        return createdRooms.stream()
                .map(createdRoom -> modelMapper.map(createdRoom, RoomResponse.class))
                .collect(Collectors.toList());
    }

    public TotalRoomResponse getRooms(User user){
        List<Room> createdRooms = roomRepository.findRoomsByUser(user);
        List<Room> joinedRooms = this.getRoomJoinedByUser(user);
        List<RoomResponse> createdRoomResponses = createdRooms.stream()
                .map(createdRoom -> modelMapper.map(createdRoom, RoomResponse.class))
                .collect(Collectors.toList());
        List<RoomResponse> joinedRoomResponses = joinedRooms.stream()
                .map(joinedRoom -> modelMapper.map(joinedRoom, RoomResponse.class))
                .collect(Collectors.toList());

        TotalRoomResponse totalRoomResponse = new TotalRoomResponse(createdRoomResponses, joinedRoomResponses);

        return totalRoomResponse;
    }

    public List<Room> getRoomJoinedByUser(User user){
        List<UserRoom> userRooms = userRoomRepository.findUserRoomByUserId(user.getId());
        return userRooms.stream()
                .map(userRoom -> userRoom.getRoom())
                .collect(Collectors.toList());
    }

    private RoomResponse mappingResponse(Room room) {
        return modelMapper.map(room, RoomResponse.class);
    }

    public RoomResponse createRoom(RoomRequest roomRequest, User user){
        Room room = modelMapper.map(roomRequest, Room.class);
        room.setUser(user);
        Room savedRoom = roomRepository.save(room);
        return modelMapper.map(savedRoom, RoomResponse.class);
    }

    public RoomResponse deleteRoom(RoomDeleteRequest roomDeleteRequest, User user){
        Integer roomId = roomDeleteRequest.getId();
        System.out.println(roomId);
        Optional<Room> room = roomRepository.findById(roomId);
        if (room.isPresent()){
            if (Objects.equals(room.get().getUser().getId(), user.getId())){
                roomRepository.deleteRoomById(room.get().getId());
                return modelMapper.map(room.get(), RoomResponse.class);
            }
            else{
                throw new UserIsInvalidException(user.getId());
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
                throw new UserIsInvalidException(user.getId());
            }
        }
        else {
            throw new RoomNotFoundException(roomId.toString());
        }
    }

    public Optional<Room> getRoomById(Integer roomId) {
        return roomRepository.findById(roomId);
    }

    public TotalRoomTableResponse getTotalRoomTableResponse(Integer roomId) {
        Optional<Room> optionalRoom = getRoomById(roomId);
        if (optionalRoom.isPresent()){
            Room room =  optionalRoom.get();
            RoomResponse roomResponse = modelMapper.map(room, RoomResponse.class);
            List<UserResponse> userResponses = userRoomService.getUsersJoinedRoom(room);
            List<BoardResponse> boards = boardService.getCreatedBoardsByRoomId(roomId);

            List<TotalBoardItemResponse> totalBoardItemResponses = boards.stream()
                    .map(board -> totalBoardItemResponseMapper(board))
                    .collect(Collectors.toList());
            return new TotalRoomTableResponse(roomResponse, userResponses, totalBoardItemResponses);
        }
        else {
            throw new RoomNotFoundException(roomId.toString());
        }
    }

    public TotalBoardItemResponse totalBoardItemResponseMapper(BoardResponse board) {
        Integer id = board.getId();
        String name = board.getName();
        List<TodoResponse> todoResponses = todoService.getCreatedTodosByBoardId(board.getId());
        return new TotalBoardItemResponse(id, name, todoResponses);
    }
}
