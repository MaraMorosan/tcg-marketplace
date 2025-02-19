package com.tcgmarketplace.product;

import com.tcgmarketplace.product.dto.CreateProductDto;
import com.tcgmarketplace.product.dto.ProductDto;
import com.tcgmarketplace.product.dto.UpdateProductDto;
import java.util.List;

public interface ProductService {
    List<ProductDto> getAllProducts();
    ProductDto getProductById(Integer id);
    ProductDto createProduct(CreateProductDto dto);
    ProductDto updateProduct(Integer id, UpdateProductDto dto);
    void deleteProduct(Integer id);
}
