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

    public ReadRequest(Long boardId, Long userId) {
        this.boardId = boardId;
        this.userId = userId;
    }
}
