package com.example.newsfeedproject.user.controller;

import com.example.newsfeedproject.user.dto.request.CreateUserRequestDto;
import com.example.newsfeedproject.user.dto.request.DeleteUserRequestDto;
import com.example.newsfeedproject.user.dto.request.UpdateUserInfoRequestDto;
import com.example.newsfeedproject.user.dto.request.UpdateUserPasswordRequestDto;
import com.example.newsfeedproject.user.dto.response.CreateUserResponseDto;
import com.example.newsfeedproject.user.dto.response.GetUserResponseDto;
import com.example.newsfeedproject.user.service.UserService;
import com.example.newsfeedproject.user.dto.response.UserListResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<UserListResponse> findAllUser(@RequestParam(required = false) String userName) {
        UserListResponse users = userService.findAllUser(userName);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<GetUserResponseDto> findUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @PatchMapping("/users/info")
    public ResponseEntity<Void> updateUserInfo(@Valid @RequestBody UpdateUserInfoRequestDto dto) {
        userService.updateUserInfo(
                dto.getEmail(),
                dto.getUsername(),
                dto.getPassword(),
                dto.getDescription(),
                dto.getImageUrl()
        );
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/users/password")
    public ResponseEntity<Void> updateUserPassword(@Valid @RequestBody UpdateUserPasswordRequestDto dto) {
        userService.updateUserPassword(
                dto.getEmail(),
                dto.getOldPassword(),
                dto.getNewPassword()
        );
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/users/signup")
    public ResponseEntity<CreateUserResponseDto> saveUser(@Valid @RequestBody CreateUserRequestDto dto) {
        return ResponseEntity.ok(userService.saveUser(
                dto.getEmail(),
                dto.getPassword(),
                dto.getUsername(),
                dto.getDescription(),
                dto.getImageUrl())
        );
    }

    @DeleteMapping("/users")
    public ResponseEntity<Void> deleteUser(@Valid @RequestBody DeleteUserRequestDto dto) {
        userService.deleteUser(dto.getEmail(), dto.getPassword());
        return ResponseEntity.noContent().build();
    }
}
