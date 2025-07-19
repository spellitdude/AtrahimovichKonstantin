package com.example.userservice;

import com.example.userservice.controller.UserController;
import com.example.userservice.dto.UserDto;
import com.example.userservice.dto.UserRequestDto;
import com.example.userservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void shouldReturnAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(List.of(new UserDto(1L, "John", "john@example.com", 30, LocalDateTime.now())));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded").exists());
    }

    @Test
    void shouldReturnUserById() throws Exception {
        UserDto user = new UserDto(1L, "John", "john@example.com", 30, LocalDateTime.now());
        when(userService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"));
    }

    @Test
    void shouldCreateUser() throws Exception {
        UserRequestDto dto = new UserRequestDto("John", "john@example.com", 30);
        UserDto created = new UserDto(1L, "John", "john@example.com", 30, LocalDateTime.now());

        when(userService.createUser(dto)).thenReturn(created);

        mockMvc.perform(post("/api/users")
                        .contentType("application/json")
                        .content("{\"name\":\"John\",\"email\":\"john@example.com\",\"age\":30}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }
}