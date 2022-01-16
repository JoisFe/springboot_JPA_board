package com.example.board.service;

import com.example.board.domain.Criteria;
import com.example.board.domain.board.Board;
import com.example.board.domain.board.BoardRepository;
import com.example.board.domain.comment.Comment;
import com.example.board.domain.comment.CommentRepository;
import com.example.board.dto.BoardDto;
import com.example.board.dto.CommentDto;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Long commentListCnt(Board board) {
        return commentRepository.countByBoardId(board);
    }

    public List<CommentDto> commentList(BoardDto boardDto, Criteria cri) {
        Pageable pageable = PageRequest.of(cri.getPage() - 1, cri.getPerPageNum());

        List<Comment> comments = commentRepository.findAllByBoardId(boardDto.toEntity(), pageable); // /마찬가지 boardId 는 Long타입이 아니라 Board 타입이라 문제가됨

        List<CommentDto> commentDtoList = new ArrayList<>();

        for (Comment comment : comments) {
            CommentDto commentDto = CommentDto.builder()
                    .id(comment.getId())
                    .boardId(comment.getBoardId())
                    .content(comment.getContent())
                    .author(comment.getAuthor())
                    .createdDate(comment.getCreatedDate())
                    .modifiedDate(comment.getModifiedDate())
                    .build();

            commentDtoList.add(commentDto);
        }

        return commentDtoList;
    }

    public CommentDto getComment(Long id) {
        Optional<Comment> commentWrapper = commentRepository.findById(id);
        Comment comment = commentWrapper.get();

        return CommentDto.builder()
                .id(comment.getId())
                .boardId(comment.getBoardId())
                .content(comment.getContent())
                .author(comment.getAuthor())
                .build();
    }


    @Transactional
    public Long uploadComment(CommentDto commentDto) { return commentRepository.save(commentDto.toEntity()).getId();
    }

    @Transactional
    public Long updateComment(CommentDto commentDto) {
        return commentRepository.save(commentDto.toEntity()).getId();
    }

    @Transactional
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

}
