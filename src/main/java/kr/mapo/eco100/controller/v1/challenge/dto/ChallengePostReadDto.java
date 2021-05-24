package kr.mapo.eco100.controller.v1.challenge.dto;

import java.time.format.DateTimeFormatter;
import kr.mapo.eco100.entity.ChallengePost;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChallengePostReadDto {

    private Long challengePostId;

    private String contents;

    private String imageUrl;

    private String subject;

    private String date;

    public ChallengePostReadDto(ChallengePost post) {
        this.challengePostId = post.getChallengePostId();
        this.contents = post.getContents();
        this.imageUrl = post.getImageUrl();
        this.subject = post.getChallengeUser().getChallenge().getSubject();
        this.date = post.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy. MM. dd."));
    }

}
