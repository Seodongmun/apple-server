package com.server.apple.controller;

import com.server.apple.domain.Cart;
import com.server.apple.domain.CartDTO;
import com.server.apple.domain.Member;
import com.server.apple.domain.Product;

import com.server.apple.server.CartService;
import com.server.apple.server.MemberService;
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

    @Autowired
    private MemberService memberService;

    // 아이디로 정보 가져오기
    @GetMapping("/private/select")
    public ResponseEntity selectCart(@RequestParam("id") String id) {

        List<Cart> cart = service.select(id);
//        System.out.println("카트정보 왔니? = " + cart);

        return ResponseEntity.status(HttpStatus.OK).body(cart);
    }


    // 추가할때 상품 테이블에 있는 productCode랑 멤버의 id를 받아야함
    @PostMapping("/private/cart")
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

        service.addCart(cart);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/private/cartCount")
    public ResponseEntity cartCount(@RequestBody Cart vo){
        Member member = Member.builder()
                .id(vo.getMember().getId())
                .build();
        vo.setMember(member);

        System.out.println("들어온 vo 정보 = " + vo);

        return ResponseEntity.status(HttpStatus.OK).body(service.cartCount(vo.getCartCode(),vo.getMember().getId()));
    }

    @PostMapping("/private/findCode")
    public ResponseEntity findCode(@RequestBody Cart vo){

        Member member = Member.builder()
                .id(vo.getMember().getId())
                .build();
        System.out.println(member);
        List<Integer> code = service.findCode(member.getId());
        System.out.println("코드리스트 = " + code);
        return ResponseEntity.status(HttpStatus.OK).body(code);
    }


    // 장바구니 count + 1 업데이트
    @PutMapping("/private/cart/increaseCount")
    public ResponseEntity increaseCount(@RequestBody Cart vo) {
        return ResponseEntity.status(HttpStatus.OK).body(service.increaseCount(vo.getCartCode()));
    }

    // 장바구니 count - 1 업데이트
    @PutMapping("/private/cart/decreaseCount")
    public ResponseEntity decreaseCount(@RequestBody Cart vo) {
        return ResponseEntity.status(HttpStatus.OK).body(service.decreaseCount(vo.getCartCode()));
    }

    // 카트 품목 삭제
    @DeleteMapping("/private/cart/{cartCode}")
    public ResponseEntity deleteCart(@PathVariable(name="cartCode") int cartCode) {

        System.out.println("삭제 할 카트코드 = " + cartCode);
        service.deleteCart(cartCode);
        
        return ResponseEntity.status(HttpStatus.OK).build();
    }








}
