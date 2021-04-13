package kr.mapo.eco100.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import kr.mapo.eco100.controller.v1.board.dto.BoardDto;
import kr.mapo.eco100.controller.v1.board.dto.CreateRequest;
import kr.mapo.eco100.controller.v1.board.dto.IncreaseLikesRequest;
import kr.mapo.eco100.controller.v1.board.dto.UpdateRequest;
import kr.mapo.eco100.entity.Board;
import kr.mapo.eco100.entity.User;
import kr.mapo.eco100.error.BoardNotFoundException;
import kr.mapo.eco100.error.UserNotFoundException;
import kr.mapo.eco100.repository.BoardRepository;
import kr.mapo.eco100.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final LikesService likesService;

    public Board create(CreateRequest createRequest) {
        
        User user = userRepository.findById(createRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException("해당 유저가 존재하지 않습니다"));

        return boardRepository.save(
                Board.builder()
                        .title(createRequest.getTitle())
                        .contents(createRequest.getContents())
                        .imageUrl(createRequest.getImageUrl())
                        .user(user)
                        .build()
        );
    }

    public Board read(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("조회한 아이디의 게시글이 없습니다"));
    }

    public List<BoardDto> getRecentBoards(int currentPage) {
        Page<Board> page = boardRepository.findAll(PageRequest.of(currentPage, currentPage + 5, Sort.Direction.DESC, "created_at"));
        return page.getContent().stream()
                .map(BoardDto::new)
                .collect(Collectors.toList());
    }

    public void update(UpdateRequest updateRequest) {
        boardRepository.findById(updateRequest.getBoardId())
                .map(board -> {
                    board.setTitle(updateRequest.getTitle());
                    board.setContents(updateRequest.getContents());
                    board.setImageUrl(updateRequest.getImageUrl());
                    return boardRepository.save(board);
                })
                .orElseThrow(() -> new BoardNotFoundException("조회한 게시글 아이디의 게시글이 없습니다"));
    }

    public void delete(Long boardId) {
        boardRepository.delete(
                boardRepository.findById(boardId)
                        .orElseThrow(() -> new BoardNotFoundException("삭제할 아이디의 게시글이 없습니다"))
        );
    }

    public Boolean increaseLikes(IncreaseLikesRequest increaseLikesRequest) {
        boolean result = likesService.save(increaseLikesRequest);
        boardRepository.findById(increaseLikesRequest.getBoardId())
                .map(board -> {
                    board.setLikes(board.getLikes() + (result ? 1 : -1));
                    return boardRepository.save(board);
                })
                .orElseThrow(() -> new BoardNotFoundException("조회한 아이디의 게시글이 없습니다"));
        return result;
    }
}
