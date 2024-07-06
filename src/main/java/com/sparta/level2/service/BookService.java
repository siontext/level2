package com.sparta.level2.service;

import com.sparta.level2.dto.CreateBookRequestDto;
import com.sparta.level2.dto.CreateBookResponseDto;
import com.sparta.level2.entity.Book;
import com.sparta.level2.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    //생성자 주입
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    //도서 등록 서비스
    public CreateBookResponseDto createBook(CreateBookRequestDto requestDto) {

        //dto에 받아온 데이터로 -> 엔티티 객체로 생성
        Book book = new Book(requestDto);

        //DB 저장
        bookRepository.save(book);

        //엔티티 -> DTO 변환
        CreateBookResponseDto responseDto = new CreateBookResponseDto(book);

        //반환해주기
        return responseDto;

    }

    //선택한 도서정보 조회 서비스
    public CreateBookResponseDto getBook(Long bookId)  {

        //id로 책 찾기 -> 1차 캐시에 저장하기 위해(영속성 컨텍스트)
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new IllegalArgumentException("해당 책이 없습니다."));

        //찾은 책 엔티티 -> DTO로 변환 (반환타입을 맞추기 위해)
        CreateBookResponseDto responseDto = new CreateBookResponseDto(book);

        return responseDto;
    }


    public List<CreateBookResponseDto> getAllBooks() {

        //모든 책 조회하기
        List<Book> books = bookRepository.findAll();

        //책을 담아줄 List Dto
        List<CreateBookResponseDto> responseDtos = new ArrayList<>();

        //for 문으로 책들을 뽑아 -> List DTO에 담아주기
        for (Book book : books) {
            CreateBookResponseDto responseDto = new CreateBookResponseDto(book); //book 객체를 -> DTO 변환
            responseDtos.add(responseDto); //리스트에 담아주기
        }

        //DTO 리스트를 오름차순정렬 ->Comparator인터페이스의 comparing 비교 메서드를 통해 (CreateBookResponseDto의getCreatedAt메서드 호출)
        responseDtos.sort(Comparator.comparing(CreateBookResponseDto::getCreatedAt));




        //책들정보 담은 DTO 리스트 반환
        return responseDtos;
    }

}
