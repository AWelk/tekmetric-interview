package com.interview.controller;

import com.interview.model.dto.request.ListingCreationDto;
import com.interview.model.dto.request.OfferCreationDto;
import com.interview.model.dto.response.ListingDto;
import com.interview.model.dto.response.OfferDto;
import com.interview.service.ListingService;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path = "listing", produces = "application/json")
public class ListingController {

  private final ListingService listingService;

  @GetMapping
  public List<ListingDto> getAllListings() {
    return listingService.getAllListings();
  }

  @GetMapping("/{listingId}")
  public ListingDto getListing(@PathVariable UUID listingId) {
    return listingService.getListingById(listingId);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ListingDto createListing(@Valid @RequestBody ListingCreationDto listingCreationDto) {
    return listingService.createListing(listingCreationDto);
  }

  @PutMapping("/{listingId}")
  public ListingDto putListing(
      @PathVariable UUID listingId, @Valid @RequestBody ListingCreationDto listingCreationDto) {
    return listingService.putListing(listingId, listingCreationDto);
  }

  @GetMapping("/{listingId}/offer")
  public List<OfferDto> getOffers(@PathVariable UUID listingId) {
    return listingService.getOffersByListingId(listingId);
  }

  @PostMapping("/{listingId}/offer")
  public OfferDto createOffer(
      @PathVariable UUID listingId, @Valid @RequestBody OfferCreationDto offerCreationDto) {
    return listingService.createOfferOnListing(listingId, offerCreationDto);
  }

  @PatchMapping("/{listingId}")
  public ListingDto updateListing(
      @PathVariable UUID listingId, @RequestBody ListingCreationDto listingCreationDto) {
    return listingService.updateListing(listingId, listingCreationDto);
  }

  @DeleteMapping("/{listingId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteListing(@PathVariable UUID listingId) {
    listingService.deleteListing(listingId);
  }
}
