package com.server.apple.server;

import com.server.apple.domain.Cart;
import com.server.apple.repo.CartDAO;
import com.server.apple.repo.MemberDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CartService {

    @Autowired
    private CartDAO dao;

    @Autowired
    private MemberDAO memDao;


    // 상품 코드로 해당 상품 정보 가져오기
    public List<Cart> select(String id){
        return dao.select(id);
    }

    // 장바구니에 상품 추가
    public void addCart(Cart cart) {
        System.out.println("서비스 들어온 카트 = " + cart);
        dao.save(cart);
    }

    // 카운트 조회
    public List<Integer> cartCount(int cartCode , String id) {
        List<Integer> count = dao.cartCount(id, cartCode);
        System.out.println(id + cartCode);
        System.out.println("카운트 리스트 몇개? = " + count);
        return count;
    }

    // 회원이 가진 카트 코드만 조회
    public List<Integer> findCode(String id){
        List<Integer> code = dao.findCode(id);
        return code;
    }

    // 회원이 가진 상품 장바구니 업데이트
    public int increaseCount(int cartCode){
        int count = dao.increaseCount(cartCode);
        return count;
    }
    public int decreaseCount(int cartCode) {
        int count = dao.decreaseCount(cartCode);
        return count;
    }

    // 장바구니 아이디 받아서 삭제
    public void deleteCart(int cartCode) {
        dao.deleteById(cartCode);
    }



}
