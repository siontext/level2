package com.sparta.level2.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
public class LoansBook { //대출 현황

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bookId;
    private Long userId;

    private boolean returned; //반납상태 (여부)

    private LocalDate loneDate;   //대출일
    private LocalDate returnDate;  //반납일


    //대출 생성자
    public LoansBook(Long bookId, Long userId) {
        this.bookId = bookId;
        this.userId = userId;
        this.returned = false;
        this.loneDate = LocalDate.now();

    }

    public LoansBook() {
    }
}
