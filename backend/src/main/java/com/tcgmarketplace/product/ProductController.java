package com.tcgmarketplace.product;

import com.tcgmarketplace.product.dto.CreateProductDto;
import com.tcgmarketplace.product.dto.ProductDto;
import com.tcgmarketplace.product.dto.UpdateProductDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchProducts(
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false, defaultValue = "10") int limit
    ) {
        List<Product> results = productService.searchByName(name, limit);
        int total = productService.countByNameLike(name);

        Map<String, Object> response = new HashMap<>();
        response.put("results", results);
        response.put("total", total);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Integer id) {
        return productService.getProductById(id);
    }

    @GetMapping("/search/expansion")
    public ResponseEntity<List<Product>> getProductsByExpansion(@RequestParam String name) {
        List<Product> products = productService.getProductByExpansionName(name);
        if (products.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ProductDto createProduct(@RequestBody CreateProductDto dto) {
        return productService.createProduct(dto);
    }

    @PutMapping("/{id}")
    public ProductDto updateProduct(@PathVariable Integer id, @RequestBody UpdateProductDto dto) {
        return productService.updateProduct(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
    }
}
