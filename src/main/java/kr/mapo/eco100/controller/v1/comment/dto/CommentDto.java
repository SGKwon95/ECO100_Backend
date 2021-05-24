package kr.mapo.eco100.controller.v1.comment.dto;

import java.time.format.DateTimeFormatter;

import kr.mapo.eco100.entity.Comment;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentDto {

    private Long commentId;

    private Long boardId;

    private String writer;

    private String contents;

    private String date;

    public CommentDto(Comment comment){
        this.commentId = comment.getCommentId();
        this.boardId = comment.getBoard().getBoardId();
        this.writer = comment.getUser().getNickname();
        this.contents = comment.getContents();
        this.date = comment.getModifiedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}


    

    
