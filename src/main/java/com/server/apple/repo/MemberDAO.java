package com.server.apple.repo;


import com.server.apple.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface MemberDAO extends JpaRepository<Member,String> {

    // 이메일로 멤버정보 조회 ( 단순한 쿼리문 -> JPQL 문 )
    // SELECT * FROM member WHERE email =
    @Query(value = "SELECT * FROM member WHERE email = :email", nativeQuery = true)
    Optional<Member> findByEmail(@Param("email") String email);

    // SELECT * FROM member WHERE id = id AND password = password
    @Query(value= "SELECT * FROM member WHERE id = :id AND password = :password", nativeQuery = true)
    Member find(@Param("id") String id , @Param("password") String password);
    



}
