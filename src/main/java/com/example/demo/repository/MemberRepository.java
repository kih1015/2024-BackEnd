package com.example.demo.repository;

import com.example.demo.domain.Member;

import java.util.List;

public interface MemberRepository {

    List<Member> findAll();

    Member findById(Long id);

    Member insert(Member member);

    Member update(Member member);

    void deleteById(Long id);
}
