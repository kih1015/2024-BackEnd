package com.example.demo.repository;

import com.example.demo.domain.Article;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ArticleRepository extends Repository<Article, Long> {

    List<Article> findAll();

    List<Article> findAllByBoardId(Long boardId);

    List<Article> findAllByAuthorId(Long memberId);

    Article findById(Long id);

    Article save(Article article);

    void deleteById(Long id);

}
