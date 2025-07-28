package com.ecommerce.happyshopping.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long productId;
    private String productName;
    private String description;
    private String image;
    private Double price;
    private Integer quantity;
    private Integer specialPrice;
    private String category_id;
}
