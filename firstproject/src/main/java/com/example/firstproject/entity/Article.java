package com.example.firstproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

// 엔티티 선언
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Article {
    @Id
    // PK 지정
    /*
    @GeneratedValue : .sql 로 넣은 데이터와 id 중복 문제 
    -> 
    @GeneratedValue(strategy = GenerationType.IDENTITY) & .sql 파일로 생성하는 데이터에 id 생략
    */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 자동 생성 기능 추가
    private Long id;

    @Column
    // 필드 선언
    private String title;

    @Column
    private String content;

    public void patch(Article article) {
        if (article.title != null)
            this.title = article.title;
        if (article.content != null)
            this.content = article.content;
    }

    /*public Long getId() {
        return id;
    }
    => @Getter 대체
    */


/*
    public Article(Long id, String title, String content) {
        this.content = content;
        this.title = title;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Aricle{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }*/



}
