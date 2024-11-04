package com.server.apple.server;

import com.server.apple.domain.Member;
import com.server.apple.repo.MemberDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberDAO dao;

    @Autowired
    private PasswordEncoder bcpe;

    // 아이디로 사용자 정보가져오기
    private String getId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth!= null && auth.isAuthenticated()) {
            Member member = (Member) auth.getPrincipal();
            return member.getId();
        }
        return null;
    }

    // 회원 전체 조회
    public List<Member> allMember(){
        return dao.findAll();
    }

    // 비밀번호 체크
    public boolean passwordCheck(String password){
        System.out.println("아이디 정보 = " + getId());
        Member member = dao.findById(getId()) // 아이디로 멤버가 있는지 확인
                .orElseThrow(()-> new UsernameNotFoundException("Member Not Found")); // 아이디 못찾을시
        if(bcpe.matches(password , member.getPassword())) {
            System.out.println("true 리턴");
            return true;
        }
        System.out.println("false 리턴");
        return false;
    }

    // 아이디로 회원 정보 조회
    public Member selectMember(String id){
        return dao.select(id);
    }


    // 로그인
    public Member login(String id, String password){
        Member member = dao.findById(id) // 아이디로 멤버가 있는지 확인
                .orElseThrow(()-> new UsernameNotFoundException("Member Not Found")); // 아이디 못찾을시
        if(bcpe.matches(password,member.getPassword())){ // 암호화된 비밀번호와 입력한 비밀번호 매치
            System.out.println("이거 로그인한 멤버임? = " + member);
            return member;
        }
        return null;
    }

    // 회원 정보 수정
    public void update(Member vo){
        vo.setPassword(bcpe.encode((vo.getPassword())));
        dao.save(vo);
    }

    // 회원 탈퇴
    public void delete(String id) {
    dao.deleteById(id);
    }

    // 회원가입
    public void signup(Member vo){
        vo.setPassword(bcpe.encode(vo.getPassword()));
        dao.save(vo);
    }








}
