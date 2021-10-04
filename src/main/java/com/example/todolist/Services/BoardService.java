package com.example.todolist.Services;

import com.example.todolist.Exceptions.BoardNotFoundException;
import com.example.todolist.Exceptions.UserIsInvalidException;
import com.example.todolist.Models.Entities.Board;
import com.example.todolist.Models.Entities.CustomUserDetail;
import com.example.todolist.Models.Entities.Room;
import com.example.todolist.Models.Entities.User;
import com.example.todolist.Models.Requests.Board.BoardDeleteRequest;
import com.example.todolist.Models.Requests.Board.BoardModifyRequest;
import com.example.todolist.Models.Requests.Board.BoardRequest;
import com.example.todolist.Models.Responses.BoardResponse;
import com.example.todolist.Models.Responses.RoomResponse;
import com.example.todolist.Repositories.BoardRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
    private ModelMapper modelMapper;

    @Autowired
    private BoardRepository boardRepository;

    public List<BoardResponse> getCreatedBoardsByRoomId(Integer roomId){
        CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        RoomResponse roomResponse = roomService.getRoomsCreatedByUser(user).stream()
                .filter(r -> roomId.equals(r.getId()))
                .findAny()
                .orElse(null);
        List<Board> boards = boardRepository.findBoardsByRoom_Id(roomResponse.getId());
        return boards.stream().map(board -> modelMapper.map(board, BoardResponse.class)).collect(Collectors.toList());
    }

    public BoardResponse createNewBoard(BoardRequest boardRequest) {
        CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        Board board = modelMapper.map(boardRequest, Board.class);
        board.setUser(user);
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
        CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        Set<Room> roomSet = user.getRooms();
        Optional<Board> board = boardRepository.findById(boardModifyRequest.getId());
        if (board.isPresent()) {
            Room boardRoom =  board.get().getRoom();
            if (roomSet.contains(boardRoom)){
                Board newBoard = modelMapper.map(boardModifyRequest, Board.class);
                boardRepository.save(newBoard);
                return modelMapper.map(boardRepository.findById(boardModifyRequest.getId()).get(), BoardResponse.class);
            }
        }
        throw new BoardNotFoundException(boardModifyRequest.getId().toString());
    }
}
