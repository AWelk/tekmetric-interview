package com.interview.controller;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

import com.interview.model.dto.request.ListingCreationDto;
import com.interview.model.dto.request.OfferCreationDto;
import com.interview.model.dto.response.ListingDto;
import com.interview.model.dto.response.OfferDto;
import com.interview.service.ListingService;
import com.interview.service.OfferService;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ListingControllerTest {

  private ListingController controller;

  @Mock private ListingService listingService;
  @Mock private OfferService offerService;

  @BeforeEach
  void setUp() {
    controller = new ListingController(listingService, offerService);
  }

  @Test
  void getAllListings_defersToListingService() {
    final boolean includeOffers = true;
    final Set<ListingDto> listings = Set.of(new ListingDto());

    when(listingService.getAllListings(includeOffers)).thenReturn(listings);

    final Set<ListingDto> result = controller.getAllListings(includeOffers);

    verify(listingService).getAllListings(includeOffers);
    assertSame(listings, result);
  }

  @Test
  void getListing_defersToListingService() {
    final UUID listingId = UUID.randomUUID();
    final boolean includeOffers = true;
    final ListingDto listingDto = new ListingDto();

    when(listingService.getListingById(listingId, includeOffers)).thenReturn(listingDto);

    final ListingDto result = controller.getListing(listingId, includeOffers);

    verify(listingService).getListingById(listingId, includeOffers);
    assertSame(listingDto, result);
  }

  @Test
  void createListing_defersToListingService() {
    final ListingCreationDto dto = new ListingCreationDto();
    final ListingDto listingDto = new ListingDto();

    when(listingService.createListing(dto)).thenReturn(listingDto);

    final ListingDto result = controller.createListing(dto);

    verify(listingService).createListing(dto);
    assertSame(listingDto, result);
  }

  @Test
  void putListing_defersToListingService() {
    final UUID listingId = UUID.randomUUID();
    final ListingCreationDto dto = new ListingCreationDto();
    final ListingDto listingDto = new ListingDto();

    when(listingService.putListing(listingId, dto)).thenReturn(listingDto);

    final ListingDto result = controller.putListing(listingId, dto);

    verify(listingService).putListing(listingId, dto);
    assertSame(listingDto, result);
  }

  @Test
  void getOffers_defersToOfferService() {
    final UUID listingId = UUID.randomUUID();
    final Set<OfferDto> offers = Set.of(new OfferDto());

    when(offerService.getOffersByListingId(listingId)).thenReturn(offers);

    final Set<OfferDto> result = controller.getOffers(listingId);

    verify(offerService).getOffersByListingId(listingId);
    assertSame(offers, result);
  }

  @Test
  void getOfferById_defersToOfferService() {
    final UUID listingId = UUID.randomUUID();
    final UUID offerId = UUID.randomUUID();
    final OfferDto offerDto = new OfferDto();

    when(offerService.getOfferById(listingId, offerId)).thenReturn(offerDto);

    final OfferDto result = controller.getOfferById(listingId, offerId);

    verify(offerService).getOfferById(listingId, offerId);
    assertSame(offerDto, result);
  }

  @Test
  void createOffer_defersToOfferService() {
    final UUID listingId = UUID.randomUUID();
    final OfferCreationDto creationDto = new OfferCreationDto();
    final OfferDto offerDto = new OfferDto();

    when(offerService.createOffer(listingId, creationDto)).thenReturn(offerDto);

    final OfferDto result = controller.createOffer(listingId, creationDto);

    verify(offerService).createOffer(listingId, creationDto);
    assertSame(offerDto, result);
  }

  @Test
  void putOffer_defersToOfferService() {
    final UUID listingId = UUID.randomUUID();
    final UUID offerId = UUID.randomUUID();
    final OfferCreationDto creationDto = new OfferCreationDto();
    final OfferDto offerDto = new OfferDto();

    when(offerService.putOffer(listingId, offerId, creationDto)).thenReturn(offerDto);

    final OfferDto result = controller.putOffer(listingId, offerId, creationDto);

    verify(offerService).putOffer(listingId, offerId, creationDto);
    assertSame(offerDto, result);
  }

  @Test
  void updateListing_defersToListingService() {
    final UUID listingId = UUID.randomUUID();
    final ListingCreationDto creationDto = new ListingCreationDto();
    final ListingDto listingDto = new ListingDto();

    when(listingService.updateListing(listingId, creationDto)).thenReturn(listingDto);

    final ListingDto result = controller.updateListing(listingId, creationDto);

    verify(listingService).updateListing(listingId, creationDto);
    assertSame(listingDto, result);
  }

  @Test
  void updateOffer_defersToOfferService() {
    final UUID listingId = UUID.randomUUID();
    final UUID offerId = UUID.randomUUID();
    final OfferCreationDto creationDto = new OfferCreationDto();
    final OfferDto offerDto = new OfferDto();

    when(offerService.updateOffer(listingId, offerId, creationDto)).thenReturn(offerDto);

    final OfferDto result = controller.updateOffer(listingId, offerId, creationDto);

    verify(offerService).updateOffer(listingId, offerId, creationDto);
    assertSame(offerDto, result);
  }

  @Test
  void deleteListing_defersToListingService() {
    final UUID listingId = UUID.randomUUID();

    controller.deleteListing(listingId);

    verify(listingService).deleteListing(listingId);
  }
}
