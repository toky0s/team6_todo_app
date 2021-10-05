package com.example.todolist.Models.Entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "room")
@Data
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "room")
    private Set<Board> boards;

    @OneToMany(mappedBy = "room")
    private Set<UserRoom> userRooms;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
