package com.interview.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.interview.mapper.ListingMapper;
import com.interview.model.domain.ListingEntity;
import com.interview.model.dto.request.ListingCreationDto;
import com.interview.model.dto.response.ListingDto;
import com.interview.repo.ListingRepository;
import java.util.Collections;
import java.util.List;
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
public class ListingServiceTest {

  @Mock private ListingRepository listingRepository;
  @Mock private ListingMapper listingMapper;

  private ListingService listingService;

  @BeforeEach
  void setUp() {
    listingService = new ListingService(listingRepository, listingMapper);
  }

  @Test
  void getAllListings_returnsListingsWithOffers_whenIncludeOffersIsTrue() {
    final boolean includeOffers = true;
    final ListingEntity listingEntity1 = Instancio.create(ListingEntity.class);
    final ListingEntity listingEntity2 = Instancio.create(ListingEntity.class);
    final ListingDto dto1 = Instancio.create(ListingDto.class);
    final ListingDto dto2 = Instancio.create(ListingDto.class);

    when(listingRepository.findAllListingsWithOffers())
        .thenReturn(List.of(listingEntity1, listingEntity2));
    when(listingMapper.listingEntity_to_listingDtoPlusOffers(listingEntity1)).thenReturn(dto1);
    when(listingMapper.listingEntity_to_listingDtoPlusOffers(listingEntity2)).thenReturn(dto2);

    final Set<ListingDto> result = listingService.getAllListings(includeOffers);

    assertEquals(2, result.size());
    assertTrue(result.contains(dto1));
    assertTrue(result.contains(dto2));
    verify(listingRepository).findAllListingsWithOffers();
    verify(listingMapper).listingEntity_to_listingDtoPlusOffers(listingEntity1);
    verify(listingMapper).listingEntity_to_listingDtoPlusOffers(listingEntity2);
    verifyNoMoreInteractions(listingRepository, listingMapper);
  }

  @Test
  void getAllListings_returnsEmpty_whenNoListingFoundAndIncludeOffersIsTrue() {
    final boolean includeOffers = true;

    when(listingRepository.findAllListingsWithOffers()).thenReturn(Collections.emptyList());

    final Set<ListingDto> result = listingService.getAllListings(includeOffers);

    assertTrue(result.isEmpty());
    verify(listingRepository).findAllListingsWithOffers();
    verifyNoMoreInteractions(listingRepository, listingMapper);
  }

  @Test
  void getAllListings_returnsListingsWithoutOffers_whenIncludeOffersIsFalse() {
    final boolean includeOffers = false;
    final ListingEntity listingEntity1 = Instancio.create(ListingEntity.class);
    final ListingEntity listingEntity2 = Instancio.create(ListingEntity.class);
    final ListingDto dto1 = Instancio.create(ListingDto.class);
    final ListingDto dto2 = Instancio.create(ListingDto.class);

    when(listingRepository.findAll()).thenReturn(List.of(listingEntity1, listingEntity2));
    when(listingMapper.listingEntity_to_listingDto(listingEntity1)).thenReturn(dto1);
    when(listingMapper.listingEntity_to_listingDto(listingEntity2)).thenReturn(dto2);

    final Set<ListingDto> result = listingService.getAllListings(includeOffers);

    assertEquals(2, result.size());
    assertTrue(result.contains(dto1));
    assertTrue(result.contains(dto2));
    verify(listingRepository).findAll();
    verify(listingMapper).listingEntity_to_listingDto(listingEntity1);
    verify(listingMapper).listingEntity_to_listingDto(listingEntity2);
    verifyNoMoreInteractions(listingRepository, listingMapper);
  }

  @Test
  void getAllListings_returnsEmpty_whenNoListingFoundAndIncludeOffersIsFalse() {
    final boolean includeOffers = false;

    when(listingRepository.findAll()).thenReturn(Collections.emptyList());

    final Set<ListingDto> result = listingService.getAllListings(includeOffers);

    assertTrue(result.isEmpty());
    verify(listingRepository).findAll();
    verifyNoMoreInteractions(listingRepository, listingMapper);
  }

