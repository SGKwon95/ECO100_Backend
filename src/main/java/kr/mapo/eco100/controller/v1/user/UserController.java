package kr.mapo.eco100.controller.v1.user;

import kr.mapo.eco100.controller.v1.user.dto.JoinRequest;
import kr.mapo.eco100.controller.v1.user.dto.UserDto;
import kr.mapo.eco100.entity.User;
import kr.mapo.eco100.service.UserService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/user/create")
    public ResponseEntity<UserDto> join(@RequestBody JoinRequest joinRequest) {

        return ResponseEntity.ok(
            new UserDto(userService.join(joinRequest))
        );
    }

    // public ResponseEntity<UserDto> login(@RequestBody JoinRequest joinRequest) {
    //     return ResponseEntity.ok(userService.find(joinRequest.getKakaoId()));
    // }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/badgelist/{userId}")
    public ResponseEntity<List<Boolean>> getBadgeList(@PathVariable Long userId) {

        return ResponseEntity.ok(
            userService.getBadgeList(userId)
        );
    }

}
