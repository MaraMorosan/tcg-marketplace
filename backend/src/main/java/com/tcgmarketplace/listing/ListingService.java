package com.tcgmarketplace.listing;

import com.tcgmarketplace.listing.dto.CreateListingDto;
import com.tcgmarketplace.listing.dto.ListingDto;
import com.tcgmarketplace.listing.dto.UpdateListingDto;
import java.util.List;

public interface ListingService {
    List<ListingDto> getAllListings();
    ListingDto getListingById(Integer id);
    ListingDto createListing(CreateListingDto dto);
    ListingDto updateListing(Integer id, UpdateListingDto dto);
    void deleteListing(Integer id);
}
