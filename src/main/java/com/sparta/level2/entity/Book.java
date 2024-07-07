package com.sparta.level2.entity;

import com.sparta.level2.auditing.Timestamped;
import com.sparta.level2.dto.CreateBookRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Book extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String writer;
    private String language;
    private String publisher;

    private boolean isAvilable = true; //대출 가능 여부


    public Book(CreateBookRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.writer = requestDto.getWriter();
        this.language = requestDto.getLanguage();
        this.publisher = requestDto.getPublisher();
    }

    //JPA 사용을 위한 기본 생성자
    public Book() {
    }


    public void loan() {
        this.isAvilable = false;
    }



}
