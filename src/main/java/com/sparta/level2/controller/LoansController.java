package com.sparta.level2.controller;

import com.sparta.level2.dto.LoanResponseDto;
import com.sparta.level2.service.LoansService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LoansController {

    private final LoansService loansService;

    public LoansController(LoansService loansService) {
        this.loansService = loansService;
    }


    //선택한 도서 대출 컨트롤러
    @PostMapping("/loan/{userId}/{bookId}")
    public ResponseEntity<String> loanBook(@PathVariable Long bookId, @PathVariable Long userId) {

        //서비스 반환타입 String
        String result = loansService.loanBook(bookId, userId);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    //선택한 도서 반납
    @PostMapping("/book/{userId}/{bookId}")
    public ResponseEntity<String> returnBook(@PathVariable Long userId, @PathVariable Long bookId) {

        //서비스 반환타입 String
        String result = loansService.returnBook(userId, bookId);

        //ResponseEntity에 담아주기
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //대출 내역 조회
    @GetMapping("/loan/history/{userId}")
    public ResponseEntity<List<LoanResponseDto>> getAllLoans(@PathVariable Long userId) {

        //서비스 반환타입 List<LoanResponseDto>
        List<LoanResponseDto> responseDto = loansService.getAllLoans(userId);

        //ResponseEntity에 담아주기
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
