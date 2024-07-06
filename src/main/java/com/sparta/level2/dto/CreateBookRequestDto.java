package com.sparta.level2.dto;


import lombok.Getter;

@Getter
public class CreateBookRequestDto {

    private String title;
    private String writer;
    private String language;
    private String publisher;
}
