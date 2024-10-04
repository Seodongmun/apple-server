package com.server.apple.server;

import com.server.apple.domain.Member;
import com.server.apple.repo.MemberDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberDAO dao;

    @Autowired
    private PasswordEncoder bcpe;

    public void signup(Member vo){

        vo.setPassword(bcpe.encode(vo.getPassword()));
        dao.save(vo);
    }

    public Member login(String id,String password){
        Member member = dao.findById(id) // 아이디로 멤버가 있는지 확인
                .orElseThrow(()-> new UsernameNotFoundException("Member Not Found")); // 아이디 못찾을시
        if(bcpe.matches(password,member.getPassword())){ // 암호화된 비밀번호와 입력한 비밀번호 매치
            return member;
        }
        return null;
    }




}
