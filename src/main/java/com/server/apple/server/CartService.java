package com.server.apple.server;

import com.server.apple.domain.Cart;
import com.server.apple.domain.Product;
import com.server.apple.repo.CartDAO;
import com.server.apple.repo.ProductDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CartService {

    @Autowired
    private CartDAO dao;


    // 상품 코드로 해당 상품 정보 가져오기
    public List<Cart> selectCart(int productCode, String id){
        return dao.selectCart(productCode, id);
    }


    // 장바구니에 상품 추가
    public void addCart(Cart cart) {
        System.out.println("서비스 들어온 카트 = " + cart);
        dao.save(cart);
    }
    
    // 회원이가진 여러 상품의 count합계
    public int cartCount(String id , int productCode) {
        System.out.println("카트 카운트 id = " + id);
        System.out.println("카트 카운트 productCode = " + productCode);
        return dao.cartCount(id,productCode);
    }

    // 회원이 가진 상품 장바구니 업데이트
    public void updateCount(int count , int productCode , String id){
        System.out.println("카트 서비스 count = " + count);
        System.out.println("카트 서비스 productCode = " + productCode);
        System.out.println("카트 서비스 id = " + id);

        dao.updateCount(count, productCode , id);
    }

    // 장바구니 아이디 받아서 삭제
    public void deleteCart(int cartCode) {
        dao.deleteById(cartCode);
    }



}
