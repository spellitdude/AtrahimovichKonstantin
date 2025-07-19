package com.example.userservice.dto;

import lombok.Data;

@Data
public class UserRequestDto {
    private String name;
    private String email;
    private Integer age;

    public UserRequestDto(String john, String mail, int i) {
    }
}