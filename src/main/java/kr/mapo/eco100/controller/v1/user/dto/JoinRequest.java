package kr.mapo.eco100.controller.v1.user.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JoinRequest {

    private Long kakaoId;

    private String nickname;
}
