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
public class CommentCreateRequest {
    
    @NotNull
    private Long userId;

    @NotNull
    private Long boardId;

    @NotBlank(message = "내용은 필수로 입력해야 합니다")
    private String contents;

}
