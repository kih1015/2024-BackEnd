package com.example.demo.repository;

import com.example.demo.domain.Board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class BoardRepositoryMemory implements BoardRepository {

    private static final Map<Long, Board> boards = new HashMap<>();
    private static final AtomicLong autoincrement = new AtomicLong(1);

    static {
        // 1번 게시판을 미리 만들어둔다.
        boards.put(autoincrement.getAndIncrement(), new Board("자유게시판"));
    }

    @Override
    public List<Board> findAll() {
        return boards.entrySet().stream()
            .map(it -> {
                Board board = it.getValue();
                board.setId(it.getKey());
                return board;
            }).toList();
    }

    @Override
    public Board findById(Long id) {
        return boards.getOrDefault(id, null);
    }

    @Override
    public Board insert(Board board) {
        boards.put(autoincrement.getAndIncrement(), board);
        return board;
    }

    @Override
    public void deleteById(Long id) {
        boards.remove(id);
    }

    @Override
    public Board update(Board board) {
        return boards.put(board.getId(), board);
    }
}
