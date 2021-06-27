package kr.mapo.eco100.controller.v1.user.dto;

import kr.mapo.eco100.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDto {

    private Long userId;

    private String nickname;

    public UserDto(User user) {
        this.userId = user.getUserId();
        this.nickname = user.getNickname();
    }

}
