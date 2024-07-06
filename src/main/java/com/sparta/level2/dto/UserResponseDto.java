package com.sparta.level2.dto;

import com.sparta.level2.entity.Gender;
import com.sparta.level2.entity.User;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private Long id;
    private String name;
    private Gender gender;
    private String phoneNumber;
    private String address;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.gender = user.getGender();
        this.phoneNumber = user.getPhoneNum();
        this.address = user.getAddress();
    }
}
