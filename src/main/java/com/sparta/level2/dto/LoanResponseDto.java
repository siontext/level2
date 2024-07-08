package com.sparta.level2.dto;

import com.sparta.level2.entity.Book;
import com.sparta.level2.entity.LoansBook;
import com.sparta.level2.entity.User;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class LoanResponseDto {

    private String userName;
    private String phoneNum;
    private String title;
    private String writer;
    private LocalDate loanDate;
    private LocalDate returnDate;



    public LoanResponseDto(User user, Book book, LoansBook loansBook) {
        this.userName = user.getName();
        this.phoneNum = user.getPhoneNum();
        this.title = book.getTitle();
        this.writer = book.getWriter();
        this.loanDate = loansBook.getLoneDate();
        this.returnDate = loansBook.getReturnDate();
    }


}
