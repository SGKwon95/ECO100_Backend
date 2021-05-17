package kr.mapo.eco100.controller.v1.board.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReadRequest {
    
    @NotNull
    private Long userId;

    @NotNull
    private Long boardId;
}
