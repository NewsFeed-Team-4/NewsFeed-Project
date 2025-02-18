package com.example.newsfeedproject.user.controller;

import com.example.newsfeedproject.user.dto.request.CreateUserRequestDto;
import com.example.newsfeedproject.user.dto.request.DeleteUserRequestDto;
import com.example.newsfeedproject.user.dto.request.UpdateUserRequestDto;
import com.example.newsfeedproject.user.dto.response.CreateUserResponseDto;
import com.example.newsfeedproject.user.dto.response.GetUserResponseDto;
import com.example.newsfeedproject.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user")
    public ResponseEntity<List<GetUserResponseDto>> findAllUser() {
        return ResponseEntity.ok(userService.findAllUser());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> findUserById(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/user")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserRequestDto dto) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user/signup")
    public ResponseEntity<CreateUserResponseDto> saveUser(@RequestBody CreateUserRequestDto dto) {
        return ResponseEntity.ok(userService.saveUser(dto.getEmail(), dto.getPassword(), dto.getUsername()));
    }

    @DeleteMapping("/user")
    public ResponseEntity<Void> deleteUser(@RequestBody DeleteUserRequestDto dto) {
        userService.deleteUser(dto.getEmail(), dto.getPassword());
        return ResponseEntity.noContent().build();
    }
}
