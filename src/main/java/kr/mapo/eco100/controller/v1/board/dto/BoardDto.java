package kr.mapo.eco100.controller.v1.board.dto;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import kr.mapo.eco100.controller.v1.comment.dto.CommentDto;
import kr.mapo.eco100.entity.Board;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardDto {

    private Long boardId;

    private Long userId;

    private String nickname;

    private String title;

    private String contents;

    private String imageUrl;

    private List<CommentDto> comments;

    private Integer commentsCnt;

    private Integer likesCnt;

    private Boolean canClickLikes;

    private String date;

    public BoardDto(Board board, boolean can_click_likes){
        
        this.boardId = board.getBoardId();
        this.userId = board.getUser().getUserId();
        this.nickname = board.getUser().getNickname();
        this.title = board.getTitle();
        this.contents = board.getContents();
        this.imageUrl = board.getImageUrl();
        if (board.getComments() != null) {
            this.comments = board.getComments().stream().map(CommentDto::new).collect(Collectors.toList());
            this.commentsCnt = this.comments.size();
        } else {
            this.comments = Collections.emptyList();
            this.commentsCnt = 0;
        }
        this.likesCnt = board.getLikes();
        this.canClickLikes = can_click_likes;
        this.date = (board.getModifiedDate() != null)? board.getModifiedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : null;
    }
}
