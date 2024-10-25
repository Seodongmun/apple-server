package com.server.apple.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CartDTO {

    private String id;

    private String title;

    private String content;

    private int price;

    private int count;

    private int productCode;




}

