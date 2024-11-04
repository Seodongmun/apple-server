package com.server.apple.repo;


import com.server.apple.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


public interface CartDAO extends JpaRepository<Cart, Integer> {

    // 코드 조회
    @Query(value="SELECT cart_code FROM cart WHERE id = :id", nativeQuery = true)
    List<Integer> findCode (@Param("id") String id);


    // 카운트 1 증가
    @Modifying
    @Transactional
    @Query(value="UPDATE cart SET count = count + 1 WHERE cart_code = :cartCode",  nativeQuery = true)
    int increaseCount(@Param("cartCode")int cartCode);

    @Modifying
    @Transactional
    @Query(value="UPDATE cart SET count = count - 1 WHERE cart_code = :cartCode",  nativeQuery = true)
    int decreaseCount(@Param("cartCode")int cartCode);

    // 카트 카운트
    @Query(value="SELECT count FROM cart WHERE id = :id AND cart_code = :cartCode",nativeQuery = true)
    List<Integer> cartCount (@Param("id") String id , @Param("cartCode") int cartCode);


    // 아이디로 선택
    @Query(value="SELECT * FROM cart WHERE id = :id", nativeQuery = true)
    List<Cart> select(@Param("id") String id);






}
