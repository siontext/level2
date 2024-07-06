package com.sparta.level2.dto;

import com.sparta.level2.entity.Book;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateBookResponseDto {

    private Long id;
    private String title;
    private String writer;
    private String language;
    private String publisher;
    private LocalDateTime createdAt;

    public CreateBookResponseDto(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.writer = book.getWriter();
        this.language = book.getLanguage();
        this.publisher = book.getPublisher();
        this.createdAt = book.getCreatedAt();
    }



}
