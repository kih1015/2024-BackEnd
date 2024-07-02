package com.example.demo.repository;

import com.example.demo.domain.Board;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface BoardRepository extends Repository<Board, Long> {

    List<Board> findAll();

    Board findById(Long id);

    Board save(Board board);

    void deleteById(Long id);

}
