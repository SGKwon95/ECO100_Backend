package kr.mapo.eco100.controller.v1.board.dto;

import kr.mapo.eco100.entity.Board;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static org.springframework.beans.BeanUtils.copyProperties;

@Getter
@Setter
@ToString
public class BoardDto {

    private Long boardId;

    private Long userId;

    private String title;

    private String contents;

    private String imageUrl;

    public BoardDto(Board source) {
        copyProperties(source,this);
    }
}
