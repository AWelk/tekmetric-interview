package com.interview.controller;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

import com.interview.model.dto.request.ListingCreationDto;
import com.interview.model.dto.request.OfferCreationDto;
import com.interview.model.dto.response.ListingDto;
import com.interview.model.dto.response.OfferDto;
import com.interview.service.ListingService;
import com.interview.service.OfferService;
import java.util.List;
import java.util.UUID;

import org.instancio.Instancio;
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
    final List<ListingDto> listings = List.of(new ListingDto());

    when(listingService.getAllListings()).thenReturn(listings);

    final List<ListingDto> result = controller.getAllListings();

    verify(listingService).getAllListings();
    assertSame(listings, result);
  }

  @Test
  void getListing_defersToListingService() {
    final UUID listingId = UUID.randomUUID();
    final ListingDto listingDto = new ListingDto();

    when(listingService.getListingById(listingId)).thenReturn(listingDto);

    final ListingDto result = controller.getListing(listingId);

    verify(listingService).getListingById(listingId);
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
  void getOffers_defersToListingService() {
    final UUID listingId = UUID.randomUUID();
    final List<OfferDto> offers = Instancio.createList(OfferDto.class);

    when(listingService.getOffersByListingId(listingId)).thenReturn(offers);

    final List<OfferDto> result = controller.getOffers(listingId);

    verify(listingService).getOffersByListingId(listingId);
    assertSame(offers, result);
  }

  @Test
  void createOffer_defersToOfferService() {
    final UUID listingId = UUID.randomUUID();
    final OfferCreationDto creationDto = new OfferCreationDto();
    final OfferDto offerDto = new OfferDto();

    when(listingService.createOfferOnListing(listingId, creationDto)).thenReturn(offerDto);

    final OfferDto result = controller.createOffer(listingId, creationDto);

    verify(listingService).createOfferOnListing(listingId, creationDto);
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
  void deleteListing_defersToListingService() {
    final UUID listingId = UUID.randomUUID();

    controller.deleteListing(listingId);

    verify(listingService).deleteListing(listingId);
  }
}
