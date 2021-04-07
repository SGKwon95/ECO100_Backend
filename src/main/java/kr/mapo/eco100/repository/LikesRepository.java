package kr.mapo.eco100.repository;

import kr.mapo.eco100.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<Likes,Long> {

    //@Query("SELECT l FROM Likes l WHERE l.userId = :userId AND l.board_id = :boardId")
    //Optional<Likes> findByUserIdAndBoardId(@Param("userId") Long userId, @Param("boardId") Long boardId);
}
