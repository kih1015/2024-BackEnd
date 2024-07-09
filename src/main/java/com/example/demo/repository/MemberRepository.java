package com.example.demo.repository;

import com.example.demo.domain.Member;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends Repository<Member, Long> {

    List<Member> findAll();

    Member findById(Long id);

    Optional<Member> findByEmail(String email);

    Member save(Member member);

    void deleteById(Long id);

}
