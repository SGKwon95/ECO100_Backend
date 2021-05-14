package kr.mapo.eco100.controller.v1.board;

import kr.mapo.eco100.controller.v1.board.dto.BoardDto;
import kr.mapo.eco100.controller.v1.board.dto.BoardsResponse;
import kr.mapo.eco100.controller.v1.board.dto.CreateRequest;
import kr.mapo.eco100.controller.v1.board.dto.IncreaseLikesRequest;
import kr.mapo.eco100.controller.v1.board.dto.UpdateRequest;
import kr.mapo.eco100.service.BoardService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

@Api(value = "참여게시판 정보")
@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;

    @ApiOperation(value = "사진 없는 글쓰기")
    @PostMapping("/board/create")
    public ResponseEntity<BoardsResponse> create(@RequestBody @Valid CreateRequest createRequest) {

        return ResponseEntity.ok(new BoardsResponse(boardService.create(createRequest)));
    }

    @ApiOperation(value = "사진 있는 글쓰기")
    @PostMapping(value = "/board/create/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BoardsResponse> createWithImage(MultipartHttpServletRequest request) throws IOException {
        
        @Valid
        CreateRequest createRequest = CreateRequest.builder()
                .userId(Long.parseLong(request.getParameter("userId")))
                .title(request.getParameter("title").toString())
                .contents(request.getParameter("contents").toString())
                .build();

        return ResponseEntity.ok(new BoardsResponse(boardService.createWithImage(createRequest, request.getFile("image"))));
    }

    @ApiOperation(value = "글 읽기")
    @GetMapping("/board/read/{id}")
    public ResponseEntity<BoardDto> read(@PathVariable(value = "id") Long boardId) {

        return ResponseEntity.ok(new BoardDto(boardService.read(boardId)));
    }

    @ApiOperation(value = "글 가져오기(5개)")
    @GetMapping("/board/{current}")
    public ResponseEntity<List<BoardsResponse>> getRecentBoards(@PathVariable(value = "current") int currentPage) {

        return ResponseEntity.ok(boardService.getRecentBoards(currentPage));
    }

    @ApiOperation(value = "글 수정")
    @PutMapping("/board/update")
    public ResponseEntity<Void> update(@RequestBody @Valid UpdateRequest updateRequest) {
        boardService.update(updateRequest);

        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "좋아요 처리")
    @PatchMapping("/board/likes")
    public ResponseEntity<Boolean> increaseLikes(@RequestBody @Valid IncreaseLikesRequest increaseLikesRequest) {

        return ResponseEntity.ok(boardService.increaseLikes(increaseLikesRequest));
    }

    @ApiOperation(value = "글 삭제")
    @DeleteMapping("/board/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Long boardId) {
        boardService.delete(boardId);
        return ResponseEntity.noContent().build();
    }

}
