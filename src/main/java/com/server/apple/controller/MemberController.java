package com.server.apple.controller;

import com.server.apple.config.TokenProvider;
import com.server.apple.domain.Member;
import com.server.apple.server.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member/*")
@CrossOrigin(origins = {"*"}, maxAge= 6000)
public class MemberController {

    @Autowired
    private MemberService service;

    @Autowired
    private TokenProvider tokenProvider;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody Member vo) {
        service.signup(vo);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Member vo) {
        Member member = service.login(vo.getId(), vo.getPassword()); // 로그인 성공
        if (member!=null) {
            String token = tokenProvider.create(member);
            return ResponseEntity.ok(token);

        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 로그인 실패
    }



}

