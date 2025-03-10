package com.tcgmarketplace.product;

import com.tcgmarketplace.product.dto.CreateProductDto;
import com.tcgmarketplace.product.dto.ProductDto;
import com.tcgmarketplace.product.dto.UpdateProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAllProducts();
    ProductDto getProductById(Integer id);
    List<ProductDto> getAllCategories();
    List<ProductDto> filterProducts(
            String productType,
            String expansionName,
            String rarityName,
            String searchTerm,
            String sortBy
    );
    ProductDto getProductByCategoryExpansionAndCardName(String category, String expansion, String name);
    List<Product> getProductByExpansionName(String expansionName);
    List<Product> searchByName(String name, int limit);
    int countByNameLike(String name);
    ProductDto createProduct(CreateProductDto dto);
    ProductDto updateProduct(Integer id, UpdateProductDto dto);
    void deleteProduct(Integer id);
}
