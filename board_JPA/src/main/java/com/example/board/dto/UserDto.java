package com.example.board.dto;

import com.example.board.domain.user.User;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter
@NoArgsConstructor
@ToString
public class UserDto {
    private Long id;

    @NotBlank(message = "Id는 필수 입력값 입니다.")
    @Size(min = 4, max=15, message = "Id를 4 ~ 15자 사이로 입력해주세요")
    private String author;

    @NotBlank(message = "Password는 필수 입력값 입니다.")
    @Size(min = 4, max=15, message = "Password를 4 ~ 15자 사이로 입력해주세요")
    private String password;

    @Builder
    public UserDto(Long id, String author, String password) {
        this.id = id;
        this.author = author;
        this.password = password;
    }

    public User toEntity() {

        return User.builder()
                .id(id)
                .author(author)
                .password(password)
                .build();
    }
}
