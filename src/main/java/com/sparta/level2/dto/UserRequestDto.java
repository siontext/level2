package com.sparta.level2.dto;

import com.sparta.level2.entity.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserRequestDto {

    private String name;
    private Gender gender;
    @NotNull
    private String ssn;
    @NotNull
    private String phoneNumber;
    private String address;
}
