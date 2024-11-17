package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ArticleApiController {
    /*@Autowired
    private ArticleRepository articleRepository;*/

    @Autowired
    private ArticleService articleService;


    /*@GetMapping("/api/articles")
    public List<Article> index() {
        return articleRepository.findAll();
    }*/

    @GetMapping("/api/articles")
    public List<Article> index() {
        return articleService.index();
    }

    /*@GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id) {
        return articleRepository.findById(id).orElse(null);
    }*/

    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id) {
        return articleService.show(id);
    }

    /*@PostMapping("/api/articles")
    public Article create(@RequestBody ArticleForm dto) {
        Article article = dto.toEntity();
        return articleRepository.save(article);
    }*/

    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto) {
        Article created = articleService.create(dto);
        return (created != null)?ResponseEntity.status(HttpStatus.OK).body(created):
                                 ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /*@PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto) {
//        1. DTO -> 엔티티 변환
        Article article = dto.toEntity();
        log.info("id: {}, article:{}", id, article.toString());

//        2. 타깃 조회
        Article target = articleRepository.findById(id).orElse(null);

//        3. 잘못된 요청 처리
        if (target == null || id != article.getId()) {
            log.info("wrong request. id: {}, article: {}", id, article.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
//        4. 업데이트 및 정상 응답(200)하기
        target.patch(article);
        Article updated = articleRepository.save(target);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }*/

    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto) {
        Article updated = articleService.update(id, dto);
        return (updated != null)? ResponseEntity.status(HttpStatus.OK).body(updated):
                                  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id){
        Article deleted = articleService.delete(id);
        return (deleted != null)? ResponseEntity.status(HttpStatus.OK).build():
                                 ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // test transaction
    @PostMapping("/api/transaction-test")
    public ResponseEntity<List<Article>> transactionTest(@RequestBody List<ArticleForm> dtos){
        List<Article> createdList = articleService.createArticles(dtos);
        return (createdList != null)? ResponseEntity.status(HttpStatus.OK).body(createdList):
                                      ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
