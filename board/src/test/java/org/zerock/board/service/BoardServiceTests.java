package org.zerock.board.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;

@SpringBootTest
@Log4j2
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister() {
        BoardDTO dto = BoardDTO.builder()
                .title("Test.")
                .content("Test...")
                .writerEmail("user55@aaa.com")
                .build();

        Long bno = boardService.register(dto);
    }

    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        PageResultDTO<BoardDTO, Object[]> result = boardService.getList(pageRequestDTO);

        for (BoardDTO boardDTO: result.getDtoList()) {
            log.info(boardDTO);
        }
    }

    @Test
    public void testGet() {
        Long bno = 100l;
        BoardDTO boardDTO = boardService.get(bno);
        log.info(boardDTO);
    }

    @Test
    public void testRemove() {
        Long bno = 1l;
        boardService.removeWithReplies(bno);
    }

    @Test
    public void testModify() {
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(2l)
                .title("제목 변경")
                .content("내용 변경")
                .build();

        boardService.modify(boardDTO);
    }
}
