package com.server.apple.controller;

import com.server.apple.config.TokenProvider;
import com.server.apple.domain.Member;
import com.server.apple.domain.MemberDTO;
import com.server.apple.server.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/*")
@CrossOrigin(origins = {"*"}, maxAge= 6000)
public class MemberController {

    @Autowired
    private MemberService service;

    @Autowired
    private TokenProvider tokenProvider;

    // 멤버 전체 조회
    @GetMapping("/member")
    public ResponseEntity allMember() {
        return ResponseEntity.status(HttpStatus.OK).body(service.allMember());
    }

    // 멤버 한명 조회
    @GetMapping("/member/{id}")
    public ResponseEntity selectMember(@PathVariable(name="id") String id) {
//        System.out.println("조회한 아이디 = " + id);
        return ResponseEntity.status(HttpStatus.OK).body(service.selectMember(id));
    }


    // 멤버 정보 수정
    @PutMapping("/private/member")
    public ResponseEntity update(@RequestBody Member vo){
        service.update(vo);
        System.out.println("업데이트 컨트롤러 = " + vo);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 회원 탈퇴
    @DeleteMapping("/private/member/{id}")
    public ResponseEntity delete(@PathVariable(name = "id") String id) {

        System.out.println("클라이언트에서 받은 아이디 = " + id);
        service.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 로그인한 사람 비밀번호 체크
    @PostMapping("/private/member")
    public ResponseEntity passwordCheck(@RequestBody Member vo) {
        System.out.println("비밀번호 체크 결과 = " + service.passwordCheck(vo.getPassword()));

        try {
            if (service.passwordCheck(vo.getPassword())) {
                return ResponseEntity.status(HttpStatus.OK).build(); // 200인지
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호가 다릅니다."); // 400인지
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생.");
        }

    }

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
            System.out.println("로그인 발급 토큰 = " + token);
            return ResponseEntity.ok(MemberDTO.builder()
                    .id(member.getId())
                    .token(token)
                    .build());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 로그인 실패
    }





}

