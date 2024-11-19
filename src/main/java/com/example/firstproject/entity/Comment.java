package com.example.firstproject.entity;

import com.example.firstproject.dto.CommentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // Article Entity와 Comment Entity를 다대일 관계 설정
    @JoinColumn(name= "article_id") // FK 설정
    private Article article;

    @Column
    private String nickname;

    @Column
    private String body;

    public static Comment createComment(CommentDto dto, Article article) {
        // 예외 발생
        // Case 1: dto에 id가 존재하는 경우
        if (dto.getId() != null) throw new IllegalArgumentException("댓글 생성 실패. 댓글에 id가 없어야 합니다.");
        // Case 2: 게시글이 일치하지 않는 경우
        if (dto.getArticleId() != article.getId()) throw new IllegalArgumentException("댓글 생성 실패. 게시글의 id가 잘못됐습니다.");

        // 엔티티 생성 및 반환
        return new Comment(dto.getId(), article, dto.getNickname(), dto.getBody());
    }

    public void patch(CommentDto dto) {
        // 예외 발생
        if (this.id != dto.getId()) throw new IllegalArgumentException("댓글 수정 실패. 잘못된 id가 입력됐습니다.");
        // 객체 갱신
        if (dto.getNickname() != null) this.nickname = dto.getNickname();
        if (dto.getBody() != null) this.body = dto.getBody();
    }
}