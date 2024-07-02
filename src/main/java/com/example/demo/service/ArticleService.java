package com.example.demo.service;

import com.example.demo.controller.dto.request.ArticleCreateRequest;
import com.example.demo.controller.dto.request.ArticleUpdateRequest;
import com.example.demo.controller.dto.response.ArticleResponse;
import com.example.demo.domain.Article;
import com.example.demo.domain.Board;
import com.example.demo.domain.Member;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public ArticleService(
        ArticleRepository articleRepository,
        MemberRepository memberRepository,
        BoardRepository boardRepository
    ) {
        this.articleRepository = articleRepository;
        this.memberRepository = memberRepository;
        this.boardRepository = boardRepository;
    }

    public List<ArticleResponse> getArticles() {
        return articleRepository.findAll().stream()
                .map(ArticleResponse::from)
                .toList();
    }

    public ArticleResponse getById(Long id) {
        Article article = articleRepository.findById(id);
        Member member = memberRepository.findById(article.getAuthor().getId());
        Board board = boardRepository.findById(article.getBoard().getId());
        return ArticleResponse.of(article, member, board);
    }

    public List<ArticleResponse> getByBoardId(Long boardId) {
        List<Article> articles = articleRepository.findAllByBoardId(boardId);
        return articles.stream()
            .map(article -> {
                Member member = memberRepository.findById(article.getAuthor().getId());
                Board board = boardRepository.findById(article.getBoard().getId());
                return ArticleResponse.of(article, member, board);
            })
            .toList();
    }

    @Transactional
    public ArticleResponse create(ArticleCreateRequest request) {
        Article article = new Article(
            memberRepository.findById(request.authorId()),
            boardRepository.findById(request.boardId()),
            request.title(),
            request.description()
        );
        Article saved = articleRepository.save(article);
        Member member = saved.getAuthor();
        Board board = saved.getBoard();
        return ArticleResponse.of(saved, member, board);
    }

    @Transactional
    public ArticleResponse update(Long id, ArticleUpdateRequest request) {
        Article article = articleRepository.findById(id);
        article.update(boardRepository.findById(request.boardId()), request.title(), request.description());
        Member member = article.getAuthor();
        Board board = article.getBoard();
        return ArticleResponse.of(article, member, board);
    }

    @Transactional
    public void delete(Long id) {
        articleRepository.deleteById(id);
    }

}
