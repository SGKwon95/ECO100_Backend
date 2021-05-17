package kr.mapo.eco100.repository.challenge;

import kr.mapo.eco100.entity.ChallengePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengePostRepository extends JpaRepository<ChallengePost, Long> {
}
