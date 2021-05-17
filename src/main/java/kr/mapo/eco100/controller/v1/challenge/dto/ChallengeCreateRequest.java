package kr.mapo.eco100.controller.v1.challenge.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
@Builder
public class ChallengeCreateRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long challengeId;

    @NotNull
    @NotBlank(message = "내용은 필수로 입력해야 합니다")
    private String contents;

    @NotNull
    private MultipartFile multipartFile;

}