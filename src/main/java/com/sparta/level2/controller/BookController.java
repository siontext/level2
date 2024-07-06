package com.sparta.level2.controller;


import com.sparta.level2.dto.CreateBookRequestDto;
import com.sparta.level2.dto.CreateBookResponseDto;
import com.sparta.level2.entity.Book;
import com.sparta.level2.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    //도서 등록
    @PostMapping("/book")
    public ResponseEntity<CreateBookResponseDto> createBook(@RequestBody CreateBookRequestDto requestDto) {

        //반환타입에 맞게 객체생성 (서비스의 반환타입) -> CreateBookResponseDto
        CreateBookResponseDto responseDto = bookService.createBook(requestDto);

        //반환된 Dto 타입을 -> ResponseEntity로 감싸주기
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);

    }

    //선택한 도서 정보 조회
    @GetMapping("/book/{bookid}")
    public ResponseEntity<CreateBookResponseDto> getBook(@PathVariable Long bookid) {

        //DTO 객체 생성 (Id로 찾은 책)
        CreateBookResponseDto book = bookService.getBook(bookid);

        //반환된 DTO타입을 -> ResponseEntity로 감싸주기
        return new ResponseEntity<>(book, HttpStatus.OK);
    }


    //도서 목록 조회
    @GetMapping("/books")
    public ResponseEntity<List<CreateBookResponseDto>> getAllBooks() {

        //어차피 getAllBooks()메서드의 반환타입이 DTO니깐 객체로 안받고 ResponseEntity에 감싸서 바로 반환해 보자.
        ResponseEntity response = new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);

        return response;
    }

}
