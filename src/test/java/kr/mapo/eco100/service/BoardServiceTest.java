package kr.mapo.eco100.service;


import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;

import kr.mapo.eco100.entity.Board;
import kr.mapo.eco100.repository.BoardRepository;

@DataJpaTest//JPA 기능만 테스트 하고 싶을 경우 사용한다. 실제 DB에서 검색하며 서버가 꺼지지 않는다.
public class BoardServiceTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    void findByTitleContains() {
        List<Board> result = boardRepository.findByTitleContains("첫번째");

        then(!result.isEmpty());

        result.forEach(System.out::println);
    }

    @Test
    void findByContentsContains() {
        List<Board> result = boardRepository.findByContentsContains("내용");

        then(!result.isEmpty());

        result.forEach(System.out::println);
    }

    @Test
    void findByTitleContainsOrContentsContains() {
        List<Board> result = boardRepository.findByTitleContainsOrContentsContains("내용","내용");

        result.forEach(System.out::println);
    }
}
