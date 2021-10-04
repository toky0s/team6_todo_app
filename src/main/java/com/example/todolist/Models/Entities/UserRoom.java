package com.example.todolist.Models.Entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "user_room")
@Data
public class UserRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
}
