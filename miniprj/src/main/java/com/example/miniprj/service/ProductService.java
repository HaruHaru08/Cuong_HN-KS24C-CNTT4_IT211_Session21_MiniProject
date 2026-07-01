package com.example.miniprj.service;

import com.example.miniprj.dto.ProductDTO;
import com.example.miniprj.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product createProduct(ProductDTO productDTO);
    Product updateProduct(Long id, ProductDTO productDTO);
    void deleteProduct(Long id);
}
