package kr.mapo.eco100.controller.v1.challenge;

import java.io.IOException;
import java.util.List;

import kr.mapo.eco100.controller.v1.board.dto.BoardsResponse;
import kr.mapo.eco100.controller.v1.challenge.dto.ChallengeCreateRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.mapo.eco100.controller.v1.challenge.dto.ChallengeOfTheMonthDTO;
import kr.mapo.eco100.controller.v1.challenge.dto.ChallengePostReadDto;
import kr.mapo.eco100.service.ChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.validation.Valid;

@Api(value = "챌린지 정보")
@RestController
@RequiredArgsConstructor
public class ChallengeController {
    
    private final ChallengeService challengeService;

    @ApiOperation(value = "이 달의 챌린지 불러오기(5개)")
    @GetMapping("/challenge/{userId}")
    public ResponseEntity<List<ChallengeOfTheMonthDTO>> challengeOfTheMonth(@PathVariable("userId") Long id) {

        return ResponseEntity.ok(challengeService.getChallenges(id));
    }

    @ApiOperation(value = "챌린지 도전하기")
    @PostMapping(value = "/challenge/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> create(MultipartHttpServletRequest request) throws IOException {

        @Valid
        ChallengeCreateRequest createRequest = ChallengeCreateRequest.builder()
                .userId(Long.parseLong(request.getParameter("userId")))
                .challengeId(Long.parseLong(request.getParameter("challengeId")))
                .contents(request.getParameter("contents"))
                .multipartFile(request.getFile("image"))
                .build();

        challengeService.create(createRequest);

        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "내가 도전한 챌린지 보기(1개)")
    @GetMapping(value = "/challenge/read/{challengePostId}")
    public ResponseEntity<ChallengePostReadDto> read(@PathVariable(value = "challengePostId") Long id) {

        return ResponseEntity.ok(new ChallengePostReadDto(challengeService.read(id)));
    }


    @ApiOperation(value = "챌린지 수정하기")
    @PutMapping(value = "/challenge/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BoardsResponse> update(MultipartHttpServletRequest request) {

        @Valid
        ChallengeCreateRequest createRequest = ChallengeCreateRequest.builder()
                .userId(Long.parseLong(request.getParameter("userId")))
                .challengeId(Long.parseLong(request.getParameter("challengeId")))
                .contents(request.getParameter("contents"))
                .multipartFile(request.getFile("image"))
                .build();

        challengeService.update(createRequest);

        return ResponseEntity.noContent().build();
    }

   @ApiOperation(value = "내가 도전한 모든 챌린지 글 보기")
   @GetMapping(value = "/challenge/post/{userId}")
   public ResponseEntity<List<ChallengePostReadDto>> myChallengePosts(@PathVariable("userId") Long userId) {

       return ResponseEntity.ok(challengeService.myChallengePosts(userId));
   }
}
