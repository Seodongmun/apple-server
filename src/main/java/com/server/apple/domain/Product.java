package com.server.apple.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @Column(name="product_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productCode;
    @Column
    private String title;
    @Column
    private String content;
    @Column
    private int price;
    @Column
    private String img;
    @Column
    private int stock;
    @Column
    private boolean status;
    @Column(name="like_count")
    private int likeCount;
    @ManyToOne
    @JoinColumn(name="id")
    private Member member;



}
