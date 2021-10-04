package com.example.todolist.Models.Entities;
import lombok.Data;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "todo")
@Data
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "detail")
    private String detail;

    @Column(name = "public_date")
    private String publicDate;

    @Column(name = "status")
    private Integer status;

    @OneToMany(mappedBy = "todo")
    private Set<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "create_user_id")
    private User createUser;

    @ManyToOne
    @JoinColumn(name = "modify_user_id")
    private User modifyUser;
}
