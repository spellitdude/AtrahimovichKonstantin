package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.dto.UserRequestDto;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(u -> modelMapper.map(u, UserDto.class))
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(u -> modelMapper.map(u, UserDto.class))
                .orElse(null);
    }

    public UserDto createUser(UserRequestDto dto) {
        User user = modelMapper.map(dto, User.class);
        user.setCreatedAt(LocalDateTime.now());
        return modelMapper.map(userRepository.save(user), UserDto.class);
    }

    public UserDto updateUser(Long id, UserRequestDto dto) {
        return userRepository.findById(id)
                .map(user -> {
                    modelMapper.map(dto, user);
                    return modelMapper.map(userRepository.save(user), UserDto.class);
                })
                .orElse(null);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}