package kr.mapo.eco100.controller.v1.challenge;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.mapo.eco100.controller.v1.challenge.dto.ChallengeOfTheMonthDTO;
import kr.mapo.eco100.service.ChallengeService;
import lombok.RequiredArgsConstructor;

@Api(value = "챌린지 정보")
@RestController
@RequiredArgsConstructor
public class ChallengeController {
    
    private final ChallengeService challengeService;

    @ApiOperation(value = "이 달의 챌린지 불러오기(5개)")
    @GetMapping("/challenge/{userId}")
    public ResponseEntity<List<ChallengeOfTheMonthDTO>> challengeOfTheMonth(@PathVariable("userId") Long id) {

        return ResponseEntity.ok(
            challengeService.getChallenges(id)
        );
    }
}
