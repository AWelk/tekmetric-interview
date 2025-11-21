package com.interview.service;

import com.interview.exception.OfferNotFoundException;
import com.interview.mapper.OfferMapper;
import com.interview.model.domain.ListingEntity;
import com.interview.model.domain.OfferEntity;
import com.interview.model.dto.request.OfferCreationDto;
import com.interview.model.dto.response.OfferDto;
import com.interview.repo.OfferRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OfferService {

  private final OfferRepository offerRepository;
  private final OfferMapper offerMapper;

  public List<OfferDto> getOffersByListingId(final UUID listingId) {
    return offerRepository.findByListingId(listingId).stream()
        .map(offerMapper::offerEntity_to_offerDto)
        .collect(Collectors.toList());
  }

    public OfferDto createOfferOnListing(final ListingEntity ListingEntity, final OfferCreationDto offerCreationDto) {
        final OfferEntity offerEntity = offerMapper.offerCreationDto_to_offerEntity(offerCreationDto);
        offerEntity.setListing(ListingEntity);

        return offerMapper.offerEntity_to_offerDto(offerRepository.save(offerEntity));
    }

  public OfferDto getOfferById(final UUID offerId) {
    return offerRepository
        .findById(offerId)
        .map(offerMapper::offerEntity_to_offerDto)
        .orElseThrow(() -> new OfferNotFoundException(offerId));
  }

  public OfferDto putOffer(final UUID offerId, final OfferCreationDto offerCreationDto) {
    // TODO handle exception if listing is present
    return offerRepository
        .findById(offerId)
        .map(o -> offerMapper.offerCreationDto_mergeInto_offerEntity(o, offerCreationDto))
        .map(offerRepository::save)
        .map(offerMapper::offerEntity_to_offerDto)
        .orElseThrow(() -> new OfferNotFoundException(offerId));
  }

  public OfferDto updateOffer(final UUID offerId, final OfferCreationDto offerCreationDto) {
    return offerRepository
        .findById(offerId)
        .map(o -> offerMapper.offerCreationDto_patchInto_offerEntity(o, offerCreationDto))
        .map(offerRepository::save)
        .map(offerMapper::offerEntity_to_offerDto)
        .orElseThrow(() -> new OfferNotFoundException(offerId));
  }

  public void deleteOffer(final UUID offerId) {
    offerRepository.deleteById(offerId);
  }
}
