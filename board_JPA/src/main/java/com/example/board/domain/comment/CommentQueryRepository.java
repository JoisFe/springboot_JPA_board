package com.example.board.domain.comment;

import com.example.board.domain.board.Board;
import com.example.board.domain.board.QBoard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class CommentQueryRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public CommentQueryRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    QComment comment = QComment.comment;

    @Transactional
    public List<Comment> findAllByBoardId(Board board, Pageable pageable) {
        return queryFactory
                .selectFrom(comment)
                .where(comment.boardId.eq(board))
                .orderBy(comment.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Transactional
    public Long countByBoardId(Board board) {
        return queryFactory
                .select(comment.count())
                .from(comment)
                .where(comment.boardId.eq(board))
                .fetchOne();
    }

}
