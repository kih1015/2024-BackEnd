package com.example.demo.repository;

import com.example.demo.domain.Article;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArticleRepositoryEntityManager {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Article> findAll() {
        return entityManager.createQuery("SELECT a FROM Article a", Article.class).getResultList();
    }

    public List<Article> findAllByBoardId(Long boardId) {
        String jpql = "SELECT a FROM Article a WHERE a.boardId = :boardId";
        TypedQuery<Article> query = entityManager.createQuery(jpql, Article.class);
        query.setParameter("boardId", boardId);
        return query.getResultList();
    }

    public List<Article> findAllByMemberId(Long memberId) {
        String jpql = "SELECT a FROM Article a WHERE a.authorId = :authorId";
        TypedQuery<Article> query = entityManager.createQuery(jpql, Article.class);
        query.setParameter("authorId", memberId);
        return query.getResultList();
    }

    public Article findById(Long id) {
        return entityManager.find(Article.class, id);
    }

    public Article insert(Article article) {
        entityManager.persist(article);
        return findById(article.getId());
    }

    public Article update(Article article) {
        return entityManager.merge(article);
    }

    public void deleteById(Long id) {
        entityManager.remove(findById(id));
    }

}
