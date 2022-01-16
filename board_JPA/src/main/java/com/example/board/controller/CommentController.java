package com.example.board.controller;

import com.example.board.dto.CommentDto;
import com.example.board.service.BoardService;
import com.example.board.service.CommentService;
import com.example.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;
    private final BoardService boardService;

    @PostMapping("comment")
    @ResponseBody
    public String uploadComment(@Valid CommentDto commentDto, BindingResult errors, HttpServletRequest req) {

        if (!userService.commentWriterTest(commentDto.getAuthor().getAuthor(), req)) return "현재 로그인한 아이디와 작성자가 다릅니다.";

        if (errors.hasErrors()) {
            return boardService.validTest(errors, userService);
        }

        commentService.uploadComment(commentDto);

        return "success";
    }

    @GetMapping("/commentView")
    public String viewComment(Model model, Long id, HttpServletRequest req) {
        model.addAttribute("list", commentService.getComment(id));

        String userId = userService.findSessionId(req);
        model.addAttribute("id", userId);

        return "boards/commentView";
    }

    @PutMapping("/updateComment")
    public String updateComment(@Valid CommentDto commentDto, BindingResult errors, RedirectAttributes rttr, HttpServletRequest req) {

        if (!userService.commentWriterTest(commentDto.getAuthor().getAuthor(), req)) return "현재 로그인한 아이디와 작성자가 다릅니다.";

        if (errors.hasErrors()) {
            rttr.addFlashAttribute("message", boardService.validTest(errors, userService));

            return "redirect:/commentView?id=" + commentDto.getId();
        }

        commentService.updateComment(commentDto);

        return "redirect:/view?id=" + commentDto.getBoardId().getId();
    }

    @DeleteMapping("/deleteComment")
    public String deleteComment(Long id, Long boardId, String authorName, HttpServletRequest req) {
        System.out.println(id);
        System.out.println(boardId);
        System.out.println(authorName);

        if (!userService.commentWriterTest(authorName, req)) return "현재 로그인한 아이디와 작성자가 다릅니다.";

        commentService.deleteComment(id);

        return "redirect:/view?id=" + boardId;

    }
}
