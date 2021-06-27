package kr.mapo.eco100.controller.v1.board;

import kr.mapo.eco100.controller.v1.board.dto.BoardDto;
import kr.mapo.eco100.controller.v1.board.dto.BoardsResponse;
import kr.mapo.eco100.controller.v1.board.dto.CommentCreateRequest;
import kr.mapo.eco100.controller.v1.board.dto.CreateRequest;
import kr.mapo.eco100.controller.v1.board.dto.IncreaseLikesRequest;
import kr.mapo.eco100.controller.v1.board.dto.ReadRequest;
import kr.mapo.eco100.controller.v1.board.dto.UpdateRequest;
import kr.mapo.eco100.controller.v1.board.dto.UpdateRequestWithImage;
import kr.mapo.eco100.controller.v1.comment.dto.CommentDto;
import kr.mapo.eco100.service.BoardService;
import kr.mapo.eco100.service.CommentService;
import kr.mapo.eco100.service.LikesService;
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
    private final LikesService likesService;
    private final CommentService commentService;

    @ApiOperation(value = "사진 없는 글쓰기")
    @PostMapping("/board/create")
    public ResponseEntity<Void> create(@RequestBody @Valid CreateRequest createRequest) {
        boardService.create(createRequest);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "사진 있는 글쓰기")
    @PostMapping(value = "/board/create/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> createWithImage(MultipartHttpServletRequest request) throws IOException {

        @Valid
        CreateRequest createRequest = CreateRequest.builder()
                .userId(Long.parseLong(request.getParameter("userId")))
                .title(request.getParameter("title"))
                .contents(request.getParameter("contents"))
                .build();

        boardService.createWithImage(createRequest, request.getFile("image"));

        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "글 1개 읽기")
    @GetMapping("/board/read/{boardId}/{userId}")
    public ResponseEntity<BoardDto> read(@PathVariable(value = "boardId") Long boardId, @PathVariable(value = "userId") Long userId) {
        @Valid
        ReadRequest readRequest = new ReadRequest(boardId,userId);

        return ResponseEntity.ok(new BoardDto(boardService.read(readRequest.getBoardId()),
                likesService.canClickLikes(readRequest)));
    }

    @ApiOperation(value = "글 가져오기(5개)")
    @GetMapping("/board/{current}")
    public ResponseEntity<List<BoardsResponse>> getRecentBoards(@PathVariable(value = "current") int currentPage) {

        return ResponseEntity.ok(boardService.getRecentBoards(currentPage));
    }

    @ApiOperation(value = "글 좋아요 순으로 가져오기(5개)")
    @GetMapping("/board/likes/{current}")
    public ResponseEntity<List<BoardsResponse>> getBoardsSortedByLikes(@PathVariable(value = "current") int currentPage) {

        return ResponseEntity.ok(boardService.getBoardsSortedByLikes(currentPage));
    }

    @ApiOperation(value = "글 수정(사진 X)")
    @PutMapping("/board/update")
    public ResponseEntity<Void> update(@RequestBody @Valid UpdateRequest updateRequest) {
        boardService.updateWithoutImage(updateRequest);

        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "글 수정(사진 O)")
    @PutMapping(value = "/board/update/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateWithImage(MultipartHttpServletRequest request) throws IOException {
        @Valid
        UpdateRequestWithImage updateRequest = UpdateRequestWithImage.builder()
                .boardId(Long.parseLong(request.getParameter("userId")))
                .title(request.getParameter("title"))
                .contents(request.getParameter("contents"))
                .build();

        boardService.updateWithImage(updateRequest, request.getFile("image"));

        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "좋아요 처리")
    @PatchMapping("/board/likes")
    public ResponseEntity<Integer> increaseLikes(@RequestBody @Valid IncreaseLikesRequest increaseLikesRequest) {

        return ResponseEntity.ok(boardService.increaseLikes(increaseLikesRequest));
    }

    @ApiOperation(value = "글 삭제")
    @DeleteMapping("/board/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Long boardId) {
        boardService.delete(boardId);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "댓글 쓰기")
    @PostMapping("/board/comment/create")
    public ResponseEntity<Void> createComment(@RequestBody @Valid CommentCreateRequest commentCreateRequest) {

        commentService.create(commentCreateRequest);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "댓글 삭제")
    @DeleteMapping("/board/comment/delete/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable(value="commentId") Long commentId) {

        commentService.delete(commentId);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "게시판 ID로 댓글 불러오기")
    @GetMapping("/board/comment/{boardId}")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable(value = "boardId") Long boardId) {

        return ResponseEntity.ok(
            commentService.getAll(boardId)
        );
    }

    @ApiOperation(value = "내가 쓴 글 전부 조회하기")
    @GetMapping("/board/read/{userId}")
    public ResponseEntity<List<BoardsResponse>> myBoards(@PathVariable(value = "userId") Long userId) {

        return ResponseEntity.ok(boardService.myBoards(userId));
    }

    @ApiOperation(value = "검색어로 글 검색하기(제목, 내용 둘 다 포함)")
    @GetMapping("/board/search/{word}")
    public ResponseEntity<List<BoardsResponse>> searchForTitleAndContents(@PathVariable(value = "word") String word) {

        return ResponseEntity.ok(boardService.searchForTitleAndContents(word));
    }

    @ApiOperation(value = "내가 쓴 모든 댓글 불러오기")
    @GetMapping("/board/comment/all/{userId}")
    public ResponseEntity<List<CommentDto>> myComments(@PathVariable(value = "userId") Long userId) {

        return ResponseEntity.ok(commentService.myComments(userId));
    }
}
