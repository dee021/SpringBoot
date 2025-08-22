package org.zerock.guestbook.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.dto.PageResultDTO;
import org.zerock.guestbook.entity.Guestbook;

@SpringBootTest
@Log4j2
public class GuestbookServiceTests {

    @Autowired
    private GuestbookService service;

    @Test
    public void testRegister() {
        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .title("Sample Title...")
                .content("Sample Content...")
                .writer("user0")
                .build();

        log.info(service.register(guestbookDTO));
    }

    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();

        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(pageRequestDTO);

        log.info("PREV: " + resultDTO.isPrev());
        log.info("NEXT: " + resultDTO.isNext());
        log.info("TOTAL: " + resultDTO.getTotalPage());


        log.info("-----------------------------------------------------");
        for (GuestbookDTO guestbookDTO: resultDTO.getDtoList()) {
            log.info(guestbookDTO);
        }

        log.info("======================================================");
        resultDTO.getPageList().forEach(i -> log.info(i));
    }

    @Test
    public void testSearch() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .type("tc")
                .keyword("test")
                .build();

        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(pageRequestDTO);

        log.info("PREV: " + resultDTO.isPrev());
        log.info("NEXT: " + resultDTO.isNext());
        log.info("TOTAL: " + resultDTO.getTotalPage());

        log.info("-------------------------------------");
        for (GuestbookDTO guestbookDTO: resultDTO.getDtoList()) {
            log.info(guestbookDTO);
        }

        log.info("=====================================");
        resultDTO.getPageList().forEach(i -> log.info(i));
    }
}
