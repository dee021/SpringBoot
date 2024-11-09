package com.example.firstproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.ToString;

// 엔티티 선언
@Entity
@AllArgsConstructor
@ToString
public class Article {
    @Id
    // PK 지정
    @GeneratedValue
    // 자동 생성 기능 추가
    private Long id;

    @Column
    // 필드 선언
    private String title;

    @Column
    private String content;
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
