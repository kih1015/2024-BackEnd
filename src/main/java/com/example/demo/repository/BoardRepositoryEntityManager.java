package com.example.demo.repository;

import com.example.demo.domain.Board;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardRepositoryEntityManager {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Board> findAll() {
        return entityManager.createQuery("SELECT b FROM Board b", Board.class).getResultList();
    }

    public Board findById(Long id) {
        return entityManager.find(Board.class, id);
    }

    public Board insert(Board board) {
        entityManager.persist(board);
        return findById(board.getId());
    }

    public void deleteById(Long id) {
        entityManager.remove(findById(id));
    }

    public Board update(Board board) {
        return entityManager.merge(board);
    }

}
