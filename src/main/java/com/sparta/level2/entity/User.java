package com.sparta.level2.entity;


import com.sparta.level2.dto.UserRequestDto;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String ssn;

    private String phoneNum;

    private String address;



    public User() {
    }

    public User(UserRequestDto userRequestDto) {
        this.name = userRequestDto.getName();
        this.gender = userRequestDto.getGender();
        this.ssn = userRequestDto.getSsn();
        this.phoneNum = userRequestDto.getPhoneNumber();
        this.address = userRequestDto.getAddress();
    }
}
