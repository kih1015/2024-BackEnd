package com.example.demo.controller;

import com.example.demo.controller.dto.request.BoardCreateRequest;
import com.example.demo.controller.dto.request.BoardUpdateRequest;
import com.example.demo.controller.dto.response.BoardResponse;
import com.example.demo.exception.RestApiException;
import com.example.demo.exception.error.BoardErrorCode;
import com.example.demo.exception.error.CommonErrorCode;
import com.example.demo.service.ArticleService;
import com.example.demo.service.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/boards")
public class BoardController {

    private final BoardService boardService;
    private final ArticleService articleService;

    public BoardController(BoardService boardService, ArticleService articleService) {
        this.boardService = boardService;
        this.articleService = articleService;
    }

    @GetMapping()
    public List<BoardResponse> getBoards() {
        return boardService.getBoards();
    }

    @GetMapping("/{id}")
    public BoardResponse getBoard(
        @PathVariable Long id
    ) {
        if (boardService.getBoards().stream().noneMatch(res -> res.id().equals(id))) {
            throw new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND);
        }
        return boardService.getBoardById(id);
    }

    @PostMapping()
    public BoardResponse createBoard(
        @RequestBody BoardCreateRequest request
    ) {
        if (request.name() == null) {
            throw new RestApiException(CommonErrorCode.NULL_PARAMETER);
        }
        return boardService.createBoard(request);
    }

    @PutMapping("/{id}")
    public BoardResponse updateBoard(
        @PathVariable Long id,
        @RequestBody BoardUpdateRequest updateRequest
    ) {
        return boardService.update(id, updateRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(
        @PathVariable Long id
    ) {
        if (articleService.getArticles()
                .stream()
                .anyMatch(res -> res.boardId().equals(id))) {
            throw new RestApiException(BoardErrorCode.ARTICLE_EXISTENCE);
        }
        boardService.deleteBoard(id);
        return ResponseEntity.noContent().build();
    }
}
