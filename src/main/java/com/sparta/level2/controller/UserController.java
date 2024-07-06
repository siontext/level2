package com.sparta.level2.controller;

import com.sparta.level2.dto.UserRequestDto;
import com.sparta.level2.dto.UserResponseDto;
import com.sparta.level2.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    //도서관 회원 등록
    @PostMapping("/member")
    public ResponseEntity<UserResponseDto> userCreate(@RequestBody UserRequestDto userRequestDto) {

        UserResponseDto responseDto = userService.userCreate(userRequestDto);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}
