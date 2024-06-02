package com.example.demo.repository;

import com.example.demo.domain.Article;

import java.util.List;

public interface ArticleRepository {

    List<Article> findAll();

    List<Article> findAllByBoardId(Long boardId);

    List<Article> findAllByMemberId(Long memberId);

    Article findById(Long id);

    Article insert(Article article);

    Article update(Article article);

    void deleteById(Long id);
}
