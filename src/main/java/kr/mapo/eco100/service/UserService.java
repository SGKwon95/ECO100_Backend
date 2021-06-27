package kr.mapo.eco100.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import kr.mapo.eco100.controller.v1.user.dto.JoinRequest;
import kr.mapo.eco100.entity.Challenge;
import kr.mapo.eco100.entity.ChallengeUser;
import kr.mapo.eco100.entity.User;
import kr.mapo.eco100.error.UserNotFoundException;
import kr.mapo.eco100.repository.BoardRepository;
import kr.mapo.eco100.repository.CommentRepository;
import kr.mapo.eco100.repository.LikesRepository;
import kr.mapo.eco100.repository.UserRepository;
import kr.mapo.eco100.repository.challenge.ChallengeRepository;
import kr.mapo.eco100.repository.challenge.ChallengeUserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final LikesRepository likesRepository;
    private final ChallengeRepository challengeRepository;
    private final ChallengeUserRepository challengeUserRepository;

    public User join(JoinRequest joinRequest) {
        Optional<User> user = userRepository.findByUid(joinRequest.getKakaoId());
        if(user.isPresent()) {
            return user.get();
        } else {
            return userRepository.save(
                    User.builder()
                            .uid(joinRequest.getKakaoId())
                            .nickname(joinRequest.getNickname())
                            .challengeCompleteCnt(0)
                            .build()
                    );
        }        
    }

    public User find(Long kakaoId) {
        return userRepository.findByUid(kakaoId)
                .orElseThrow(() -> new UserNotFoundException("해당 유저가 없습니다."));
    }

    public List<Boolean> getBadgeList(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("해당 유저가 없습니다."));
        int boardCnt = boardRepository.countByUser(user);
        int commentCnt = commentRepository.countByUser(user);
        int likesCnt = likesRepository.countByuserId(userId);
        int challengeCnt = getChallenges(userId);
        Boolean[] isCompleted = new Boolean[12];
        isCompleted[0] = true;
        if(boardCnt > 0) {
            isCompleted[1] = true;
        } else {
            isCompleted[1] = false;
        }
        if(commentCnt > 0) {
            isCompleted[2] = true;
        } else {
            isCompleted[2] = false;
        }
        if(boardCnt >= 10) {
            isCompleted[3] = true;
        } else {
            isCompleted[3] = false;
        }
        if(commentCnt >= 10) {
            isCompleted[4] = true;
        } else {
            isCompleted[4] = false;
        }
        if(likesCnt >= 10) {
            isCompleted[5] = true;
        } else {
            isCompleted[5] = false;
        }
        if(challengeCnt > 0) {
            isCompleted[6] = true;
        } else {
            isCompleted[6] = false;
        }
        if(challengeCnt > 1) {
            isCompleted[7] = true;
        } else {
            isCompleted[7] = false;
        }
        if(challengeCnt > 2) {
            isCompleted[8] = true;
        } else {
            isCompleted[8] = false;
        }
        if(challengeCnt > 3) {
            isCompleted[9] = true;
        } else {
            isCompleted[9] = false;
        }
        if(challengeCnt > 4) {
            isCompleted[10] = true;
        } else {
            isCompleted[10] = false;
        }
        if(challengeCnt > 5) {
            isCompleted[11] = true;
        } else {
            isCompleted[11] = false;
        }
        return Arrays.asList(isCompleted);
    }

    public void delete(Long userId) {
        userRepository.delete(
            userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("해당 유저가 없습니다."))
        );
    }

    public int getChallenges(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));

        int month = LocalDateTime.now().getMonthValue();
        Page<Challenge> page = challengeRepository
                .findAll(PageRequest.of((month - 1) % 6, 5));

        List<Challenge> challengeList = page.getContent();
        
        Optional<List<ChallengeUser>> challengeUserTable = challengeUserRepository.findByUserAndChallengeIn(user, challengeList);

        if (challengeUserTable.isPresent()) {
            Result r = new Result();
            challengeUserTable.get().forEach(obj -> {
                r.result += obj.getCount();
            });
            return r.result;
        }
        return 0;
    }

    public class Result {
        public int result = 0;
    }
}
