package com.example.newsfeedproject.user.service;

import com.example.newsfeedproject.user.dto.response.CreateUserResponseDto;
import com.example.newsfeedproject.user.dto.response.UserListResponse;
import com.example.newsfeedproject.user.dto.response.GetUserResponseDto;
import com.example.newsfeedproject.user.entity.DeletedUser;
import com.example.newsfeedproject.user.entity.User;
import com.example.newsfeedproject.user.repository.DeletedUserRepository;
import com.example.newsfeedproject.user.repository.UserRepository;
import com.example.newsfeedproject.user.repository.spec.UserSpecification;
import com.example.newsfeedproject.util.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DeletedUserRepository deletedUserRepository;

    public CreateUserResponseDto saveUser(String email, String password, String username, String description, String imageUrl) {

        // email 중복 확인
        if (userRepository.findByEmail(email).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 존재하는 사용자 입니다.");
        }

        // 삭제된 사용자인지 확인
        if (deletedUserRepository.findByEmail(email).isPresent()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "이미 탈퇴한 사용자 입니다.");
        }

        // 중복 X -> 비밀번호 encrypt 및 user entity 생성
        User newUser = User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .username(username)
                .description(description)
                .imageUrl(imageUrl)
                .build();

        // user entity 저장
        userRepository.save(newUser);

        // 저장된 entity -> dto 변환 및 반환
        return new CreateUserResponseDto(userRepository.findByEmailOrElseThrow(email));
    }

    @Transactional
    public void deleteUser(String email, String password) {

        // 사용자 검증
        User savedUser = verifyUserOrElseThrow(email, password);

        // 검증 O -> 삭제된 사용자 테이블(DB)에 저장
        // 탈퇴한 사용자의 아이디는 재사용할 수 없고, 복구할 수 없습니다. <-- 해당 이메일을 재사용 할 수 없다는 뜻?
        deletedUserRepository.save(DeletedUser.of(email));

        // 사용자 삭제
        userRepository.delete(savedUser);
    }

    public User verifyUserOrElseThrow(String email, String password) {
        // 유저가 존재하는지 확인
        User savedUser = userRepository.findByEmailOrElseThrow(email);

        // 비밀번호 검증
        passwordEncoder.verify(password, savedUser.getPassword());
        return savedUser;
    }

    public UserListResponse findAllUser(String userName) {
        Specification<User> spec = Specification.where(UserSpecification.hasUsername(userName));
        return UserListResponse.from(userRepository.findAll(spec));
    }

    public GetUserResponseDto findUserById(Long id) {
        User savedUser = userRepository.findByIdOrElseThrow(id);

        return new GetUserResponseDto(savedUser);
    }

    public void updateUserInfo(String email, String username, String password, String description, String imageUrl) {
        User verified = verifyUserOrElseThrow(email, password);

        verified.updateUserInfo(username, description, imageUrl);
        userRepository.save(verified);
    }

    public void updateUserPassword(String email, String oldPassword, String newPassword) {
        User verified = verifyUserOrElseThrow(email, oldPassword);

        verified.updateUserPassword(newPassword);
        userRepository.save(verified);
    }
}
