package com.tcgmarketplace.listing;

import com.tcgmarketplace.listing.dto.CreateListingDto;
import com.tcgmarketplace.listing.dto.ListingDto;
import com.tcgmarketplace.listing.dto.UpdateListingDto;
import com.tcgmarketplace.user.User;

import java.util.List;

public interface ListingService {
    List<ListingDto> getAllListings();
    ListingDto getListingById(Integer id);
    ListingDto createListing(User user, CreateListingDto dto);
    ListingDto updateListing(User user, Integer id, UpdateListingDto dto);
    void deleteListing(User user, Integer id);
}
