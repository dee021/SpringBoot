package org.zerock.board.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertBoard() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member = Member.builder().email("user" + i + "@aaa.com").build();

            Board board = Board.builder()
                    .title("Title..." + i)
                    .content("Content..." + i)
                    .writer(member)
                    .build();

            boardRepository.save(board);
        });
    }

    @Test
    @Transactional
    public void testRead1() {
        Optional<Board> result = boardRepository.findById(100L);

        Board board = result.get();

        log.info(board);
        log.info(board.getWriter());
    }

    @Test
    public void testReadWithWriter() {
        Object result = boardRepository.getBoardWithWriter(100l);
        Object[] arr = (Object[])result;

        log.info("---------------------------------");
        log.info(Arrays.toString(arr));
    }

    @Test
    public void testGetBoardWithReply() {
        List<Object[]> result = boardRepository.getBoardWithReply(100l);

        for (Object[] arr : result) {
            log.info(Arrays.toString(arr));
        }
    }

    @Test
    public void testWithReplyCount() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);

        result.get().forEach(row -> {
            Object[] arr = (Object[])row;
            log.info(Arrays.toString(arr));
        });
    }

    @Test
    public void testRead3() {
        Object result = boardRepository.getBoardByBno(100l);
        Object[] arr = (Object[])result;
        log.info(Arrays.toString(arr));
    }

    @Test
    public void testSearch1() {
        boardRepository.search1();
    }

    @Test
    public void testSearchPage() {
        Pageable pageable = PageRequest.of(0, 10,
                Sort.by("bno").descending().and(Sort.by("title").ascending()));

        Page<Object[]> result = boardRepository.searchPage("t", "1", pageable);
    }
}
