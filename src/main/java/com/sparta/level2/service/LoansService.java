package com.sparta.level2.service;

import com.sparta.level2.entity.Book;
import com.sparta.level2.entity.LoansBook;
import com.sparta.level2.entity.User;
import com.sparta.level2.repository.BookRepository;
import com.sparta.level2.repository.LoansRepository;
import com.sparta.level2.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

        //반납하지 않은 책이 있는지 확인(대출상태엔티티에서 유저 아이디로 확인)
        List<LoansBook> loans = loansRepository.findByUserIdAndReturnedIsFalse(userId); //LoansBook엔티티에 유저아이디로 returned에 False가 있는지 찾기
        if (!loans.isEmpty()) {
            return "아직 반납하지 않은 도서가 있습니다!";
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

    //선택한 도서 반납
    @Transactional
    public String returnBook(Long userId, Long bookId) {

        //회원 확인
        Optional<User> userOptional = userRepository.findById(userId); //Optional 객체 (null처리를 위해)
        if (userOptional.isEmpty()) {
            return "회원이 존재하지 않습니다.";
        }

        //빌린 책 확인
        Optional<LoansBook> loansBookOptional = loansRepository.findById(bookId); //Optional 객체 (null처리를 위해)
        if (loansBookOptional.isEmpty()) {
            return "대출되지 않은 책입니다.";
        }

        //빌린 책 반납상태 -> true로 변환
        LoansBook loansBook = loansBookOptional.get(); //Optional 객체 -> 엔티티 객체로 변환
        loansBook.setReturnedAndDate(); //반납상태 -> true로, 반납일은 오늘로 찍어주는 메서드


        return "반납이 완료되었습니다";

    }

}
