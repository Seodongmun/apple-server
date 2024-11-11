package com.server.apple.domain;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private MultipartFile productFile;

    private String title;

    private String content;

    private int price;

    private int stock;

    private Member member;

    private int productCode;

}
