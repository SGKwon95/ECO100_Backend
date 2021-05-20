package kr.mapo.eco100.controller.v1.challenge.dto;

import java.util.List;

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

    private Integer myParticipationCnt;

    private List<Long> challengePostIdList;

    public ChallengeOfTheMonthDTO(Challenge challenge, List<Long> challengePostIdList) {
        this.challengeId = challenge.getChallengeId();
        this.subject = challenge.getSubject();
        this.numOfParticipants = challenge.getNumOfParticipants();
        this.imageUrl = challenge.getImageUrl();
        this.challengePostIdList = challengePostIdList;
        this.myParticipationCnt = challengePostIdList.size();
    }
}
