package com.server.apple.config;


import com.server.apple.domain.Member;
import com.server.apple.repo.MemberDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;



@Slf4j // 구글 유저정보 조회용
@Component
public class OAuth2SucessHandler  extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private MemberDAO dao;

    // 구글 로그인 후 위치
    // Authentication 시큐리티로 import
    public void onAuthenticationSuccess(HttpServletRequest requset , HttpServletResponse response , Authentication authentication) throws IOException{
        // 유저 정보 조회
        OAuth2User user = (OAuth2User) authentication.getPrincipal();
        log.info("google : " + user);
        
        // 소셜 로그인 성공 후 사용자 정보 추출
        String email = (String) user.getAttributes().get("email");
        System.out.println(email);
        // DB에서 사용자 정보 조회 ( email로 )
        Member member = dao.findByEmail(email).orElseGet(()->{
            // + 존재하지 않으면 신규 사용자로 등록

            return dao.save(Member.builder()
                            .id(email)
                            .email(email)
                            .build());
        });

        // JWT 토큰 생성
        String token = tokenProvider.create(member);
        
        // 토큰을 포함한 리다이렉트 처리
        response.sendRedirect("http://localhost:3000/login-success?token=" + token);
    }



}
