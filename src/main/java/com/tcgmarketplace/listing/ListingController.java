package com.tcgmarketplace.listing;

import com.tcgmarketplace.listing.dto.CreateListingDto;
import com.tcgmarketplace.listing.dto.ListingDto;
import com.tcgmarketplace.listing.dto.UpdateListingDto;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/listing")
public class ListingController {

    private final ListingService listingService;

    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }

    @GetMapping
    public List<ListingDto> getAllListings() {
        return listingService.getAllListings();
    }

    @GetMapping("/{id}")
    public ListingDto getListingById(@PathVariable Integer id) {
        return listingService.getListingById(id);
    }

    @PostMapping
    public ListingDto createListing(@RequestBody CreateListingDto dto) {
        return listingService.createListing(dto);
    }

    @PutMapping("/{id}")
    public ListingDto updateListing(@PathVariable Integer id, @RequestBody UpdateListingDto dto) {
        return listingService.updateListing(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteListing(@PathVariable Integer id) {
        listingService.deleteListing(id);
    }
}
