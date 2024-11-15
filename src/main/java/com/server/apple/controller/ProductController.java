package com.server.apple.controller;

import com.server.apple.domain.Member;
import com.server.apple.domain.Paging;
import com.server.apple.domain.Product;
import com.server.apple.domain.ProductDTO;
import com.server.apple.server.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/*")
@CrossOrigin(origins = {"*"}, maxAge =6000)
public class ProductController {
    // =================================================================================== //
    private String path= "\\\\192.168.10.51\\upload\\";

    // =================================================================================== //
    @Autowired
    private ProductService service;

    // 상품 전체보기
    @GetMapping("/product")
    public ResponseEntity viewAll(){
        return ResponseEntity.status(HttpStatus.OK).body(service.viewAll());
    }

    // 판매자가 가진 상품리스트 조회
    @GetMapping("/private/product/seller/{id}")
    public ResponseEntity getProductCode(@PathVariable(name="id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getProductCode(id));
    }

    // productCode로 상품 한개 조회
    @GetMapping("/private/product/{productCode}")
    public ResponseEntity getProduct(@PathVariable(name="productCode") int productCode) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getProduct(productCode));
    }

    // 상품 제거
    @DeleteMapping("/private/product/{productCode}")
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
    @PostMapping("/private/product")
    public ResponseEntity addProduct(@RequestPart("productFile") MultipartFile productFile,  // dto랑 파일 따로 받음
                                     @RequestPart("product") Product vo) throws IOException {
        
        String uuid = UUID.randomUUID().toString();
        String productName = uuid + "_" + productFile.getOriginalFilename(); // `MultipartFile`에서 파일 이름 가져오기
        System.out.println("업로드된 파일 이름 = " + productFile.getOriginalFilename());

        // 저장 경로와 파일 이름 설정
        File uploadFile = new File(path + "product" + File.separator + productName);
        System.out.println("업로드 파일 경로 = " + uploadFile);
        // 파일 저장

        productFile.transferTo(uploadFile);
        System.out.println("이미지 추가 = " + productFile);

        Member member = Member.builder()
                .id(vo.getMember().getId())
                .build();
        // 파일 추가삭제는 본인의 서버에 권한있어야 가능
        Product product = Product.builder()
                .img("http://192.168.10.51/upload/product/" + productName)
                .title(vo.getTitle())
                .content(vo.getContent())
                .price(vo.getPrice())
                .stock(vo.getStock())
                .member(member)
                .build();
        System.out.println("추가된 상품 정보 = " + product);

        service.create(product);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 상품 업데이트
    @PutMapping ("/private/product")
    public ResponseEntity updateProduct(@RequestBody ProductDTO dto){

        Member member = Member.builder()
                .id(dto.getMember().getId())
                .build();

        Product product = Product.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .member(member)
                .build();

        service.create(product);
        return ResponseEntity.status(HttpStatus.OK).build();
    }




}
