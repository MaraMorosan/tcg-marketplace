package com.tcgmarketplace.listing;

import com.tcgmarketplace.listing.dto.CreateListingDto;
import com.tcgmarketplace.listing.dto.ListingDto;
import com.tcgmarketplace.listing.dto.UpdateListingDto;
import com.tcgmarketplace.product.Product;
import com.tcgmarketplace.product.ProductRepository;
import com.tcgmarketplace.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ListingServiceImpl implements ListingService {

    private final ListingRepository listingRepository;
    private final ProductRepository productRepository;

    public ListingServiceImpl(ListingRepository listingRepository,
                              ProductRepository productRepository) {
        this.listingRepository = listingRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<ListingDto> getAllListings() {
        return listingRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public ListingDto getListingById(Integer id) {
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Listing not found"));
        return toDto(listing);
    }

    @Override
    public ListingDto createListing(User seller, CreateListingDto dto) {
        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getParentProduct() == null) {
            throw new RuntimeException("Category products cannot be listed.");
        }

        Listing listing = new Listing();
        listing.setProduct(product);
        listing.setSeller(seller);
        listing.setStock(dto.stock());
        listing.setPrice(dto.price());
        listing.setCondition(dto.condition() != null ? dto.condition() : Condition.NEAR_MINT);

        listingRepository.save(listing);
        return toDto(listing);
    }

    @Override
    public ListingDto updateListing(User user, Integer id, UpdateListingDto dto) {
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Listing not found"));

        if (!listing.getSeller().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized: You can only edit your own listings");
        }

        listing.setStock(dto.stock());
        listing.setPrice(dto.price());
        listing.setCondition(dto.condition() != null ? dto.condition() : listing.getCondition());

        listingRepository.save(listing);
        return toDto(listing);
    }

    @Override
    public void deleteListing(User user, Integer id) {
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Listing not found"));

        if (!listing.getSeller().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized: You can only delete your own listings");
        }

        listingRepository.deleteById(id);
    }

    private ListingDto toDto(Listing listing) {
        Integer productId = listing.getProduct() != null ? listing.getProduct().getId() : null;
        Integer sellerId = listing.getSeller() != null ? listing.getSeller().getId() : null;
        return new ListingDto(
                listing.getId(),
                productId,
                sellerId,
                listing.getStock(),
                listing.getPrice(),
                listing.getCondition(),
                listing.getCreatedAt(),
                listing.getUpdatedAt()
        );
    }
}
