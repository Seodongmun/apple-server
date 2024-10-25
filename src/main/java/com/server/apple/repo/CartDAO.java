package com.server.apple.repo;


import com.server.apple.domain.Cart;
import com.server.apple.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CartDAO extends JpaRepository<Cart, Integer> {


    // 한 회원이가진 여러 상품의 count합계
    //  SELECT product_code, SUM(count)
    //  FROM cart
    //  WHERE id = "id"
    //  GROUP BY product_code;
    // 받는 파라미터는 id랑 productCode
    @Query(value="SELECT SUM(count) , product_code FROM cart WHERE id = :id AND product_code = :productCode GROUP BY product_code" , nativeQuery = true)
    int cartCount(@Param("id") String id, @Param("productCode") int productCode);

    // 회원이 가진 상품 장바구니 업데이트
    // UPDATE cart SET count = :count WHERE product_code = :productCode AND id = :id
    @Modifying
    @Transactional
    @Query(value="UPDATE cart SET count = :count WHERE product_code = :productCode AND id = :id",  nativeQuery = true)
    void updateCount(@Param("count") int count , @Param("productCode") int productCode , @Param("id") String id );

    // 상품코드로 해당 상품정보만 가져오기
    @Query(value="SELECT * FROM cart WHERE product_code = :productCode AND id = :id", nativeQuery = true)
    List<Cart> selectCart(@Param("productCode") int productCode , @Param("id") String id);




}
