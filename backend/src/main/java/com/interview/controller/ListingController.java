package com.interview.controller;

import com.interview.model.dto.request.ListingCreationDto;
import com.interview.model.dto.request.OfferCreationDto;
import com.interview.model.dto.response.ListingDto;
import com.interview.model.dto.response.OfferDto;
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
  public ListingDto createListing(@RequestBody ListingCreationDto listingCreationDto) {
    return listingService.createListing(listingCreationDto);
  }

  @PutMapping("/{listingId}")
  public ListingDto putListing(
      @PathVariable UUID listingId, @RequestBody ListingCreationDto listingCreationDto) {
    return listingService.putListing(listingId, listingCreationDto);
  }

  @GetMapping("/{listingId}/offer")
  public Set<OfferDto> getOffers(@PathVariable UUID listingId) {
    return offerService.getOffersByListingId(listingId);
  }

  @GetMapping("/{listingId}/offer/{offerId}")
  public OfferDto getOfferById(@PathVariable UUID listingId, @PathVariable UUID offerId) {
    return offerService.getOfferById(listingId, offerId);
  }

  @PostMapping("/{listingId}/offer")
  public OfferDto createOffer(
      @PathVariable UUID listingId, @RequestBody OfferCreationDto offerCreationDto) {
    return offerService.createOffer(listingId, offerCreationDto);
  }

  @PutMapping("/{listingId}/offer/{offerId}")
  public OfferDto putOffer(
      @PathVariable UUID listingId,
      @PathVariable UUID offerId,
      @RequestBody OfferCreationDto offerCreationDto) {
    return offerService.putOffer(listingId, offerId, offerCreationDto);
  }

  @PatchMapping("/{listingId}")
  public ListingDto updateListing(
      @PathVariable UUID listingId, @RequestBody ListingCreationDto listingCreationDto) {
    return listingService.updateListing(listingId, listingCreationDto);
  }

  @PatchMapping("/{listingId}/offer/{offerId}")
  public OfferDto updateOffer(
      @PathVariable UUID listingId,
      @PathVariable UUID offerId,
      @RequestBody OfferCreationDto offerCreationDto) {
    return offerService.updateOffer(listingId, offerId, offerCreationDto);
  }

  @DeleteMapping("/{listingId}")
    public void deleteListing(@PathVariable UUID listingId) {
      listingService.deleteListing(listingId);
  }
}
