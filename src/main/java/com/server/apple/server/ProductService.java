package com.server.apple.server;

import com.server.apple.domain.Paging;
import com.server.apple.domain.Product;
import com.server.apple.repo.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductDAO dao;
    
    // 예외처리 클래스
    public class ProductNotFoundException extends RuntimeException {
        public ProductNotFoundException(String message) {
            super(message);
        }
    }

    // 상품 전체 조회
    public List<Product> viewAll(){

    /*
		   limit가 10인 경우
		   page = 1 -> offset = 0
		   page = 2 -> offset = 10
		   page = 3 -> offset = 20 ...

		   offset = limit * (page -1)
		 * */

//        paging.setOffset(paging.getLimit() * (paging.getPage() -1));
//
//        System.out.println("Offset = " + paging.getOffset());
//        System.out.println("Limit = " + paging.getLimit());
//
//        return dao.findAll(paging.getOffset(), paging.getLimit());
        return dao.findAll();
    }

    // 상품 한개 조회
    public Product getProduct(int productCode) {
        Product product = dao.findById(productCode).orElse(null);
        System.out.println("상품하나조회 = " + product);
        return product;
    }

    // 판매자가 가진 상품리스트 조회
    public List<Product> getProductCode(String id){

        List<Product> product = dao.getProductCode(id);
        System.out.println(product);

        return product;
    }

    // 상품 추가
    public void create(Product vo){
        dao.save(vo);
    }

    // 삭제
    public void deleteProduct(int productCode){

        try {
            dao.deleteById(productCode);
        } catch (EmptyResultDataAccessException e) {
            throw new ProductNotFoundException("일치하는 상품 코드가 없습니다: " + productCode);
        }

        
    }
    
    // 좋아요 업데이트
    public int updateLike(int like , int productCode) {
        return dao.updateLike(like , productCode);
    }






}
