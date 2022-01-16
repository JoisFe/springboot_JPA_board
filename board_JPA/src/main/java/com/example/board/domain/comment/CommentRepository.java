package com.example.board.domain.comment;

import com.example.board.domain.board.Board;
import com.example.board.dto.BoardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE c.boardId = :board ORDER BY c.createdDate DESC")
    List<Comment> findAllByBoardId(Board board, Pageable pageable);

    @Query("SELECT COUNT(c) FROM Comment c WHERE c.boardId = :board")
    Long countByBoardId(Board board);

}
