package com.example.demo.repository;

import com.example.demo.domain.Member;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface MemberRepository extends Repository<Member, Long> {

    List<Member> findAll();

    Member findById(Long id);

    Member save(Member member);

    void deleteById(Long id);

}
