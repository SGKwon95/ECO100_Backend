package kr.mapo.eco100.service;

import org.springframework.stereotype.Service;

import kr.mapo.eco100.controller.v1.user.dto.JoinRequest;
import kr.mapo.eco100.controller.v1.user.dto.UserDto;
import kr.mapo.eco100.entity.User;
import kr.mapo.eco100.error.UserNotFoundException;
import kr.mapo.eco100.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDto join(JoinRequest joinRequest) {
        return new UserDto(
                userRepository.save(
                    User.builder()
                            .userNo(joinRequest.getUserNo())
                            .nickname(joinRequest.getNickname())
                            .level(1)
                            .build()
                    )
                );
    }

    public UserDto find(Long userId) {
        return userRepository.findById(userId).map(UserDto::new)
                .orElseThrow(() -> new UserNotFoundException("해당 유저가 없습니다."));
    }

    public void levelUp(Long userId) {
        userRepository.findById(userId).map(user -> {
            user.setLevel(user.getLevel() + 1);
            return userRepository.save(user);
        }).orElseThrow(() -> new UserNotFoundException("해당 유저가 없습니다."));
    }

    public void delete(Long userId) {
        userRepository.delete(
            userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("해당 유저가 없습니다."))
        );
    }
}
