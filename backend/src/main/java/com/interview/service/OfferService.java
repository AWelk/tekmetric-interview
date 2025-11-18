package com.interview.service;

import com.interview.mapper.OfferMapper;
import com.interview.model.dto.OfferDto;
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
  private final OfferMapper offerMapper;

  public Set<OfferDto> getOffersByListingId(final UUID listingId) {
    return offerRepository.findByListingId(listingId).stream()
        .map(offerMapper::offerEntityToOfferDto)
        .collect(Collectors.toSet());
  }

  public OfferDto getOfferById(final UUID listingId, final UUID offerId) {
    return offerRepository
        .findByListingAndOfferId(listingId, offerId)
        .map(offerMapper::offerEntityToOfferDto)
        .orElse(null);
  }
}
