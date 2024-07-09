package com.example.demo.jwt;

import com.example.demo.service.MemberService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtFilter extends OncePerRequestFilter {

    private final MemberService memberService;
    private final String secretKey;

    public JwtFilter(MemberService memberService, String secretKey) {
        this.memberService = memberService;
        this.secretKey = secretKey;
    }

    // 필터 실제 로직
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        // token 안보내면 block
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // token 꺼내기
        String token = authorization.split(" ")[1];

        // token expired 여부
        if (JwtUtil.isExpired(token, secretKey)) {
            filterChain.doFilter(request, response);
            return;
        }

        // UserName token 에서 꺼내기
        String userName = JwtUtil.getUserName(token, secretKey);

        //인증된 사용자를 나타내는 토큰 객체를 생성하고, 권한 정보를 설정
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userName, null, List.of(new SimpleGrantedAuthority("USER")));
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
