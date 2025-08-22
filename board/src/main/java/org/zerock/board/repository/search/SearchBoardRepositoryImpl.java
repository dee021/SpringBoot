package org.zerock.board.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.QBoard;
import org.zerock.board.entity.QMember;
import org.zerock.board.entity.QReply;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {

    public SearchBoardRepositoryImpl() {
        super(Board.class);
    }

    @Override
    public Board search1() {
        QBoard board = QBoard.board;
        QReply reply = QReply.reply;
        QMember member = QMember.member;

        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));


        // 엔티티 객체 단위가 아니라 각 데이터를 추출하는 경우 Tuple 이용
        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member.email, reply.count()) // 여러 객체를 가져오는 형태
                        .groupBy(board); // 집합 함수 처리 가능




        log.info("------------------------------------");
        log.info(tuple);
        log.info("------------------------------------");

        List<Tuple> result = tuple.fetch();
        return null;
    }

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {
        log.info("====================Search Impl===============================");

        QBoard board = QBoard.board;
        QReply reply = QReply.reply;
        QMember member = QMember.member;


        // from ~ join
        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

        // select
        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member.email, reply.count());

        // search cond
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expression = board.bno.gt(0l);

        booleanBuilder.and(expression);

        if (type != null) {
            String[] typeArr = type.split("");

            BooleanBuilder conditionBuilder = new BooleanBuilder();

            for (String t : typeArr) {
                switch (t) {
                    case "t":
                        conditionBuilder.or(board.title.contains(keyword));
                        break;
                    case "w":
                        conditionBuilder.or(member.email.contains(keyword));
                        break;
                    case "c":
                        conditionBuilder.or(board.content.contains(keyword));
                        break;
                }
            }

            booleanBuilder.and(conditionBuilder);
        }

        tuple.where(booleanBuilder);
        tuple.groupBy(board);

        // sort and pagination
        this.getQuerydsl().applyPagination(pageable, tuple);

        List<Tuple> result = tuple.fetch();
        log.info(result);
        long count = tuple.fetchCount();
        log.info("count: " + count);

        return new PageImpl<Object[]>(result.stream().map(Tuple::toArray).collect(Collectors.toList()) ,
                pageable, count);
    }
}
