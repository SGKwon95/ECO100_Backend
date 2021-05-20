package kr.mapo.eco100.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kr.mapo.eco100.controller.v1.board.dto.CommentCreateRequest;
import kr.mapo.eco100.controller.v1.comment.dto.CommentDto;
import kr.mapo.eco100.entity.Board;
import kr.mapo.eco100.entity.Comment;
import kr.mapo.eco100.entity.User;
import kr.mapo.eco100.error.BoardNotFoundException;
import kr.mapo.eco100.error.UserNotFoundException;
import kr.mapo.eco100.repository.BoardRepository;
import kr.mapo.eco100.repository.CommentRepository;
import kr.mapo.eco100.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    
    public void create(CommentCreateRequest request) {
        Board board = boardRepository.findById(request.getBoardId())
                .orElseThrow(()->new BoardNotFoundException("해당 게시글이 존재하지 않습니다."));
        
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(()->new UserNotFoundException("해당 유저가 존재하지 않습니다."));

        commentRepository.save(
            Comment.builder()
                .board(board)
                .user(user)
                .contents(request.getContents())
                .build()
        );
    }

    public List<CommentDto> getAll(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(()->new BoardNotFoundException("해당 게시글이 존재하지 않습니다."));
        
        return commentRepository.findByBoard(board).stream().map(CommentDto::new).collect(Collectors.toList());
    }
}
