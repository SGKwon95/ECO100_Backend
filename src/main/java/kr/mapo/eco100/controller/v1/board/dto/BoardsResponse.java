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
    private Long boardId;

    private String nickname;

    private String title;

    private Integer commentsCnt;

    private Integer likesCnt;

    private String date;

    public BoardsResponse(Board board) {
        this.boardId = board.getBoardId();
        this.nickname = board.getUser().getNickname();
        this.title = board.getTitle();
        this.commentsCnt = (board.getComments() != null)? board.getComments().size() : 0;
        this.likesCnt = board.getLikes();
        this.date = board.getModifiedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
