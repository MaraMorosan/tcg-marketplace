package com.tcgmarketplace.listing;

import com.tcgmarketplace.listing.dto.CreateListingDto;
import com.tcgmarketplace.listing.dto.ListingDto;
import com.tcgmarketplace.listing.dto.UpdateListingDto;
import com.tcgmarketplace.user.User;
import com.tcgmarketplace.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/listings")
public class ListingController {

    private final ListingService listingService;
    private final UserRepository userRepository;

    public ListingController(ListingService listingService, UserRepository userRepository) {
        this.listingService = listingService;
        this.userRepository = userRepository;
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
    public ResponseEntity<ListingDto> createListing(@AuthenticationPrincipal UserDetails userDetails,
                                                    @RequestBody CreateListingDto dto) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(listingService.createListing(user, dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ListingDto> updateListing(@AuthenticationPrincipal UserDetails userDetails,
                                                    @PathVariable Integer id,
                                                    @RequestBody UpdateListingDto dto) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(listingService.updateListing(user, id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteListing(@AuthenticationPrincipal UserDetails userDetails,
                                              @PathVariable Integer id) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        listingService.deleteListing(user, id);
        return ResponseEntity.noContent().build();
    }
}
