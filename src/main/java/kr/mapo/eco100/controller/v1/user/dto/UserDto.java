package kr.mapo.eco100.controller.v1.user.dto;

import kr.mapo.eco100.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import static org.springframework.beans.BeanUtils.copyProperties;

@Getter
@Setter
@ToString
public class UserDto {

    private Long userId;

    private String nickname;

    private Integer level;

    public UserDto(User source) { copyProperties(source,this);}
}
