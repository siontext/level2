package com.sparta.level2.controller;

import com.sparta.level2.service.LoansService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
        String result = loansService.loanBook(bookId, userId);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


//    //선택한 도서 반납
//    @PostMapping("/book/{bookId}")
//    public ResponseEntity<String> returnBook(@PathVariable Long bookId) {
//
//    }
}
