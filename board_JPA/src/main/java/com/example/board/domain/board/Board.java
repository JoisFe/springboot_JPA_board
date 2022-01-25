package com.example.board.domain.board;

import com.example.board.domain.Time;
import com.example.board.domain.member.Member;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Board extends Time {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="author", nullable = false)
    private Member author; // User 의 userId를 가리키는 외래키임 User의 userId는 고유키이기 때문에 외래키로 연결 가능

    @Column(length = 30, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", length = 300, nullable = false)
    private String content;

    @Builder
    public Board(Long id, Member author, String title, String content) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
    }

}