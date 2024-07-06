package com.sparta.level2.service;

import com.sparta.level2.dto.UserRequestDto;
import com.sparta.level2.dto.UserResponseDto;
import com.sparta.level2.entity.User;
import com.sparta.level2.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    //생성자 주입
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    // 도서관 회원 등록 서비스
    public UserResponseDto userCreate(UserRequestDto userRequestDto) {

        //받은 DTO 데이터로 -> 엔티티로 만들기
        User user = new User(userRequestDto);

        //주민번호 중복인지 검증
        if (isSsnDuplicate(user.getSsn())) {
            throw new IllegalArgumentException("이미 사용중인 주민등록번호입니다.");
        }

        //전화번호 중복인지 검증
        if (isPhoneNumDuplicate(user.getPhoneNum())) {
            throw new IllegalArgumentException("이미 등록된 전화번호입니다.");
        }

        //DB에 저장하기
        userRepository.save(user);

        //엔티티 -> DTO로 반환해주기
        UserResponseDto responseDto = new UserResponseDto(user);
        return responseDto;

    }





    // 주민등록 번호 중복 검증 메서드
    private boolean isSsnDuplicate(String ssn) {
        return userRepository.existsBySsn(ssn); //주민번호 있으면(중복) -> True 반환
    }

    //전화번호 중복검증 메서드
    private boolean isPhoneNumDuplicate(String phoneNumber) {
        return userRepository.existsByPhoneNum(phoneNumber); //전화번호 있으면(중복) -> True 반환
    }

}
