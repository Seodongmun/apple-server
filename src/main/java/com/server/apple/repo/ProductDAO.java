package com.server.apple.repo;

import com.server.apple.domain.Paging;
import com.server.apple.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductDAO extends JpaRepository<Product, Integer> {
    
    // 좋아요 업데이트
    @Query(value= "UPDATE product SET like = :like WHERE product_code = :productCode", nativeQuery = true)
    int updateLike(@Param("like") int like , @Param("productCode") int productCode);

    // 판매자가 가진 상품리스트 조회
    @Query(value="SELECT * FROM product WHERE id = :id",nativeQuery = true)
    List<Product> getProductCode(@Param("id") String id);

    // 페이징
//    @Query(value="SELECT * FROM product Offset = :offset , Limit = :limit", nativeQuery = true)
//    List<Product> findAll(@Param("offset") int offset , @Param("limit") int limit);

}
