package com.example.demo.repository;

import com.example.demo.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberRepositoryEntityManager {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Member> findAll() {
        return entityManager.createQuery("SELECT m FROM Member m", Member.class).getResultList();
    }

    public Member findById(Long id) {
        return entityManager.find(Member.class, id);
    }

    public Member insert(Member member) {
        entityManager.persist(member);
        return findById(member.getId());
    }

    public Member update(Member member) {
        return entityManager.merge(member);
    }

    public void deleteById(Long id) {
        entityManager.remove(findById(id));
    }

}
