package com.example.demo.repository;

import com.example.demo.domain.Member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class MemberRepositoryMemory {

    private static final Map<Long, Member> members = new HashMap<>();
    private static final AtomicLong autoincrement = new AtomicLong(1);

    static {
        // 1번 유저를 미리 만들어둔다.
        members.put(autoincrement.getAndIncrement(), new Member("최준호", "temp@gmail.com", "password"));
    }

    public List<Member> findAll() {
        return members.entrySet().stream()
            .map(it -> {
                Member member = it.getValue();
                member.setId(it.getKey());
                return member;
            }).toList();
    }

    public Member findById(Long id) {
        return members.getOrDefault(id, null);
    }

    public Member insert(Member member) {
        long id = autoincrement.getAndIncrement();
        members.put(id, member);
        member.setId(id);
        return member;
    }

    public Member update(Member member) {
        return members.put(member.getId(), member);
    }

    public void deleteById(Long id) {
        members.remove(id);
    }

}
