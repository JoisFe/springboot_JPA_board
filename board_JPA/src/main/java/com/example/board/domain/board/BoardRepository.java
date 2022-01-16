package com.example.board.domain.board;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    // List<Board> findAll(PageRequest pageRequest);

    @Query("SELECT b From Board b ORDER BY b.createdDate DESC")
    List<Board> findAllGreaterThan(PageRequest pageRequest);

    Optional<Board> findById(Long id);
}
