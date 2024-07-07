package com.sparta.level2.service;

import com.sparta.level2.entity.Book;
import com.sparta.level2.entity.LoansBook;
import com.sparta.level2.entity.User;
import com.sparta.level2.repository.BookRepository;
import com.sparta.level2.repository.LoansRepository;
import com.sparta.level2.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class LoansService {

    private final LoansRepository loansRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public LoansService(LoansRepository loansRepository , UserRepository userRepository , BookRepository bookRepository) {
        this.loansRepository = loansRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional //변경감지 기능을 사용하기 위해 트랜잭션 추가
    //선택한 도서 대출 서비스
    public String loanBook(Long bookId, Long userId) {

        //회원 확인
        Optional<User> user = userRepository.findById(userId); //Optional 객체 (null처리를 위해)
        if (user.isEmpty()) {
            return "회원이 존재하지 않습니다.";
        }

        //도서 존재 확인
        Optional<Book> bookOptional = bookRepository.findById(bookId); //이건Optional 객체 (null처리를 위해)
        if (bookOptional.isEmpty()) {
            return "도서가 존재하지 않습니다";
        }

        //Optional 객체를 -> 엔티티로 (안의 메서드 사용하기 위해서)
        Book book = bookOptional.get();

        //대출가능 확인
        if (!book.isAvilable()) { //isAvilable() 기본값이 true
            return "이미 대출된 책입니다.";
        }

        //도서의 대출가능 상태를 false로 변경 메서드
        book.loan();


        //대출하기 (생성자로 대출 객체 생성)
        LoansBook loan = new LoansBook(bookId, userId);
        loansRepository.save(loan); //DB저장


        return "대출되었습니다.";

    }
}
