package com.example.demo.repository;

import com.example.demo.domain.Board;

import java.util.List;

public interface BoardRepository {

    List<Board> findAll();

    Board findById(Long id);

    Board insert(Board board);

    void deleteById(Long id);

    Board update(Board board);
}
