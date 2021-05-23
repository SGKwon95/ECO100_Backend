package kr.mapo.eco100.repository;

import kr.mapo.eco100.entity.Board;
import kr.mapo.eco100.entity.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByUser(User user);
    List<Board> findByTitleContains(String word);
    List<Board> findByContentsContains(String word);
    List<Board> findByTitleContainsOrContentsContains(String title, String contents);
}
