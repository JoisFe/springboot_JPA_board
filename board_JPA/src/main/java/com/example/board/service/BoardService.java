package com.example.board.service;

import com.example.board.domain.board.Board;
import com.example.board.domain.board.BoardRepository;
import com.example.board.domain.Criteria;

import com.example.board.domain.user.User;
import com.example.board.dto.BoardDto;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public Long boardListCnt() {
        return boardRepository.count();
    }

    public List<BoardDto> boardList(Criteria cri) {
        PageRequest pageRequest = PageRequest.of(cri.getPage() - 1, cri.getPerPageNum());

        //List<Board> boards = boardRepository.findAll(pageRequest);
        List<Board> boards = boardRepository.findAllGreaterThan(pageRequest);
        List<BoardDto> boardDtoList = new ArrayList<>();

        for (Board board : boards) {
            BoardDto boardDto = BoardDto.builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .author(board.getAuthor())
                    .createdDate(board.getCreatedDate())
                    .modifiedDate(board.getModifiedDate())
                    .build();

            boardDtoList.add(boardDto);
        }

        return boardDtoList;
    }

    public BoardDto getBoard(Long id) {
        Optional<Board> boardWrapper = boardRepository.findById(id);
        Board board = boardWrapper.get();

        return BoardDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .author(board.getAuthor())
                .build();
    }

    @Transactional
    public Long uploadBoard(BoardDto boardDto) {
        return boardRepository.save(boardDto.toEntity()).getId();
    }

    @Transactional
    public Long updateBoard(BoardDto boardDto) {
        return boardRepository.save(boardDto.toEntity()).getId();
    }

    @Transactional
    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }

    public String validTest(BindingResult errors, UserService userService) {
        Map<String, String> validatorResult = userService.validateHandling(errors);
        for (String key : validatorResult.keySet()) {
            return validatorResult.get(key);
        }
        return ""; // 여기 올 수가 없는게 애초에 이 함수안에 들어왔다는 것은 for문 안에 하나는 걸린다는 것!!
    }

}
