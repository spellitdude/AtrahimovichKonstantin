package com.example.userservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private Integer age;
    private LocalDateTime createdAt;

    public UserDto(long l, String john, String mail, int i, LocalDateTime now) {
    }

    public Long getId() {
        return null;
    }
}