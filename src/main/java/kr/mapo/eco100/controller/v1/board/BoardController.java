package kr.mapo.eco100.controller.v1.board;

import kr.mapo.eco100.controller.v1.board.dto.BoardDto;
import kr.mapo.eco100.entity.Board;
import kr.mapo.eco100.entity.Likes;
import kr.mapo.eco100.entity.User;
import kr.mapo.eco100.error.BoardNotFoundException;
import kr.mapo.eco100.error.UserNotFoundException;
import kr.mapo.eco100.repository.BoardRepository;
import kr.mapo.eco100.repository.LikesRepository;
import kr.mapo.eco100.repository.UserRepository;
import kr.mapo.eco100.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController("/board")
public class BoardController {

    private final BoardRepository boardRepository;
    private final LikesService likesService;
    private final UserRepository userRepository;

    @PostMapping("/create/{id}")
    public Board create(@RequestBody BoardDto boardDto, @PathVariable(value = "id") Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("해당 유저가 존재하지 않습니다"));

        return boardRepository.save(
                Board.builder()
                        .title(boardDto.getTitle())
                        .contents(boardDto.getContents())
                        .imageUrl(boardDto.getImageUrl())
                        .user(user)
                        .build()
        );
    }

    @GetMapping("/read/{id}")
    public Board read(@PathVariable Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException("조회한 아이디의 게시글이 없습니다"));
    }

    @GetMapping("/{current}")
    public Page<Board> getRecentBoards(@PathVariable(value = "current") int currentPage) {
        return boardRepository.findAll(PageRequest.of(currentPage, currentPage + 5, Sort.Direction.DESC, "created_at"));
    }

    @PutMapping("/update/{id}")
    public Board update(@PathVariable Long id, @RequestBody BoardDto boardDto) {
        return boardRepository.findById(id)
                .map(board -> {
                    board.setTitle(boardDto.getTitle());
                    board.setContents(boardDto.getTitle());
                    board.setImageUrl(boardDto.getImageUrl());
                    return boardRepository.save(board);
                })
                .orElseThrow(() -> new BoardNotFoundException("조회한 아이디의 게시글이 없습니다"));
    }

    @PatchMapping("/likes/{boardId}/{who}")
    public ResponseEntity<Boolean> increaseLikes(@PathVariable(value = "who") Long userId, @PathVariable Long boardId) {
        Boolean result = likesService.save(userId, boardId);
        boardRepository.findById(boardId)
                .map(board -> {
                    board.setLikes(board.getLikes() + (result ? 1 : -1));
                    return boardRepository.save(board);
                })
                .orElseThrow(() -> new BoardNotFoundException("조회한 아이디의 게시글이 없습니다"));

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boardRepository.delete(
                boardRepository.findById(id)
                        .orElseThrow(() -> new BoardNotFoundException("삭제할 아이디의 게시글이 없습니다"))
        );
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler
    public ResponseEntity<String> BoardNotFoundExceptionHandler(BoardNotFoundException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.OK);
    }
}
