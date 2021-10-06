package com.example.todolist.Models.Entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @OneToMany(cascade = CascadeType.PERSIST , mappedBy = "user")
    private Set<Room> rooms;

    @OneToMany(cascade = CascadeType.PERSIST , mappedBy = "user")
    private Set<Board> boards;

    @OneToMany(cascade = CascadeType.PERSIST , mappedBy = "createUser")
    private Set<Todo> createdTodos;

    @OneToMany(cascade = CascadeType.PERSIST , mappedBy = "modifyUser")
    private Set<Todo> modifiedTodos;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "user")
    private Set<Comment> comments;

    @OneToMany(mappedBy = "user")
    private Set<UserRoom> userRooms;
}
