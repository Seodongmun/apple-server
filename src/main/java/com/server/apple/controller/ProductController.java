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

    // 상품 한개 보기
    @GetMapping("/product/{productCode}")
    public ResponseEntity viewProduct(@PathVariable(name="productCode") int productCode) {
        return ResponseEntity.status(HttpStatus.OK).body(service.view(productCode));
    }

    // 상품 전체보기
    @GetMapping("/product")
    public ResponseEntity viewAll(){
        return ResponseEntity.status(HttpStatus.OK).body(service.viewAll());
    }

    // 상품 제거
    @DeleteMapping("/private/product/{productCode}")
    public ResponseEntity removeProduct(@PathVariable(name= "productCode") int productCode) {
        System.out.println("상품제거할 id = " + productCode + " 번");
        service.deleteProduct(productCode);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 상품 추가
    @PostMapping("/private/product")
    public ResponseEntity addProduct(@RequestBody Product vo){
        service.changeProduct(vo);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 상품 수정
    @PutMapping ("/private/product")
    public ResponseEntity updateProduct(@RequestBody Product vo){
        System.out.println("업데이트 vo = " + vo);
        service.changeProduct(vo);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    





}
