
package com.example.board.domain.board;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class BoardQueryRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public BoardQueryRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    QBoard board = QBoard.board;

    @Transactional
    public List<Board> findAllGreaterThan(PageRequest pageRequest) {
        return queryFactory
                .selectFrom(board)
                .orderBy(board.createdDate.desc())
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch();
    }

}
