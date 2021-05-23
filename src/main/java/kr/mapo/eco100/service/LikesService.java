package kr.mapo.eco100.service;

import kr.mapo.eco100.controller.v1.board.dto.IncreaseLikesRequest;
import kr.mapo.eco100.controller.v1.board.dto.ReadRequest;
import kr.mapo.eco100.entity.Board;
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

    public boolean save(IncreaseLikesRequest increaseLikesRequest) {
        Board board = boardRepository.findById(
            increaseLikesRequest.getBoardId())
            .orElseThrow(() -> new BoardNotFoundException("좋아요를 누른 게시물이 존재하지 않음"));
        
        Optional<Likes> likes = likesRepository.findByuserIdAndBoard(
            increaseLikesRequest.getUserId(),
            board
            );

        if (!likes.isPresent()) {
            likesRepository.save(
                Likes.builder()
                    .userId(increaseLikesRequest.getUserId())
                    .board(board)
                    .isCanceled(false).build()
            );
            return true;
        } else {
            if (likes.get().getIsCanceled()) {
                // 좋아요 취소한 걸 다시 누를 경우
                likes.map(obj -> {
                    obj.setIsCanceled(false);
                    return likesRepository.save(obj);
                });
                return true;
            } else {
                likes.map(obj -> {
                    obj.setIsCanceled(true);
                    return likesRepository.save(obj);
                });
                return false;
            }
        }
    }

    public boolean canClickLikes(ReadRequest readRequest) {
        Board board = boardRepository.findById(
            readRequest.getBoardId())
            .orElseThrow(() -> new BoardNotFoundException("좋아요를 누른 게시물이 존재하지 않음"));

        Optional<Likes> likes = likesRepository.findByuserIdAndBoard(readRequest.getUserId(),board);

        return likes.isPresent()? likes.get().getIsCanceled(): true;
    }
}
