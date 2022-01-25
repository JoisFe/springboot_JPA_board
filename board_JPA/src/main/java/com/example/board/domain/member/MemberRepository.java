package com.example.board.domain.member;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
;import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Long countByUsernameAndPassword(String username, String password);

    void deleteByUsernameAndPassword(String username, String password);

    Optional<Member> findByUsername (@Param("username") String username);
}
