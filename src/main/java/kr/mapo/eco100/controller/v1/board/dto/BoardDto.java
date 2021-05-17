package kr.mapo.eco100.controller.v1.board.dto;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import kr.mapo.eco100.entity.Board;
import kr.mapo.eco100.entity.Comment;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardDto {

    private Long board_id;

    private String user_nickname;

    private String title;

    private String contents;

    private String image_url;

    private List<Comment> comments;

    private Integer comments_cnt;

    private Integer likes_cnt;

    private Boolean can_click_likes;

    private String date;

    public BoardDto(Board board, boolean can_click_likes){
        
        this.board_id = board.getBoardId();
        this.user_nickname = board.getUser().getNickname();
        this.title = board.getTitle();
        this.contents = board.getContents();
        this.image_url = board.getImageUrl();
        if (board.getComments() != null) {
            this.comments = board.getComments();
            this.comments_cnt = this.comments.size();
        } else {
            this.comments = Collections.emptyList();
            this.comments_cnt = 0;
        }
        this.likes_cnt = board.getLikes();
        this.can_click_likes = can_click_likes;
        this.date = (board.getModifiedDate() != null)? board.getModifiedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : null;
    }
}
