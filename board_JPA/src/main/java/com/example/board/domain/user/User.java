package com.example.board.domain.user;

import com.example.board.PasswordConverter;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 15, nullable = false, unique = true)
    private String author;

    @Convert(converter = PasswordConverter.class)
    @Column(nullable = false)
    private String password;


    @Builder
    public User(Long id, String author, String password) {
        this.id = id;
        this.author = author;
        this.password = password;
    }
}
