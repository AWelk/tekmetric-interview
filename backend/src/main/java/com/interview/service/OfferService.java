package com.interview.service;

import com.interview.mapper.OfferMapper;
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
    if (!listingRepository.existsById(listingId)) {
      throw new RuntimeException("AHHHH");
    }

    final OfferEntity entity = offerMapper.offerCreationDto_to_offerEntity(offerCreationDto);
    entity.setListingId(listingId);
    return offerMapper.offerEntity_to_offerDto(offerRepository.save(entity));
  }

  public OfferDto putOffer(
      final UUID listingId, final UUID offerId, final OfferCreationDto offerCreationDto) {
    if (!listingRepository.existsById(listingId)) {
      throw new RuntimeException("AHHHH");
    }

    final OfferEntity entityToSave =
        offerRepository
            .findById(offerId)
            .map(
                existingOffer ->
                    offerMapper.offerCreationDto_mergeInto_offerEntity(
                        existingOffer, offerCreationDto))
            .orElseGet(
                () -> {
                  final OfferEntity constructedEntity =
                      offerMapper.offerCreationDto_to_offerEntity(offerCreationDto);
                  constructedEntity.setListingId(listingId);
                  return constructedEntity;
                });
    return offerMapper.offerEntity_to_offerDto(offerRepository.save(entityToSave));
  }
}
