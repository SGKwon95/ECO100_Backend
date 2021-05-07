package kr.mapo.eco100.controller.v1.board;

import kr.mapo.eco100.controller.v1.board.dto.BoardDto;
import kr.mapo.eco100.controller.v1.board.dto.CreateRequest;
import kr.mapo.eco100.controller.v1.board.dto.IncreaseLikesRequest;
import kr.mapo.eco100.controller.v1.board.dto.UpdateRequest;
import kr.mapo.eco100.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/board/create")
    public ResponseEntity<BoardDto> create(@RequestBody @Valid CreateRequest createRequest) {

        return ResponseEntity.ok(new BoardDto(boardService.create(createRequest)));
    }

    @PostMapping(value = "/board/create/image", consumes = { "multipart/form-data" })
    public ResponseEntity<BoardDto> createWithImage(MultipartHttpServletRequest request) throws IOException {
        
        @Valid
        CreateRequest createRequest = CreateRequest.builder()
                .userId(Long.parseLong(request.getAttribute("userId").toString()))
                .title(request.getAttribute("title").toString()).contents(request.getAttribute("contents").toString())
                .build();

        return ResponseEntity.ok(new BoardDto(boardService.createWithImage(createRequest, request.getFile("image"))));
    }

    @GetMapping("/board/read/{id}")
    public ResponseEntity<BoardDto> read(@PathVariable(value = "id") Long boardId) {

        return ResponseEntity.ok(new BoardDto(boardService.read(boardId)));
    }

    @GetMapping("/board/{current}")
    public ResponseEntity<List<BoardDto>> getRecentBoards(@PathVariable(value = "current") int currentPage) {

        return ResponseEntity.ok(boardService.getRecentBoards(currentPage));
    }

    @PutMapping("/board/update")
    public ResponseEntity<Void> update(@RequestBody @Valid UpdateRequest updateRequest) {
        boardService.update(updateRequest);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/board/likes")
    public ResponseEntity<Boolean> increaseLikes(@RequestBody @Valid IncreaseLikesRequest increaseLikesRequest) {

        return ResponseEntity.ok(boardService.increaseLikes(increaseLikesRequest));
    }

    @DeleteMapping("/board/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Long boardId) {
        boardService.delete(boardId);
        return ResponseEntity.noContent().build();
    }

}
