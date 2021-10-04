package com.example.todolist.Controllers;

import com.example.todolist.Models.Entities.Board;
import com.example.todolist.Models.Requests.Board.BoardDeleteRequest;
import com.example.todolist.Models.Requests.Board.BoardModifyRequest;
import com.example.todolist.Models.Requests.Board.BoardRequest;
import com.example.todolist.Models.Responses.BoardResponse;
import com.example.todolist.Services.BoardService;
import com.example.todolist.Services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BoardController {
    @Autowired
    private BoardService boardService;

    @Autowired
    private RoomService roomService;

    @GetMapping("/room/{id}/boards")
    public List<BoardResponse> getBoardsByRoomAndUser(@PathVariable Integer id){
        return boardService.getCreatedBoardsByRoomId(id);
    }

    @PostMapping("/board")
    public BoardResponse createNewBoard(@RequestBody BoardRequest boardRequest){
        return boardService.createNewBoard(boardRequest);
    }

    @DeleteMapping("/board")
    public BoardResponse deleteBoard(@RequestBody BoardDeleteRequest boardDeleteRequest){
        return boardService.deleteBoard(boardDeleteRequest);
    }

    @PutMapping("/board")
    public BoardResponse modifyNewBoard(@RequestBody BoardModifyRequest boardModifyRequest){
        return boardService.modifyBoard(boardModifyRequest);
    }
}
