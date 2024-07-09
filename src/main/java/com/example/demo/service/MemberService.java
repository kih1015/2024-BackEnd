package com.example.demo.service;

import com.example.demo.controller.dto.request.MemberCreateRequest;
import com.example.demo.controller.dto.request.MemberLoginRequest;
import com.example.demo.controller.dto.request.MemberUpdateRequest;
import com.example.demo.controller.dto.response.MemberResponse;
import com.example.demo.domain.Member;
import com.example.demo.exception.RestApiException;
import com.example.demo.exception.error.ErrorCode;
import com.example.demo.exception.error.MemberErrorCode;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MemberService {

    @Value("${jwt.secret}")
    private String secretKey;

    final private Long expiredMs = 1000 * 60 * 60L;

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public MemberResponse getById(Long id) {
        Member member = memberRepository.findById(id);
        return MemberResponse.from(member);
    }

    public List<MemberResponse> getAll() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
            .map(MemberResponse::from)
            .toList();
    }

    @Transactional
    public MemberResponse create(MemberCreateRequest request) {
        Member member = memberRepository.save(
            new Member(request.name(), request.email(), passwordEncoder.encode(request.password()))
        );
        return MemberResponse.from(member);
    }

    @Transactional
    public void delete(Long id) {
        memberRepository.deleteById(id);
    }

    @Transactional
    public MemberResponse update(Long id, MemberUpdateRequest request) {
        Member member = memberRepository.findById(id);
        member.update(request.name(), request.email());
        return MemberResponse.from(member);
    }

    public String login(MemberLoginRequest request) {
        // 인증과정
        Member selectedMember = memberRepository.findByEmail(request.email()).orElseThrow(() -> new RestApiException(MemberErrorCode.EMAIL_NOT_FOUND));
        if (!passwordEncoder.matches(request.password(), selectedMember.getPassword())) {
            throw new RestApiException(MemberErrorCode.INVALID_PASSWORD);
        }

        return JwtUtil.createJwt(request.email(), secretKey, expiredMs);
    }

}
