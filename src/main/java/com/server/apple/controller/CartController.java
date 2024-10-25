package com.server.apple.controller;

import com.server.apple.domain.Cart;
import com.server.apple.domain.CartDTO;
import com.server.apple.domain.Member;
import com.server.apple.domain.Product;
import com.server.apple.repo.CartDAO;
import com.server.apple.server.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/*")
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class CartController {

    @Autowired
    private CartService service;

    // 상품 코드, 아이디로 해당 상품 정보 가져오기
    @GetMapping("/select")
    public ResponseEntity selectCart(@RequestParam("productCode") int productCode,
                                     @RequestParam("id") String id) {
        System.out.println("상품코드 오노? = " + productCode);
        System.out.println("아이디 오노? = " + id);
        List<Cart> cart = service.selectCart(productCode, id);
        System.out.println("카트정보 왔니? = " + cart);

        return ResponseEntity.status(HttpStatus.OK).body(cart);
    }

    // 추가할때 상품 테이블에 있는 productCode랑 멤버의 id를 받아야함
    @PostMapping("/cart")
    public ResponseEntity addCart(@RequestBody CartDTO dto){

        Member member = Member.builder()
                .id(dto.getId())
                .build();
        Product product = Product.builder()
                .productCode(dto.getProductCode())
                .build();
        Cart cart = Cart.builder()
                .count(dto.getCount())
                .member(member)
                .product(product)
                .orderDate(LocalDateTime.now())
                .build();

        System.out.println("받은 값 = " + cart);
        service.addCart(cart);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 회원이 가지고있는 장바구니 상품의 총 카운트
    @GetMapping("/cart/{id}")
    public ResponseEntity cartCount(@RequestBody CartDTO dto) {

        Member member = Member.builder()
                .id(dto.getId())
                .build();

        Product product = Product.builder()
                .productCode(dto.getProductCode())
                .build();

        int count = service.cartCount(member.getId() , product.getProductCode());
        System.out.println("아이디 = " +  member.getId() + "/ 상품코드 = " + product.getProductCode());
        System.out.println("총 갯수 = " + count);
        return ResponseEntity.status(HttpStatus.OK).body(count);
    }

    // 장바구니 count 업데이트
    @PutMapping("/cart")
    public ResponseEntity updateCount(@RequestBody Cart vo) {

//        System.out.println("/ vo 변경 카운트" + vo.getCount() + "vo 상품코드 = " + vo.getProductCode() + "/ vo 아이디 = " + vo.getId() );
//        service.updateCount(vo.getCount(), vo.getProductCode(), vo.getId());

        return ResponseEntity.status(HttpStatus.OK).build();
    }
    
    // 카트 품목 삭제
    @DeleteMapping("/cart/{cartCode}")
    public ResponseEntity deleteCart(@PathVariable(name="cartCode") int cartCode) {

        System.out.println("삭제 도착");
        service.deleteCart(cartCode);
        
        return ResponseEntity.status(HttpStatus.OK).build();
    }






}
