package com.ecommerce.happyshopping.service;


import com.ecommerce.happyshopping.payload.ProductDTO;
import com.ecommerce.happyshopping.payload.ProductResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    ProductResponse getAllProducts(Integer pageNumber, Integer pageSize,  String sortBy, String sortOrder);
    ProductResponse getProductsByCategory(Long categoryId, Integer pageNumber, Integer pageSize,  String sortBy, String sortOrder);
    ProductResponse getProductsByKeyword(String keyword, Integer pageNumber, Integer pageSize,  String sortBy, String sortOrder);

    ProductDTO addProduct(ProductDTO productDTO,  Long categoryId);
    ProductDTO updateProduct(Long productId, ProductDTO productDTO);
    ProductDTO deleteProduct(Long id);
    ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException;
}

