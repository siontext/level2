package com.sparta.level2.service;

import com.sparta.level2.dto.LoanResponseDto;
import com.sparta.level2.entity.Book;
import com.sparta.level2.entity.LoansBook;
import com.sparta.level2.entity.User;
import com.sparta.level2.repository.BookRepository;
import com.sparta.level2.repository.LoansRepository;
import com.sparta.level2.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class LoansService {

    private final LoansRepository loansRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public LoansService(LoansRepository loansRepository, UserRepository userRepository, BookRepository bookRepository) {
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


    //대출 내역 조회
    public List<LoanResponseDto> getAllLoans(Long userId) {

        //대출 내역을 담아 반환해줄 컬렉션리스트
        List<LoanResponseDto> responseDtos = new ArrayList<>();

        //회원 확인
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        //대출정보객체에 -> 같은 userId를 매핑 해주기
        List<LoansBook> loansBooks = loansRepository.findByUserId(userId);

        //LoansBook(대출정보)에 userId 매핑했고, 이번엔 bookId를 매핑해 줘야함 (반복문이니깐  List랑 비슷)
        for (LoansBook loansBook : loansBooks) {
            Book book = bookRepository.findById(loansBook.getId()).orElseThrow(
                    () -> new IllegalArgumentException("도서가 존재하지 않습니다."));

            //dto에 반환에 필요한 데이터들 담아주기 (이렇게 담아주면
            LoanResponseDto loanResponseDto = new LoanResponseDto(user, book, loansBook);
            responseDtos.add(loanResponseDto); // 컬렉션 리스트에 담아주기
        }

        //오름차순 정렬해주기
        responseDtos.sort(Comparator.comparing(LoanResponseDto::getLoanDate));

        return responseDtos;
    }

}
