package kr.mapo.eco100.controller.v1.challenge.dto;

import kr.mapo.eco100.entity.Challenge;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChallengeOfTheMonthDTO {
    
    private Long challengeId;

    private String subject;

    private Integer numOfParticipants;

    private String imageUrl;

    private Integer participationCnt;

    public ChallengeOfTheMonthDTO(Challenge challenge, Integer count) {
        this.challengeId = challenge.getChallengeId();
        this.subject = challenge.getSubject();
        this.numOfParticipants = challenge.getNumOfParticipants();
        this.imageUrl = challenge.getImageUrl();
        this.participationCnt = count;
    }
}
