package kr.mapo.eco100.repository.challenge;

import java.util.List;
import java.util.Optional;

import kr.mapo.eco100.entity.Challenge;
import kr.mapo.eco100.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.mapo.eco100.entity.ChallengeUser;

@Repository
public interface ChallengeUserRespoitory extends JpaRepository<ChallengeUser, Long> {
    Optional<List<ChallengeUser>> findByUserAndChallengeIn(User user, List<Challenge> challenges);
    Optional<ChallengeUser> findByUserAndChallenge(User user, Challenge challenge);
}
