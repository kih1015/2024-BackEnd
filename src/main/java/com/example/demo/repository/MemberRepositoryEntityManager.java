package com.example.demo.repository;

import com.example.demo.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberRepositoryEntityManager implements MemberRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Member> findAll() {
        return entityManager.createQuery("SELECT m FROM Member m", Member.class).getResultList();
    }

    @Override
    public Member findById(Long id) {
        return entityManager.find(Member.class, id);
    }

    @Override
    public Member insert(Member member) {
        entityManager.persist(member);
        return findById(member.getId());
    }

    @Override
    public Member update(Member member) {
        return entityManager.merge(member);
    }

    @Override
    public void deleteById(Long id) {
        entityManager.remove(findById(id));
    }

}
