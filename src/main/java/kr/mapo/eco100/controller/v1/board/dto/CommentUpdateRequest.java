package kr.mapo.eco100.controller.v1.board.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class CommentUpdateRequest {
    
    @NotNull
    private Long commentId;

    @NotBlank(message = "내용은 필수로 입력해야 합니다")
    private String contents;

}
