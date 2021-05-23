package kr.mapo.eco100.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.mapo.eco100.controller.v1.board.dto.BoardsResponse;
import kr.mapo.eco100.controller.v1.board.dto.CreateRequest;
import kr.mapo.eco100.controller.v1.board.dto.IncreaseLikesRequest;
import kr.mapo.eco100.controller.v1.board.dto.UpdateRequest;
import kr.mapo.eco100.controller.v1.board.dto.UpdateRequestWithImage;
import kr.mapo.eco100.entity.Board;
import kr.mapo.eco100.entity.User;
import kr.mapo.eco100.error.BoardNotFoundException;
import kr.mapo.eco100.error.UserNotFoundException;
import kr.mapo.eco100.repository.BoardRepository;
import kr.mapo.eco100.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final LikesService likesService;

    public Board create(CreateRequest createRequest) {

        User user = userRepository.findById(createRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException("해당 유저가 존재하지 않습니다"));

        return boardRepository.save(Board.builder().title(createRequest.getTitle())
                .contents(createRequest.getContents()).imageUrl(createRequest.getImageUrl()).user(user).likes(0).build());
    }

    public Board createWithImage(CreateRequest createRequest, MultipartFile file) throws IOException {

        User user = userRepository.findById(createRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException("해당 유저가 존재하지 않습니다"));

        String filename = saveImageFile(file);
        return boardRepository
                .save(Board.builder().title(createRequest.getTitle()).contents(createRequest.getContents())
                        .imageUrl("http://rpinas.iptime.org:10122/image/board/" + filename).user(user).likes(0).build());
    }

    public Board read(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(() -> new BoardNotFoundException("조회한 아이디의 게시글이 없습니다"));
    }

    public List<BoardsResponse> getRecentBoards(int currentPage) {
        //PageRequest.of(불러올 페이지, 불러올 페이지 개수, 정렬 방법, 정렬 기준)
        Page<Board> page = boardRepository
                .findAll(PageRequest.of(currentPage, 5, Sort.Direction.DESC, "boardId"));
        return page.getContent().stream().map(BoardsResponse::new).collect(Collectors.toList());
    }

    public List<BoardsResponse> getBoardsSortedByLikes(int currentPage) {
        Page<Board> page = boardRepository
                .findAll(PageRequest.of(currentPage, 5, Sort.Direction.DESC, "likes"));
        return page.getContent().stream().map(BoardsResponse::new).collect(Collectors.toList());
    }

    public void updateWithoutImage(UpdateRequest updateRequest) {
        boardRepository.findById(updateRequest.getBoardId()).map(board -> {
            board.setTitle(updateRequest.getTitle());
            board.setContents(updateRequest.getContents());
            if(updateRequest.getIsDeletedImage()) {//원래 글이 사진이 있을 경우
                deleteImageFile(board.getImageUrl());
                board.setImageUrl(null);
            }
            return boardRepository.save(board);
        }).orElseThrow(() -> new BoardNotFoundException("조회한 게시글 아이디의 게시글이 없습니다"));
    }

    public void updateWithImage(UpdateRequestWithImage updateRequest, MultipartFile file) throws IOException {
        String newImageFileName = saveImageFile(file);
        boardRepository.findById(updateRequest.getBoardId()).map(board -> {
            board.setTitle(updateRequest.getTitle());
            board.setContents(updateRequest.getContents());
            deleteImageFile(board.getImageUrl());
            board.setImageUrl("http://rpinas.iptime.org:10122/image/board/" + newImageFileName);
            return boardRepository.save(board);
        }).orElseThrow(() -> new BoardNotFoundException("조회한 게시글 아이디의 게시글이 없습니다"));
    }

    public void delete(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new BoardNotFoundException("삭제할 아이디의 게시글이 없습니다"));
        if(board.getImageUrl() != null) {
            deleteImageFile(board.getImageUrl());
        }

        boardRepository.delete(board);
    }

    public Integer increaseLikes(IncreaseLikesRequest increaseLikesRequest) {
        boolean click = likesService.save(increaseLikesRequest);
        
        boardRepository.findById(increaseLikesRequest.getBoardId()).map(board -> {
            board.setLikes(board.getLikes() + (click ? 1 : -1));
            return boardRepository.save(board);
        }).orElseThrow(() -> new BoardNotFoundException("조회한 아이디의 게시글이 없습니다"));
        
        return boardRepository.findById(increaseLikesRequest.getBoardId()).map(board -> board.getLikes()).orElse(0);
    }

    public List<BoardsResponse> myBoards(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("해당 유저가 존재하지 않습니다"));

        return boardRepository.findByUser(user).stream().map(BoardsResponse::new).collect(Collectors.toList());
    }

    public List<BoardsResponse> searchForTitleAndContents(String word) {
        return boardRepository.findByTitleContainsOrContentsContains(word,word).stream().map(BoardsResponse::new).collect(Collectors.toList());
    }

    public void deleteImageFile(String imageUrl) {
        String fileLocation = "./images/board/"; // 저장할 경로를 지정한다.
        String filename = imageUrl.substring(imageUrl.lastIndexOf("/")+1);
        File file = new File(fileLocation+filename);
        if(file.exists()) {
            if(file.delete()) {
                log.info(filename+" 파일을 삭제하였습니다.");
            } else {
                log.info(filename+" 파일을 삭제하는데 실패하였습니다.");
            }
        } else {
            log.info(filename+" 파일이 존재하지 않습니다.");
        }
    }

    public String saveImageFile(MultipartFile file) throws IOException {
        String fileLocation = "./images/board/"; // 저장할 경로를 지정한다.
        FileOutputStream fos = null;
        String filename = "";
        try {

            File dir = new File(fileLocation);
            dir.mkdirs();// 저장할 경로에 지정된 폴더가 없으면 생성한다. 있으면 아무일도 일어나지 않는다.

            int start = file.getOriginalFilename().lastIndexOf('.');
            String extension = file.getOriginalFilename().substring(start);// 파일 이름의 확장자만 추출한다.
            filename = UUID.randomUUID().toString() + extension;

            File imgfile = new File(fileLocation + filename);
            fos = new FileOutputStream(imgfile);// fileoutputstream은 new로 만들 시 파일을 생성한다.
            fos.write(file.getBytes());// 전송된 이미지를 파일에 저장한다.

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fos.close();
        }
        return filename;
    }
}
