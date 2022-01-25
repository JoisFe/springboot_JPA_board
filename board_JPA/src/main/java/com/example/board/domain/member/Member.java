package com.example.board.domain.member;

import com.example.board.domain.Time;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
public class Member extends Time {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 15, nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    private String role;

    @Builder
    public Member(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;

    }

}
