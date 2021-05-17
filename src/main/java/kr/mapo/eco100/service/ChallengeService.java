package kr.mapo.eco100.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import kr.mapo.eco100.controller.v1.challenge.dto.ChallengeCreateRequest;
import kr.mapo.eco100.entity.ChallengePost;
import kr.mapo.eco100.error.ChallengeNotFoundException;
import kr.mapo.eco100.error.ChallengePostNotFoundException;
import kr.mapo.eco100.repository.challenge.ChallengePostRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import kr.mapo.eco100.controller.v1.challenge.dto.ChallengeOfTheMonthDTO;
import kr.mapo.eco100.entity.Challenge;
import kr.mapo.eco100.entity.ChallengeUser;
import kr.mapo.eco100.entity.User;
import kr.mapo.eco100.error.UserNotFoundException;
import kr.mapo.eco100.repository.UserRepository;
import kr.mapo.eco100.repository.challenge.ChallengeRepository;
import kr.mapo.eco100.repository.challenge.ChallengeUserRespoitory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final ChallengeUserRespoitory challengeUserRepository;
    private final ChallengePostRepository challengePostRepository;
    private final UserRepository userRepository;

    public List<ChallengeOfTheMonthDTO> getChallenges(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));

        int month = LocalDateTime.now().getMonthValue();
        Page<Challenge> page = challengeRepository
                .findAll(PageRequest.of((month - 1) % 6, 5));

        List<Challenge> challengeList = page.getContent();
        List<ChallengeOfTheMonthDTO> result = new ArrayList<ChallengeOfTheMonthDTO>();
        Optional<List<ChallengeUser>> challengeUserTable = challengeUserRepository.findByUserAndChallengeIn(user, challengeList);

        if (challengeUserTable.isPresent()) {
            boolean[] isPicked = new boolean[31];

            challengeUserTable.get().forEach(obj -> {
                isPicked[obj.getChallenge().getChallengeId().intValue()] = true;
                result.add(new ChallengeOfTheMonthDTO(obj.getChallenge(), obj.getCount()));
            });

            challengeList.forEach(obj -> {
                if (!isPicked[obj.getChallengeId().intValue()]) {
                    result.add(new ChallengeOfTheMonthDTO(obj, 0));
                }
            });
            result.sort(Comparator.comparing(ChallengeOfTheMonthDTO::getChallengeId));
        } else {
            challengeList.forEach(obj -> result.add(new ChallengeOfTheMonthDTO(obj, 0)));
        }
        return result;
    }

    public void increaseParticipation(Long challengeId) {
        challengeRepository.findById(challengeId)
                .map(challenge -> {
                    challenge.setNumOfParticipants(challenge.getNumOfParticipants() + 1);
                    return challengeRepository.save(challenge);
                });
    }

    public void create(ChallengeCreateRequest createRequest) throws IOException {
        User user = userRepository.findById(createRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException("해당 유저가 존재하지 않습니다"));

        Challenge target = challengeRepository.findById(createRequest.getChallengeId())
                .orElseThrow(() -> new ChallengeNotFoundException("해당 챌린지가 존재하지 않습니다"));

        String fileLocation = "./images/challenge/"; // 저장할 경로를 지정한다.
        FileOutputStream fos = null;
        MultipartFile file = createRequest.getMultipartFile();
        String filename = "";
        try {

            File dir = new File(fileLocation);
            dir.mkdirs();// 저장할 경로에 지정된 폴더가 없으면 생성한다. 있으면 아무일도 일어나지 않는다.

            int start = file.getOriginalFilename().lastIndexOf('.');
            String extension = file.getOriginalFilename().substring(start);// 파일 이름의 확장자만 추출한다.
            filename = UUID.randomUUID().toString() + extension;

            File imgfile = new File(fileLocation + filename);
            fos = new FileOutputStream(imgfile);// fileoutputstream은 new로 만들 시 파일을 생성한다.
            fos.write(file.getBytes());// 전송된 이미지를 파일에 저장한다.

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fos.close();
        }

        Optional<ChallengeUser> updateTarget = challengeUserRepository
                .findByUserAndChallenge(user, target);

        ChallengePost.ChallengePostBuilder challengePost = ChallengePost.builder()
                .imageUrl("http://rpinas.iptime.org:10122/image/challenge/" + filename)
                .contents(createRequest.getContents());

        if (updateTarget.isPresent()) {
            challengeUserRepository.findById(updateTarget.get().getChallengeUserId())
                    .map(challengeUser -> {
                        challengeUser.setCount(2);
                        return challengeUserRepository.save(challengeUser);
                    });

            challengePost.challengeUser(
                    challengeUserRepository.findById(updateTarget.get().getChallengeUserId()).get()
            );
        } else {
            challengePost.challengeUser(
                    challengeUserRepository.save(ChallengeUser.builder()
                            .challenge(target)
                            .user(user)
                            .count(1)
                            .build())
            );
            increaseParticipation(createRequest.getChallengeId());
        }
        challengePostRepository.save(
                challengePost
                        .build()
        );
    }

    public ChallengePost read(Long id) {
        ChallengePost post = challengePostRepository.findById(id)
                .orElseThrow(()->new ChallengePostNotFoundException("해당 챌린지 게시물이 존재하지 않습니다."));
        return post;
    }

    public void update(ChallengeCreateRequest createRequest) {

    }
}
