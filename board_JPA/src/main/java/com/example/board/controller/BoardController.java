package com.example.board.controller;

import com.example.board.domain.Criteria;
import com.example.board.domain.Paging;
import com.example.board.dto.BoardDto;
import com.example.board.dto.CommentDto;
import com.example.board.service.BoardService;
import com.example.board.service.CommentService;
import com.example.board.service.UserService;
import lombok.RequiredArgsConstructor;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final UserService userService;
    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("/main")
    public String main(Model model, Criteria cri, HttpServletRequest req) {
        String id = userService.findSessionId(req);
        model.addAttribute("id", id);

        return "boards/main";
    }

    @PostMapping("/main")
    @ResponseBody
    public Map<String, Object> main(Criteria cri) {
        Long boardListCnt = boardService.boardListCnt();

        Paging paging = new Paging(cri, boardListCnt);

        List<BoardDto> list = boardService.boardList(cri);

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("list", list);
        map.put("paging", paging);

        return map;
    }

    @GetMapping("/view")
    public String viewBoard(Model model, Long id, HttpServletRequest req) {
        model.addAttribute("view", boardService.getBoard(id));

        String userId = userService.findSessionId(req);
        model.addAttribute("id", userId);

        if (userId != null) model.addAttribute("authorUserId", userService.getUser(userId).getId());

        return "boards/view";
    }

    @PostMapping("/view")
    @ResponseBody
    public Map<String, Object> viewBoard(Long id, Criteria cri) {
        BoardDto boardDto = boardService.getBoard(id);

        Long commentListCnt = commentService.commentListCnt(boardDto.toEntity()); //-> 지금 entity를 보면 boardId가 Long 타입이 아니라 Board 타입이라 문제가 됨

        Paging paging = new Paging(cri, commentListCnt);

        List<CommentDto> list = commentService.commentList(boardDto, cri);

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("list", list);
        map.put("paging", paging);

        return map;
    }

    @GetMapping("/upload")
    public String uploadBoardForm(Model model, HttpServletRequest req) {
        String id = userService.findSessionId(req);

        model.addAttribute("id", id);

        model.addAttribute("authorId", userService.getUser(id).getId());

        return "boards/upload";
    }

    private final Logger logger = LoggerFactory.getLogger(BoardController.class);

    @PostMapping("/upload")
    @ResponseBody
    public String uploadBoard(@Valid BoardDto boardDto, BindingResult errors, HttpServletRequest req) {
        if (!userService.boardWriterTest(boardDto.getAuthor().getAuthor(), req)) return "현재 로그인한 아이디와 작성자가 다릅니다.";

        if (errors.hasErrors()) {
            return boardService.validTest(errors, userService);
        }

        boardService.uploadBoard(boardDto);

        return "success";
    }

    @PutMapping("/update")
    public String updateBoard(@Valid BoardDto boardDto, BindingResult errors, RedirectAttributes rttr, HttpServletRequest req) {
        if (!userService.boardWriterTest(boardDto.getAuthor().getAuthor(), req)) return "현재 로그인한 아이디와 작성자가 다릅니다.";

        if (errors.hasErrors()) {
            rttr.addFlashAttribute("message", boardService.validTest(errors, userService));

            return "redirect:/view?id=" + boardDto.getId();

        }
        boardService.updateBoard(boardDto);

        return "redirect:/main";
    }

    @DeleteMapping("/delete")
    public String deleteBoard(Long id, String author, HttpServletRequest req) {

         if (!userService.boardWriterTest(author, req)) return "현재 로그인한 아이디와 작성자가 다릅니다.";

        boardService.deleteBoard(id);

        return "redirect:/main";
    }
}
