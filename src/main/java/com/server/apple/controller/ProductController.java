package com.server.apple.controller;

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

    // 상품 전체보기
    @GetMapping("/product")
    public ResponseEntity viewAll(){
        System.out.println(service.viewAll());
        return ResponseEntity.status(HttpStatus.OK).body(service.viewAll());
    }

    // 상품 추가
    @PostMapping("/product")
    public ResponseEntity addProduct(@RequestBody Product vo){
        System.out.println(vo);
        service.changeProduct(vo);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 상품 수정
    @PutMapping ("/product")
    public ResponseEntity updateProduct(@RequestBody Product vo){
        System.out.println(vo);
        service.changeProduct(vo);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    





}