  @Test
  void getListingById_returnsListing_whenPresentAndIncludeOffersIsTrue() {
    final boolean includeOffers = true;
    final UUID listingId = UUID.randomUUID();
    final ListingEntity listingEntity = Instancio.create(ListingEntity.class);
    final ListingDto dto = Instancio.create(ListingDto.class);

    when(listingRepository.findListingByIdWithOffers(listingId))
        .thenReturn(Optional.of(listingEntity));
    when(listingMapper.listingEntity_to_listingDtoPlusOffers(listingEntity)).thenReturn(dto);

    final ListingDto result = listingService.getListingById(listingId, includeOffers);

    assertSame(dto, result);
    verify(listingRepository).findListingByIdWithOffers(listingId);
    verify(listingMapper).listingEntity_to_listingDtoPlusOffers(listingEntity);
    verifyNoMoreInteractions(listingRepository, listingMapper);
  }

  @Test
  void getListingById_returnsNull_whenNotPresentAndIncludeOffersIsTrue() {
    final boolean includeOffers = true;
    final UUID listingId = UUID.randomUUID();

    when(listingRepository.findListingByIdWithOffers(listingId)).thenReturn(Optional.empty());

    final ListingDto result = listingService.getListingById(listingId, includeOffers);

    assertNull(result);
    verify(listingRepository).findListingByIdWithOffers(listingId);
    verifyNoMoreInteractions(listingRepository, listingMapper);
  }

  @Test
  void getListingById_returnsListing_whenPresentAndIncludeOffersIsFalse() {
    final boolean includeOffers = false;
    final UUID listingId = UUID.randomUUID();
    final ListingEntity listingEntity = Instancio.create(ListingEntity.class);
    final ListingDto dto = Instancio.create(ListingDto.class);

    when(listingRepository.findById(listingId)).thenReturn(Optional.of(listingEntity));
    when(listingMapper.listingEntity_to_listingDto(listingEntity)).thenReturn(dto);

    final ListingDto result = listingService.getListingById(listingId, includeOffers);

    assertSame(dto, result);
    verify(listingRepository).findById(listingId);
    verify(listingMapper).listingEntity_to_listingDto(listingEntity);
    verifyNoMoreInteractions(listingRepository, listingMapper);
  }

  @Test
  void getListingById_returnsNull_whenNotPresentAndIncludeOffersIsFalse() {
    final boolean includeOffers = false;
    final UUID listingId = UUID.randomUUID();

    when(listingRepository.findById(listingId)).thenReturn(Optional.empty());

    final ListingDto result = listingService.getListingById(listingId, includeOffers);

    assertNull(result);
    verify(listingRepository).findById(listingId);
    verifyNoMoreInteractions(listingRepository, listingMapper);
  }

  @Test
  void createListing_savesCreationRequest() {
    final ListingCreationDto creationDto = Instancio.create(ListingCreationDto.class);
    final ListingEntity listingEntity = Instancio.create(ListingEntity.class);
    final ListingEntity savedListingEntity = Instancio.create(ListingEntity.class);
    final ListingDto dto = Instancio.create(ListingDto.class);

    when(listingMapper.listingCreationD_to_listingEntity(creationDto)).thenReturn(listingEntity);
    when(listingRepository.save(listingEntity)).thenReturn(savedListingEntity);
    when(listingMapper.listingEntity_to_listingDtoPlusOffers(savedListingEntity)).thenReturn(dto);

    final ListingDto result = listingService.createListing(creationDto);

    verify(listingMapper).listingCreationD_to_listingEntity(creationDto);
    verify(listingRepository).save(listingEntity);
    verify(listingMapper).listingEntity_to_listingDtoPlusOffers(savedListingEntity);
    assertSame(dto, result);
    verifyNoMoreInteractions(listingRepository, listingMapper);
  }

