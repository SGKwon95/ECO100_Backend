package kr.mapo.eco100.service;

import kr.mapo.eco100.entity.Likes;
import kr.mapo.eco100.error.BoardNotFoundException;
import kr.mapo.eco100.repository.BoardRepository;
import kr.mapo.eco100.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikesService {

    private final BoardRepository boardRepository;
    private final LikesRepository likesRepository;

    public boolean save(Long userId,Long boardId) {
        return true;
        /*Optional<Likes> likes =likesRepository.findByUserIdAndBoardId(userId,boardId);
        if (!likes.isPresent()){
            likesRepository.save(
                    Likes.builder()
                            .userId(userId)
                            .board(boardRepository.findById(boardId)
                                    .orElseThrow(()->new BoardNotFoundException("좋아요를 누른 게시물이 존재하지 않음")))
                            .build()
            );
            return true;
        } else {
            likesRepository.delete(likes.get());
            return false;
        }

         */
    }
}
