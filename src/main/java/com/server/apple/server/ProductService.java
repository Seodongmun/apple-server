package com.server.apple.server;

import com.server.apple.domain.Product;
import com.server.apple.repo.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductDAO dao;

    // 상품 전체 조회
    public List<Product> viewAll(){
        return dao.findAll();
    }

    // 상품 한개 조회
    public Product view(int productCode) {
        Product product = dao.findById(productCode).orElse(null);
        System.out.println("한개짜리 상품조회 = " + product);
        return product;
    }

    // changeProduct (save)
    public void changeProduct(Product vo){
        dao.save(vo);
    }

    // 삭제
    public void deleteProduct(int productCode){
        dao.deleteById(productCode);
    }



    
    // 이미지 추가
    public void addImg(){

    }    



}
