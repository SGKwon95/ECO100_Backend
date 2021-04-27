package kr.mapo.eco100.controller.v1.board.dto.board;

import kr.mapo.eco100.entity.Board;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.beans.BeanUtils.copyProperties;

@Getter
@Setter
@ToString
@Slf4j
public class BoardDto {

    private Long board_id;

    private String user_nickname;

    private String title;

    private String contents;

    private String image_url;

    private Integer comments_cnt;

    private Integer likes_cnt;

    public BoardDto(Board board) {
        //copyProperties(source,this);
        log.info(board.toString());
        this.board_id = board.getBoardId();
        this.user_nickname = board.getUser().getNickname();
        this.title = board.getTitle();
        this.contents = board.getContents();
        this.image_url = board.getImageUrl();
        this.comments_cnt = 0;
        this.likes_cnt = 0;
    }
}