  @Test
  void putListing_createsNewListing_whenIdNotPresent() {
    final UUID listingId = UUID.randomUUID();
    final ListingCreationDto creationDto = Instancio.create(ListingCreationDto.class);
    final ListingEntity listingEntity = Instancio.create(ListingEntity.class);
    final ListingEntity savedListingEntity = Instancio.create(ListingEntity.class);
    final ListingDto dto = Instancio.create(ListingDto.class);

    when(listingRepository.findById(listingId)).thenReturn(Optional.empty());
    when(listingMapper.listingCreationD_to_listingEntity(creationDto)).thenReturn(listingEntity);
    when(listingRepository.save(listingEntity)).thenReturn(savedListingEntity);
    when(listingMapper.listingEntity_to_listingDtoPlusOffers(savedListingEntity)).thenReturn(dto);

    final ListingDto result = listingService.putListing(listingId, creationDto);

    verify(listingRepository).findById(listingId);
    verify(listingMapper).listingCreationD_to_listingEntity(creationDto);
    verify(listingRepository).save(listingEntity);
    verify(listingMapper).listingEntity_to_listingDtoPlusOffers(savedListingEntity);
    assertSame(dto, result);
    verifyNoMoreInteractions(listingRepository, listingMapper);
  }

  @Test
  void putListing_updatesListing_whenIdIsPresent() {
    final UUID listingId = UUID.randomUUID();
    final ListingCreationDto creationDto = Instancio.create(ListingCreationDto.class);
    final ListingEntity listingEntity = Instancio.create(ListingEntity.class);
    final ListingEntity savedListingEntity = Instancio.create(ListingEntity.class);
    final ListingDto dto = Instancio.create(ListingDto.class);

    when(listingRepository.findById(listingId)).thenReturn(Optional.of(listingEntity));
    when(listingMapper.listingCreationDto_mergeInto_listingEntry(listingEntity, creationDto))
        .thenReturn(listingEntity);
    when(listingRepository.save(listingEntity)).thenReturn(savedListingEntity);
    when(listingMapper.listingEntity_to_listingDtoPlusOffers(savedListingEntity)).thenReturn(dto);

    final ListingDto result = listingService.putListing(listingId, creationDto);

    verify(listingRepository).findById(listingId);
    verify(listingMapper).listingCreationDto_mergeInto_listingEntry(listingEntity, creationDto);
    verify(listingRepository).save(listingEntity);
    verify(listingMapper).listingEntity_to_listingDtoPlusOffers(savedListingEntity);
    assertSame(dto, result);
    verifyNoMoreInteractions(listingRepository, listingMapper);
  }

  @Test
  void updateListing_patchesEntity_whenIdIsPresent() {
    final UUID listingId = UUID.randomUUID();
    final ListingCreationDto creationDto = Instancio.create(ListingCreationDto.class);
    final ListingEntity listingEntity = Instancio.create(ListingEntity.class);
    final ListingDto dto = Instancio.create(ListingDto.class);

    when(listingRepository.findById(listingId)).thenReturn(Optional.of(listingEntity));
    when(listingMapper.listingCreationDto_patchInto_ListingEntity(listingEntity, creationDto))
        .thenReturn(listingEntity);
    when(listingRepository.save(listingEntity)).thenReturn(listingEntity);
    when(listingMapper.listingEntity_to_listingDto(listingEntity)).thenReturn(dto);

    final ListingDto result = listingService.updateListing(listingId, creationDto);

    verify(listingRepository).findById(listingId);
    verify(listingMapper).listingCreationDto_patchInto_ListingEntity(listingEntity, creationDto);
    verify(listingRepository).save(listingEntity);
    verify(listingMapper).listingEntity_to_listingDto(listingEntity);
    assertSame(dto, result);
    verifyNoMoreInteractions(listingRepository, listingMapper);
  }

  @Test
  void updateListing_returnsNull_whenIdIsNotPresent() {
    final UUID listingId = UUID.randomUUID();
    final ListingCreationDto creationDto = Instancio.create(ListingCreationDto.class);

    when(listingRepository.findById(listingId)).thenReturn(Optional.empty());

    final ListingDto result = listingService.updateListing(listingId, creationDto);

    assertNull(result);
  }

  @Test
  void deleteListing_deletesByID() {
      final UUID listingId = UUID.randomUUID();

      listingService.deleteListing(listingId);
      verify(listingRepository).deleteById(listingId);
  }
}
