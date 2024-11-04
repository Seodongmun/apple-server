package com.server.apple.controller;

import com.server.apple.domain.Member;
import com.server.apple.domain.Paging;
import com.server.apple.domain.Product;
import com.server.apple.server.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/*")
@CrossOrigin(origins = {"*"}, maxAge =6000)
public class ProductController {

    @Autowired
    private ProductService service;

//    // 상품 전체보기
    @GetMapping("/product")
    public ResponseEntity viewAll(){

        return ResponseEntity.status(HttpStatus.OK).body(service.viewAll());
    }

    // 판매자가 가진 상품리스트 조회
    @GetMapping("/product/seller/{id}")
    public ResponseEntity getProductCode(@PathVariable(name="id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getProductCode(id));
    }

    // productCode로 상품 한개 조회
    @GetMapping("/product/{productCode}")
    public ResponseEntity getProduct(@PathVariable(name="productCode") int productCode) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getProduct(productCode));
    }

    // 상품 제거
    @DeleteMapping("/product/{productCode}")
    public ResponseEntity removeProduct(@PathVariable(name= "productCode") int productCode) {

        try {
            System.out.println("제거된 상품 = " + productCode + " 번");
            service.deleteProduct(productCode);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (ProductService.ProductNotFoundException e) {
            System.out.println("삭제할 상품이 없습니다");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    // 상품 추가
    @PostMapping("/product")
    public ResponseEntity addProduct(@RequestBody Product vo){

        Member member = Member.builder()
                .id(vo.getMember().getId())
                .build();

        vo.setMember(member);
        service.changeProduct(vo);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 상품 업데이트
    @PutMapping ("/product")
    public ResponseEntity updateProduct(@RequestBody Product vo){

        Member member = Member.builder()
                .id(vo.getMember().getId())
                .build();
        System.out.println("가져온 아이디 = " + vo.getMember().getId());
        System.out.println("업데이트 vo = " + vo);

        vo.setMember(member);
        service.changeProduct(vo);
        return ResponseEntity.status(HttpStatus.OK).build();
    }



}
