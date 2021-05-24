package kr.mapo.eco100.controller.v1.challenge.dto;

import kr.mapo.eco100.entity.ChallengePost;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChallengePostReadDto {

    private Long challengePostId;

    private String contents;

    private String imageUrl;

    private Long challengeId;

    public ChallengePostReadDto(ChallengePost post) {
        this.challengePostId = post.getChallengePostId();
        this.contents = post.getContents();
        this.imageUrl = post.getImageUrl();
        this.challengeId = post.getChallengeId();
    }

}
