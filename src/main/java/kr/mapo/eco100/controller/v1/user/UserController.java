package kr.mapo.eco100.controller.v1.user;

import kr.mapo.eco100.controller.v1.user.dto.UserDto;
import kr.mapo.eco100.entity.User;
import kr.mapo.eco100.error.UserNotFoundException;
import kr.mapo.eco100.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController("/user")
public class UserController {

    private final UserRepository userRepository;

    @PostMapping("/create")
    public User create(@RequestBody UserDto userDto) {

        return userRepository.save(
                User.builder()
                        .email(userDto.getEmail())
                        .nickname(userDto.getNickname())
                        .level(1)
                        .build()
        );
    }

    @GetMapping("/read")
    public User read(@PathVariable Long id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("해당 유저가 없습니다."));
    }

    @PatchMapping("/levelup/{id}")
    public ResponseEntity<Void> levelUp(@PathVariable Long id) {
        userRepository.findById(id)
                .map(user -> {
                    user.setLevel(user.getLevel() + 1);
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new UserNotFoundException("해당 유저가 없습니다."));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userRepository.delete(
                userRepository.findById(id)
                        .orElseThrow(() -> new UserNotFoundException("해당 유저가 없습니다."))
        );
        return ResponseEntity.noContent().build();
    }

}
