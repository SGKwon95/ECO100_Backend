package kr.mapo.eco100.controller.v1.board.dto;

import kr.mapo.eco100.entity.Board;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static org.springframework.beans.BeanUtils.copyProperties;

@Getter
@Setter
@ToString
public class BoardDto {

    @Size(max = 30, message = "제목은 30자 이하로 작성해주세요")
    @NotBlank(message = "제목은 필수로 입력해야 합니다")
    private String title;

    @NotBlank(message = "내용은 필수로 입력해야 합니다")
    private String contents;

    private String imageUrl;

    public BoardDto(Board source) {
        copyProperties(source,this);
    }
}
