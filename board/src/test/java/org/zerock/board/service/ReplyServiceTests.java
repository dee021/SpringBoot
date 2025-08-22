package org.zerock.board.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.dto.ReplyDTO;

import java.util.List;

@SpringBootTest
@Log4j2
public class ReplyServiceTests {

    @Autowired
    private ReplyService service;

    @Test
    public void testGetList() {
        Long bno = 100l;
        List<ReplyDTO> replyDTOList = service.getList(bno);
        replyDTOList.forEach(replyDTO -> log.info(replyDTO));
    }
}
