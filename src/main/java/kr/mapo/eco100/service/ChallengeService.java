package kr.mapo.eco100.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final ChallengeUserRespoitory challengeUserRepository;
    private final UserRepository userRepository;

    public List<ChallengeOfTheMonthDTO> getChallenges(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("해당 유저를 찾을 수 없습니다."));

        int month = LocalDateTime.now().getMonthValue();
        Page<Challenge> page = challengeRepository
                .findAll(PageRequest.of(((month - 1) % 6) * 5, 5));
        
        List<Challenge> challengeList = page.getContent();
        List<ChallengeOfTheMonthDTO> result = new ArrayList<ChallengeOfTheMonthDTO>();
        Optional<List<ChallengeUser>> challengeUserTable = challengeUserRepository.findByuserIdAndchallengeIdIn(userId, challengeList.stream().map(obj->obj.getChallengeId()).collect(Collectors.toList()));
        
        if(challengeUserTable.isPresent()) {
            boolean[] isPicked = new boolean[30];
            
            challengeUserTable.get().forEach(obj -> {
                isPicked[obj.getChallenge().getChallengeId().intValue()] = true;
                result.add(new ChallengeOfTheMonthDTO(obj.getChallenge(),obj.getCount()));
            });
            
            challengeList.forEach(obj -> {
                if(!isPicked[obj.getChallengeId().intValue()]) {
                    result.add(new ChallengeOfTheMonthDTO(obj,0));
                }
            });
            Collections.sort(result, (c1, c2) -> c1.getChallengeId().compareTo(c2.getChallengeId()));
        } else {
            challengeList.forEach(obj -> {
                result.add(new ChallengeOfTheMonthDTO(obj,0));
            });
        }
        return result;
    }
}
