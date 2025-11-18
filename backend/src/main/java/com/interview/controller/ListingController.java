package com.interview.controller;

import com.interview.model.dto.ListingDto;
import com.interview.model.dto.OfferDto;
import com.interview.service.ListingService;
import com.interview.service.OfferService;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path = "listing", produces = "application/json")
public class ListingController {

  private final ListingService listingService;
  private final OfferService offerService;

  @GetMapping
  public Set<ListingDto> getAllListings(
      @RequestParam(name = "includeOffers", defaultValue = "false", required = false)
          final boolean includeOffers) {
    return listingService.getAllListings(includeOffers);
  }

  @GetMapping("/{listingId}")
  public ListingDto getListing(
      @PathVariable UUID listingId,
      @RequestParam(name = "includeOffers", defaultValue = "false", required = false)
          final boolean includeOffers) {
    return listingService.getListingById(listingId, includeOffers);
  }

  @PostMapping
  public ListingDto createListing(@RequestBody ListingDto listingDto) {
    return listingService.createListing(listingDto);
  }

  @GetMapping("/{listingId}/offer")
  public Set<OfferDto> getOffers(@PathVariable UUID listingId) {
    return offerService.getOffersByListingId(listingId);
  }

  @GetMapping("/{listingId}/offer/{offerId}")
  public OfferDto getOfferById(@PathVariable UUID listingId, @PathVariable UUID offerId) {
    return offerService.getOfferById(listingId, offerId);
  }
}
