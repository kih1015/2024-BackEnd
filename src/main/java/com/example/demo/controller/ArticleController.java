package com.example.demo.controller;

import com.example.demo.controller.dto.request.ArticleCreateRequest;
import com.example.demo.controller.dto.request.ArticleUpdateRequest;
import com.example.demo.controller.dto.response.ArticleResponse;
import com.example.demo.exception.RestApiException;
import com.example.demo.exception.error.ArticleErrorCode;
import com.example.demo.exception.error.CommonErrorCode;
import com.example.demo.service.ArticleService;
import com.example.demo.service.BoardService;
import com.example.demo.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class ArticleController {

    private final ArticleService articleService;
    private final MemberService memberService;
    private final BoardService boardService;

    public ArticleController(ArticleService articleService, MemberService memberService, BoardService boardService) {
        this.articleService = articleService;
        this.memberService = memberService;
        this.boardService = boardService;
    }

    @GetMapping("/articles")
    public ResponseEntity<List<ArticleResponse>> getArticles(
            @RequestParam Long boardId
    ) {
        List<ArticleResponse> response = articleService.getByBoardId(boardId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/articles/{id}")
    public ResponseEntity<ArticleResponse> getArticle(
            @PathVariable Long id
    ) {
        if (articleService.getArticles()
                .stream()
                .noneMatch(res -> res.id().equals(id))) {
            throw new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND);
        }
        ArticleResponse response = articleService.getById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/articles")
    public ResponseEntity<ArticleResponse> crateArticle(
            @RequestBody ArticleCreateRequest request
    ) {
        boolean isNullExistence = request.boardId() == null
                || request.authorId() == null
                || request.title() == null
                || request.description() == null;
        if (isNullExistence) {
            throw new RestApiException(CommonErrorCode.NULL_PARAMETER);
        }
        if (boardService.getBoards()
                .stream()
                .noneMatch(res -> res.id().equals(request.boardId()))
                || memberService.getAll()
                .stream()
                .noneMatch(res -> res.id().equals(request.authorId()))) {
            throw new RestApiException(ArticleErrorCode.REFERENCE_ERROR);
        }
        ArticleResponse response = articleService.create(request);
        return ResponseEntity.created(URI.create("/articles/" + response.id())).body(response);
    }

    @PutMapping("/articles/{id}")
    public ResponseEntity<ArticleResponse> updateArticle(
            @PathVariable Long id,
            @RequestBody ArticleUpdateRequest request
    ) {
        if (boardService.getBoards()
                .stream()
                .noneMatch(res -> res.id().equals(request.boardId()))) {
            throw new RestApiException(ArticleErrorCode.REFERENCE_ERROR);
        }
        ArticleResponse response = articleService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/articles/{id}")
    public ResponseEntity<Void> updateArticle(
            @PathVariable Long id
    ) {
        articleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
