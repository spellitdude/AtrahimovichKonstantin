package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
import com.example.userservice.dto.UserRequestDto;
import com.example.userservice.service.UserService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public CollectionModel<EntityModel<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        List<EntityModel<UserDto>> userResources = users.stream()
                .map(user -> EntityModel.of(user,
                        linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel()))
                .collect(Collectors.toList());

        return CollectionModel.of(userResources,
                linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<UserDto> getUserById(@PathVariable Long id) {
        UserDto user = userService.getUserById(id);
        return EntityModel.of(user,
                linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel(),
                linkTo(methodOn(UserController.class).getAllUsers()).withRel("all-users"));
    }

    @PostMapping
    public EntityModel<UserDto> createUser(@RequestBody UserRequestDto dto) {
        UserDto createdUser = userService.createUser(dto);
        return EntityModel.of(createdUser,
                linkTo(methodOn(UserController.class).getUserById(createdUser.getId())).withSelfRel());
    }

    @PutMapping("/{id}")
    public EntityModel<UserDto> updateUser(@PathVariable Long id, @RequestBody UserRequestDto dto) {
        UserDto updatedUser = userService.updateUser(id, dto);
        return EntityModel.of(updatedUser,
                linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}