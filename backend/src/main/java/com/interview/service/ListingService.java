package com.interview.service;

import com.interview.exception.ListingNotFoundException;
import com.interview.mapper.ListingMapper;
import com.interview.model.domain.ListingEntity;
import com.interview.model.dto.request.ListingCreationDto;
import com.interview.model.dto.request.OfferCreationDto;
import com.interview.model.dto.response.ListingDto;
import com.interview.model.dto.response.OfferDto;
import com.interview.repo.ListingRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class ListingService {

  private final OfferService offerService;
  private final ListingRepository listingRepository;
  private final ListingMapper listingMapper;

  public List<ListingDto> getAllListings() {
    return listingRepository.findAll().stream()
        .map(listingMapper::listingEntity_to_listingDto)
        .collect(Collectors.toList());
  }

  public ListingDto getListingById(final UUID listingId) {
    return listingRepository
        .findById(listingId)
        .map(listingMapper::listingEntity_to_listingDto)
        .orElseThrow(() -> new ListingNotFoundException(listingId));
  }

  public ListingDto createListing(final ListingCreationDto listingCreationDto) {
    // TODO validate incomming request
    final ListingEntity listingEntity =
        listingMapper.listingCreationD_to_listingEntity(listingCreationDto);
    return listingMapper.listingEntity_to_listingDto(listingRepository.save(listingEntity));
  }

  public ListingDto putListing(final UUID listingId, final ListingCreationDto listingCreationDto) {
    return listingRepository
        .findById(listingId)
        .map(
            existingListing ->
                listingMapper.listingCreationDto_mergeInto_listingEntry(
                    existingListing, listingCreationDto))
        .map(listingRepository::save)
        .map(listingMapper::listingEntity_to_listingDto)
        .orElseThrow(() -> new ListingNotFoundException(listingId));
  }

  public ListingDto updateListing(
      final UUID listingId, final ListingCreationDto listingCreationDto) {
    return listingRepository
        .findById(listingId)
        .map(l -> listingMapper.listingCreationDto_patchInto_ListingEntity(l, listingCreationDto))
        .map(listingRepository::save)
        .map(listingMapper::listingEntity_to_listingDto)
        .orElseThrow(() -> new ListingNotFoundException(listingId));
  }

  public OfferDto createOfferOnListing(
      final UUID listingId, final OfferCreationDto offerCreationDto) {
    // TODO handle reference being null
    final ListingEntity listingEntity = listingRepository.getReferenceById(listingId);

    return offerService.createOfferOnListing(listingEntity, offerCreationDto);
  }

  public List<OfferDto> getOffersByListingId(final UUID listingId) {
    final ListingEntity listingEntity =
        listingRepository
            .findById(listingId)
            .orElseThrow(() -> new ListingNotFoundException(listingId));

    return offerService.getOffersByListingId(listingEntity.getListingId());
  }

  public void deleteListing(final UUID listingId) {
    listingRepository.deleteById(listingId);
  }
}
