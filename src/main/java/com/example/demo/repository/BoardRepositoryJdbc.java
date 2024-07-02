package com.example.demo.repository;

import com.example.demo.domain.Board;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

public class BoardRepositoryJdbc {

    private final JdbcTemplate jdbcTemplate;

    public BoardRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Board> boardRowMapper = (rs, rowNum) -> new Board(
        rs.getLong("id"),
        rs.getString("name")
    );

    public List<Board> findAll() {
        return jdbcTemplate.query("""
            SELECT id, name
            FROM board
            """, boardRowMapper);
    }

    public Board findById(Long id) {
        return jdbcTemplate.queryForObject("""
            SELECT id, name
            FROM board
            WHERE id = ?
            """, boardRowMapper, id);
    }

    public Board insert(Board board) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement("""
                INSERT INTO board (name) VALUES (?)
                """, new String[]{"id"});
            ps.setString(1, board.getName());
            return ps;
        }, keyHolder);
        return findById(keyHolder.getKey().longValue());
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("""
            DELETE FROM board WHERE id = ?
            """, id);
    }

    public Board update(Board board) {
        return jdbcTemplate.queryForObject("""
            UPDATE board SET name = ? WHERE id = ?
            """, boardRowMapper, board.getName(), board.getId()
        );
    }

}
