package com.example.todolist.Services;

import com.example.todolist.Exceptions.BoardNotFoundException;
import com.example.todolist.Exceptions.RoomNotFoundException;
import com.example.todolist.Exceptions.UserIsInvalidException;
import com.example.todolist.Models.Entities.*;
import com.example.todolist.Models.Requests.Board.BoardDeleteRequest;
import com.example.todolist.Models.Requests.Board.BoardModifyRequest;
import com.example.todolist.Models.Requests.Board.BoardRequest;
import com.example.todolist.Models.Responses.Board.BoardResponse;
import com.example.todolist.Models.Responses.Room.RoomResponse;
import com.example.todolist.Models.Responses.UserResponse;
import com.example.todolist.Repositories.BoardRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BoardService {
    @Autowired
    private RoomService roomService;

    @Autowired
    private UserRoomService userRoomService;

    @Autowired
    private TodoService todoService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BoardRepository boardRepository;

    public List<BoardResponse> getCreatedBoardsByRoomId(Integer roomId){
        Optional<Room> optionalRoom = roomService.getRoomById(roomId);
        if (optionalRoom.isPresent()){
            Room room = optionalRoom.get();
            List<Board> boards = boardRepository.findBoardsByRoom_Id(room.getId());
            return boards.stream().map(board -> modelMapper.map(board, BoardResponse.class)).collect(Collectors.toList());
        }
        else {
            throw new RoomNotFoundException(roomId.toString());
        }
    }

    public BoardResponse createNewBoard(BoardRequest boardRequest) {
        CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        Optional<Room> optionalRoom = roomService.getRoomById(boardRequest.getRoomId());
        Board board = new Board();
        board.setUser(user);
        board.setName(boardRequest.getName());
        board.setRoom(optionalRoom.get());
        Board board1 = boardRepository.save(board);
        return modelMapper.map(board1, BoardResponse.class);
    }

    public BoardResponse deleteBoard(BoardDeleteRequest boardDeleteRequest){
        Optional<Board> board = boardRepository.findById(boardDeleteRequest.getId());
        if (board.isPresent()) {
            CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userDetails.getUser();
            if (Objects.equals(user.getId(), board.get().getUser().getId())){
                boardRepository.delete(board.get());
                return modelMapper.map(board.get(), BoardResponse.class);
            }
            else {
                throw new UserIsInvalidException(user.getName());
            }
        }
        else {
            throw new BoardNotFoundException(boardDeleteRequest.getId().toString());
        }
    }

    public BoardResponse modifyBoard(BoardModifyRequest boardModifyRequest) {
        Optional<Board> board = boardRepository.findById(boardModifyRequest.getId());
        if (board.isPresent()) {
                Board newBoard = modelMapper.map(boardModifyRequest, Board.class);
                boardRepository.save(newBoard);
                return modelMapper.map(boardRepository.findById(boardModifyRequest.getId()).get(), BoardResponse.class);
        }
        throw new BoardNotFoundException(boardModifyRequest.getId().toString());
    }
}
