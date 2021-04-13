package kr.mapo.eco100.controller.v1.user;

import kr.mapo.eco100.controller.v1.user.dto.JoinRequest;
import kr.mapo.eco100.controller.v1.user.dto.UserDto;
import kr.mapo.eco100.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/user/create")
    public ResponseEntity<UserDto> join(@RequestBody JoinRequest joinRequest) {

        return ResponseEntity.ok(
            userService.join(joinRequest)
        );
    }

    @GetMapping("/user/find/{id}")
    public ResponseEntity<UserDto> find(@PathVariable Long id) {

        return ResponseEntity.ok(
            userService.find(id)
        );
    }

    @PatchMapping("/user/levelup/{id}")
    public ResponseEntity<Void> levelUp(@PathVariable Long id) {
        userService.levelUp(id);
        
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        
        return ResponseEntity.noContent().build();
    }

}
