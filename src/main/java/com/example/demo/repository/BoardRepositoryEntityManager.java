package com.example.demo.repository;

import com.example.demo.domain.Board;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardRepositoryEntityManager implements BoardRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Board> findAll() {
        return entityManager.createQuery("SELECT b FROM Board b", Board.class).getResultList();
    }

    @Override
    public Board findById(Long id) {
        return entityManager.find(Board.class, id);
    }

    @Override
    public Board insert(Board board) {
        entityManager.persist(board);
        return findById(board.getId());
    }

    @Override
    public void deleteById(Long id) {
        entityManager.remove(findById(id));
    }

    @Override
    public Board update(Board board) {
        Board forUpdate = findById(board.getId());
        forUpdate.update(board.getName());
        return findById(board.getId());
    }

}
