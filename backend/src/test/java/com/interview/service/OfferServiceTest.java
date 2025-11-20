package com.interview.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.interview.mapper.OfferMapper;
import com.interview.model.domain.ListingEntity;
import com.interview.model.domain.OfferEntity;
import com.interview.model.dto.request.OfferCreationDto;
import com.interview.model.dto.response.OfferDto;
import com.interview.repo.ListingRepository;
import com.interview.repo.OfferRepository;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OfferServiceTest {

  @Mock private ListingRepository listingRepository;
  @Mock private OfferRepository offerRepository;
  @Mock private OfferMapper offerMapper;

  private OfferService offerService;

  @BeforeEach
  void setUp() {
    offerService = new OfferService(offerRepository, listingRepository, offerMapper);
  }

  @Test
  void getOffersByListingId_returnsSetOfOffers_whenListingIsPresent() {
    final UUID listingId = UUID.randomUUID();
    final OfferEntity offerEntity1 = Instancio.create(OfferEntity.class);
    final OfferEntity offerEntity2 = Instancio.create(OfferEntity.class);
    final OfferDto offerDto1 = Instancio.create(OfferDto.class);
    final OfferDto offerDto2 = Instancio.create(OfferDto.class);

    when(offerRepository.findByListingId(listingId)).thenReturn(Set.of(offerEntity1, offerEntity2));
    when(offerMapper.offerEntity_to_offerDto(offerEntity1)).thenReturn(offerDto1);
    when(offerMapper.offerEntity_to_offerDto(offerEntity2)).thenReturn(offerDto2);

    final Set<OfferDto> result = offerService.getOffersByListingId(listingId);

    verify(offerRepository).findByListingId(listingId);
    verify(offerMapper).offerEntity_to_offerDto(offerEntity1);
    verify(offerMapper).offerEntity_to_offerDto(offerEntity2);
    assertEquals(2, result.size());
    assertTrue(result.contains(offerDto1));
    assertTrue(result.contains(offerDto2));
    verifyNoMoreInteractions(listingRepository, offerRepository, offerMapper);
  }

  @Test
  void getOffersByListingId_returnsEmptySet_whenListingIsNotPresent() {
    final UUID listingId = UUID.randomUUID();

    when(offerRepository.findByListingId(listingId)).thenReturn(Set.of());

    final Set<OfferDto> result = offerService.getOffersByListingId(listingId);

    verify(offerRepository).findByListingId(listingId);
    assertEquals(0, result.size());
    verifyNoMoreInteractions(listingRepository, offerRepository, offerMapper);
  }

  @Test
  void getOfferById_returnsOffer_whenOfferIsPresent() {
    final UUID listingId = UUID.randomUUID();
    final UUID offerId = UUID.randomUUID();
    final OfferEntity offerEntity = Instancio.create(OfferEntity.class);
    final OfferDto offerDto = Instancio.create(OfferDto.class);

    when(offerRepository.findByListingAndOfferId(listingId, offerId))
        .thenReturn(Optional.of(offerEntity));
    when(offerMapper.offerEntity_to_offerDto(offerEntity)).thenReturn(offerDto);

    final OfferDto result = offerService.getOfferById(listingId, offerId);

    verify(offerRepository).findByListingAndOfferId(listingId, offerId);
    verify(offerMapper).offerEntity_to_offerDto(offerEntity);
    assertSame(offerDto, result);
    verifyNoMoreInteractions(listingRepository, offerRepository, offerMapper);
  }

  @Test
  void getOfferById_returnsNull_whenOfferIsNotPresent() {
    final UUID listingId = UUID.randomUUID();
    final UUID offerId = UUID.randomUUID();

    when(offerRepository.findByListingAndOfferId(listingId, offerId)).thenReturn(Optional.empty());

    final OfferDto result = offerService.getOfferById(listingId, offerId);

    verify(offerRepository).findByListingAndOfferId(listingId, offerId);
    assertNull(result);
    verifyNoMoreInteractions(listingRepository, offerRepository, offerMapper);
  }

  @Test
  void createOffer_createsOffer_whenListingIsPresent() {
    final UUID listingId = UUID.randomUUID();
    final OfferCreationDto creationDto = Instancio.create(OfferCreationDto.class);
    final ListingEntity listingEntity = Instancio.create(ListingEntity.class);
    final OfferEntity offerEntity = spy(Instancio.create(OfferEntity.class));
    final OfferDto offerDto = Instancio.create(OfferDto.class);

    when(listingRepository.getReferenceById(listingId)).thenReturn(listingEntity);
    when(offerMapper.offerCreationDto_to_offerEntity(creationDto)).thenReturn(offerEntity);
    when(offerRepository.save(offerEntity)).thenReturn(offerEntity);
    when(offerMapper.offerEntity_to_offerDto(offerEntity)).thenReturn(offerDto);

    final OfferDto result = offerService.createOffer(listingId, creationDto);

    verify(listingRepository).getReferenceById(listingId);
    verify(offerMapper).offerCreationDto_to_offerEntity(creationDto);
    verify(offerEntity).setListing(listingEntity);
    verify(offerRepository).save(offerEntity);
    verify(offerMapper).offerEntity_to_offerDto(offerEntity);
    assertSame(offerDto, result);
    verifyNoMoreInteractions(listingRepository, offerRepository, offerMapper);
  }

  @Test
  void createOffer_throwsException_whenListingIsNotPresent() {
    // TODO fix this
    final UUID listingId = UUID.randomUUID();
    final OfferCreationDto creationDto = Instancio.create(OfferCreationDto.class);

    when(listingRepository.getReferenceById(listingId)).thenThrow(new RuntimeException());

    assertThrows(RuntimeException.class, () -> offerService.createOffer(listingId, creationDto));
  }

  @Test
  void putOffer_setsOffer_whenListingAndOfferIsPresent() {
    final UUID listingId = UUID.randomUUID();
    final UUID offerId = UUID.randomUUID();
    final OfferCreationDto offerCreationDto = Instancio.create(OfferCreationDto.class);
    final ListingEntity listingEntity = Instancio.create(ListingEntity.class);
    final OfferEntity offerEntity = spy(Instancio.create(OfferEntity.class));
    final OfferDto offerDto = Instancio.create(OfferDto.class);

    when(listingRepository.getReferenceById(listingId)).thenReturn(listingEntity);
    when(offerRepository.findById(offerId)).thenReturn(Optional.of(offerEntity));
    when(offerMapper.offerCreationDto_mergeInto_offerEntity(offerEntity, offerCreationDto))
        .thenReturn(offerEntity);
    when(offerRepository.save(offerEntity)).thenReturn(offerEntity);
    when(offerMapper.offerEntity_to_offerDto(offerEntity)).thenReturn(offerDto);

    final OfferDto result = offerService.putOffer(listingId, offerId, offerCreationDto);

    verify(listingRepository).getReferenceById(listingId);
    verify(offerRepository).findById(offerId);
    verify(offerMapper).offerCreationDto_mergeInto_offerEntity(offerEntity, offerCreationDto);
    verify(offerEntity).setListing(listingEntity);
    verify(offerRepository).save(offerEntity);
    verify(offerMapper).offerEntity_to_offerDto(offerEntity);
    assertSame(offerDto, result);
  }

  @Test
  void putOffer_creatsOffer_whenListingIsPresentAndOfferIsNotPresent() {
    final UUID listingId = UUID.randomUUID();
    final UUID offerId = UUID.randomUUID();
    final OfferCreationDto offerCreationDto = Instancio.create(OfferCreationDto.class);
    final ListingEntity listingEntity = Instancio.create(ListingEntity.class);
    final OfferEntity offerEntity = spy(Instancio.create(OfferEntity.class));
    final OfferDto offerDto = Instancio.create(OfferDto.class);

    when(listingRepository.getReferenceById(listingId)).thenReturn(listingEntity);
    when(offerRepository.findById(offerId)).thenReturn(Optional.empty());
    when(offerMapper.offerCreationDto_to_offerEntity(offerCreationDto)).thenReturn(offerEntity);
    when(offerRepository.save(offerEntity)).thenReturn(offerEntity);
    when(offerMapper.offerEntity_to_offerDto(offerEntity)).thenReturn(offerDto);

    final OfferDto result = offerService.putOffer(listingId, offerId, offerCreationDto);

    verify(listingRepository).getReferenceById(listingId);
    verify(offerRepository).findById(offerId);
    verify(offerMapper).offerCreationDto_to_offerEntity(offerCreationDto);
    verify(offerMapper).offerCreationDto_to_offerEntity(offerCreationDto);
    verify(offerEntity).setOfferId(offerId);
    verify(offerEntity).setListing(listingEntity);
    verify(offerRepository).save(offerEntity);
    verify(offerMapper).offerEntity_to_offerDto(offerEntity);
    assertSame(offerDto, result);
    verifyNoMoreInteractions(listingRepository, offerRepository, offerMapper);
  }

  @Test
  void putOffer_throwsException_whenListingIsNotPresent() {
    // TODO fix this
    final UUID listingId = UUID.randomUUID();
    final UUID offerId = UUID.randomUUID();

    when(listingRepository.getReferenceById(listingId)).thenThrow(new RuntimeException());

    assertThrows(RuntimeException.class, () -> offerService.putOffer(listingId, offerId, null));
  }

  @Test
  void updateOffer_patchesOffer_whenPresent() {
    final UUID listingId = UUID.randomUUID();
    final UUID offerId = UUID.randomUUID();
    final OfferCreationDto offerCreationDto = Instancio.create(OfferCreationDto.class);
    final OfferEntity offerEntity = Instancio.create(OfferEntity.class);
    final OfferDto offerDto = Instancio.create(OfferDto.class);

    when(offerRepository.findByListingAndOfferId(listingId, offerId))
        .thenReturn(Optional.of(offerEntity));
    when(offerMapper.offerCreationDto_patchInto_offerEntity(offerEntity, offerCreationDto))
        .thenReturn(offerEntity);
    when(offerRepository.save(offerEntity)).thenReturn(offerEntity);
    when(offerMapper.offerEntity_to_offerDto(offerEntity)).thenReturn(offerDto);

    final OfferDto result = offerService.updateOffer(listingId, offerId, offerCreationDto);

    verify(offerRepository).findByListingAndOfferId(listingId, offerId);
    verify(offerMapper).offerCreationDto_patchInto_offerEntity(offerEntity, offerCreationDto);
    verify(offerRepository).save(offerEntity);
    verify(offerMapper).offerEntity_to_offerDto(offerEntity);
    assertSame(offerDto, result);
    verifyNoMoreInteractions(listingRepository, offerRepository, offerMapper);
  }

  @Test
  void updateOffer_returnsNull_whenListingIsNotPresent() {
    // TODO fix this
    final UUID listingId = UUID.randomUUID();
    final UUID offerId = UUID.randomUUID();
    final OfferCreationDto offerCreationDto = Instancio.create(OfferCreationDto.class);

    when(offerRepository.findByListingAndOfferId(listingId, offerId)).thenReturn(Optional.empty());

    final OfferDto result = offerService.updateOffer(listingId, offerId, offerCreationDto);

    verify(offerRepository).findByListingAndOfferId(listingId, offerId);
    assertNull(result);
    verifyNoMoreInteractions(listingRepository, offerRepository, offerMapper);
  }
}
