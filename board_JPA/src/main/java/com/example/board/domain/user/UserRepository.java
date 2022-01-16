package com.example.board.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
;

public interface UserRepository extends JpaRepository<User, Long> {
    Long countByAuthorAndPassword(String author, String password);

    void deleteByAuthorAndPassword(String author, String password);

    User findByAuthor(String author);
}
