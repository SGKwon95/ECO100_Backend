package kr.mapo.eco100.controller.v1.board.dto;

import java.time.format.DateTimeFormatter;

import kr.mapo.eco100.entity.Board;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardsResponse {
    private Long board_id;

    private String user_nickname;

    private String title;

    private Integer comments_cnt;

    private Integer likes_cnt;

    private String date;

    public BoardsResponse(Board board) {
        this.board_id = board.getBoardId();
        this.user_nickname = board.getUser().getNickname();
        this.title = board.getTitle();
        this.comments_cnt = (board.getComments() != null)? board.getComments().size() : 0;
        this.likes_cnt = board.getLikes();
        this.date = (board.getCreatedDate() != null)? board.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : null;
    }
}
