package com.interview.service;

import com.interview.mapper.OfferMapper;
import com.interview.model.domain.ListingEntity;
import com.interview.model.domain.OfferEntity;
import com.interview.model.dto.request.OfferCreationDto;
import com.interview.model.dto.response.OfferDto;
import com.interview.repo.ListingRepository;
import com.interview.repo.OfferRepository;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OfferService {

  private final OfferRepository offerRepository;
  private final ListingRepository listingRepository;
  private final OfferMapper offerMapper;

  public Set<OfferDto> getOffersByListingId(final UUID listingId) {
    return offerRepository.findByListingId(listingId).stream()
        .map(offerMapper::offerEntity_to_offerDto)
        .collect(Collectors.toSet());
  }

  public OfferDto getOfferById(final UUID listingId, final UUID offerId) {
    return offerRepository
        .findByListingAndOfferId(listingId, offerId)
        .map(offerMapper::offerEntity_to_offerDto)
        .orElse(null);
  }

  public OfferDto createOffer(final UUID listingId, final OfferCreationDto offerCreationDto) {
    // TODO handle exception if listing is present
    final ListingEntity listing = listingRepository.getReferenceById(listingId);

    final OfferEntity entityToSave = offerMapper.offerCreationDto_to_offerEntity(offerCreationDto);
    entityToSave.setListing(listing);
    return offerMapper.offerEntity_to_offerDto(offerRepository.save(entityToSave));
  }

  public OfferDto putOffer(
      final UUID listingId, final UUID offerId, final OfferCreationDto offerCreationDto) {
    // TODO handle exception if listing is present
    final ListingEntity listing = listingRepository.getReferenceById(listingId);

    final OfferEntity entityToSave =
        offerRepository
            .findById(offerId)
            .map(o -> offerMapper.offerCreationDto_mergeInto_offerEntity(o, offerCreationDto))
            .orElseGet(
                () -> {
                  final OfferEntity entity =
                      offerMapper.offerCreationDto_to_offerEntity(offerCreationDto);
                  entity.setOfferId(offerId);
                  return entity;
                });

    entityToSave.setListing(listing);
    return offerMapper.offerEntity_to_offerDto(offerRepository.save(entityToSave));
  }

  public OfferDto updateOffer(
      final UUID listingId, final UUID offerId, final OfferCreationDto offerCreationDto) {
    return offerRepository
        .findByListingAndOfferId(listingId, offerId)
        .map(o -> offerMapper.offerCreationDto_patchInto_offerEntity(o, offerCreationDto))
        .map(offerRepository::save)
        .map(offerMapper::offerEntity_to_offerDto)
        .orElse(null);
  }
}
