package com.tcgmarketplace.product;

import com.tcgmarketplace.product.dto.CreateProductDto;
import com.tcgmarketplace.product.dto.ProductDto;
import com.tcgmarketplace.product.dto.UpdateProductDto;
import com.tcgmarketplace.expansion.Expansion;
import com.tcgmarketplace.expansion.ExpansionRepository;
import com.tcgmarketplace.card.Card;
import com.tcgmarketplace.card.CardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ExpansionRepository expansionRepository;
    private final CardRepository cardRepository;

    public ProductServiceImpl(ProductRepository productRepository,
                              ExpansionRepository expansionRepository,
                              CardRepository cardRepository) {
        this.productRepository = productRepository;
        this.expansionRepository = expansionRepository;
        this.cardRepository = cardRepository;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public ProductDto getProductById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return toDto(product);
    }

    @Override
    public ProductDto createProduct(CreateProductDto dto) {
        Product product = new Product();
        product.setName(dto.name());
        product.setProductType(dto.productType());

        if(dto.expansionId() != null) {
            Expansion expansion = expansionRepository.findById(dto.expansionId())
                    .orElseThrow(() -> new RuntimeException("Expansion not found"));
            product.setExpansion(expansion);
        }
        if(dto.cardId() != null) {
            Card card = cardRepository.findById(dto.cardId())
                    .orElseThrow(() -> new RuntimeException("Card not found"));
            product.setCard(card);
        }
        if(dto.parentProductId() != null) {
            Product parent = productRepository.findById(dto.parentProductId())
                    .orElseThrow(() -> new RuntimeException("Parent product not found"));
            product.setParentProduct(parent);
        }

        productRepository.save(product);
        return toDto(product);
    }

    @Override
    public ProductDto updateProduct(Integer id, UpdateProductDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setName(dto.name());
        product.setProductType(dto.productType());

        if(dto.expansionId() != null) {
            Expansion expansion = expansionRepository.findById(dto.expansionId())
                    .orElseThrow(() -> new RuntimeException("Expansion not found"));
            product.setExpansion(expansion);
        } else {
            product.setExpansion(null);
        }

        if(dto.cardId() != null) {
            Card card = cardRepository.findById(dto.cardId())
                    .orElseThrow(() -> new RuntimeException("Card not found"));
            product.setCard(card);
        } else {
            product.setCard(null);
        }

        if(dto.parentProductId() != null) {
            Product parent = productRepository.findById(dto.parentProductId())
                    .orElseThrow(() -> new RuntimeException("Parent product not found"));
            product.setParentProduct(parent);
        } else {
            product.setParentProduct(null);
        }

        product.setProductType(dto.productType());

        productRepository.save(product);
        return toDto(product);
    }

    @Override
    public void deleteProduct(Integer id) {
        if(!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
    }

    private ProductDto toDto(Product product) {
        Integer expansionId = product.getExpansion() != null ? product.getExpansion().getId() : null;
        Integer cardId = product.getCard() != null ? product.getCard().getId() : null;
        Integer parentProductId = product.getParentProduct() != null ? product.getParentProduct().getId() : null;
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getProductType(),
                expansionId,
                cardId,
                parentProductId,
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}
