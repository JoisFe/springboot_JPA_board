package com.example.board.service;


import com.example.board.domain.user.User;
import com.example.board.domain.user.UserRepository;
import com.example.board.dto.BoardDto;
import com.example.board.dto.CommentDto;
import com.example.board.dto.UserDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long register(UserDto userDto) {
        return userRepository.save(userDto.toEntity()).getId();
    }

    public Long login(UserDto userDto) {
        return userRepository.countByAuthorAndPassword(userDto.getAuthor(), userDto.getPassword());
    }

    public void memberDelete(UserDto userDto) {
        userRepository.deleteByAuthorAndPassword(userDto.getAuthor(), userDto.getPassword());
    }

    public User findId(UserDto userDto) {
        return userRepository.findByAuthor(userDto.getAuthor());
    }

    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }

    public Long idCheck(UserDto userDto) {
        return userRepository.countByAuthorAndPassword(userDto.getAuthor(), userDto.getPassword());
    }


    public String findSessionId(HttpServletRequest req) {
        HttpSession session = req.getSession();

        return (String)session.getAttribute("id");
    }

    public String registerValidTest(Model model, @Valid UserDto userDto, BindingResult errors, String samePassword) {
        if (errors.hasErrors()) {
            Map<String, String> validatorResult = validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));

                return validatorResult.get(key);
            }
        }

        if (idCheck(userDto) == 1) return "중복된 아이디가 존재합니다.";

        if (!userDto.getPassword().equals(samePassword)) return "두 비밀번호가 다릅니다";

        register(userDto);

        return "success";
    }

    public boolean boardWriterTest(String author, HttpServletRequest req) {
        String sessionId = findSessionId(req);

        return author.equals(sessionId); //Author는 타입이 User이고 그 멤버중 AUthor가 있으니 getAuthor를 2번 함
    }

    public boolean commentWriterTest(String author, HttpServletRequest req) {
        String sessionId = findSessionId(req);
        return author.equals(sessionId);
    }

    public User getUser(String author) {

        return userRepository.findByAuthor(author);
    }


}
