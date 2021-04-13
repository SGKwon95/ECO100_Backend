package kr.mapo.eco100.repository;

import kr.mapo.eco100.entity.Board;
import kr.mapo.eco100.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<Likes,Long> {

    Optional<Likes> findByuserIdAndBoard(Long userId, Board board);
    
}
