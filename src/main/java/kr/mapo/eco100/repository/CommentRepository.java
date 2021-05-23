package kr.mapo.eco100.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.mapo.eco100.entity.Board;
import kr.mapo.eco100.entity.Comment;
import kr.mapo.eco100.entity.User;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBoard(Board board);
    List<Comment> findByUser(User user);
}
