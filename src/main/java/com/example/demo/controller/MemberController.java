package com.example.demo.controller;

import com.example.demo.controller.dto.request.MemberCreateRequest;
import com.example.demo.controller.dto.request.MemberUpdateRequest;
import com.example.demo.controller.dto.response.MemberResponse;
import com.example.demo.exception.RestApiException;
import com.example.demo.exception.error.CommonErrorCode;
import com.example.demo.exception.error.MemberErrorCode;
import com.example.demo.service.ArticleService;
import com.example.demo.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final ArticleService articleService;

    public MemberController(MemberService memberService, ArticleService articleService) {
        this.memberService = memberService;
        this.articleService = articleService;
    }

    @GetMapping()
    public ResponseEntity<List<MemberResponse>> getMembers() {
        List<MemberResponse> response = memberService.getAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> getMember(@PathVariable Long id) {
        if (memberService.getAll().stream().noneMatch(res -> res.id().equals(id))) {
            throw new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND);
        }
        MemberResponse response = memberService.getById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/join")
    public ResponseEntity<MemberResponse> create(@RequestBody MemberCreateRequest request) {
        if (request.name() == null || request.email() == null || request.password() == null) {
            throw new RestApiException(CommonErrorCode.NULL_PARAMETER);
        }
        MemberResponse response = memberService.create(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok().body("token");
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberResponse> updateMember(@PathVariable Long id, @RequestBody MemberUpdateRequest request) {
        if (memberService.getAll().stream().filter(res -> !res.id().equals(id)).anyMatch(res -> res.email().equals(request.email()))) {
            throw new RestApiException(MemberErrorCode.EMAIL_CONFLICT);
        }
        MemberResponse response = memberService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        if (articleService.getArticles().stream().anyMatch(res -> res.authorId().equals(id))) {
            throw new RestApiException(MemberErrorCode.ARTICLE_EXISTENCE);
        }
        memberService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
