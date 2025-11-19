package com.interview.service;

import com.interview.mapper.ListingMapper;
import com.interview.model.domain.ListingEntity;
import com.interview.model.dto.request.ListingCreationDto;
import com.interview.model.dto.response.ListingDto;
import com.interview.repo.ListingRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class ListingService {

  private final ListingRepository listingRepository;
  private final ListingMapper listingMapper;

  public Set<ListingDto> getAllListings(final boolean includeOffers) {
    final Supplier<List<ListingEntity>> executable;
    final Function<ListingEntity, ListingDto> mapper;

    if (includeOffers) {
      executable = listingRepository::findAllListingsWithOffers;
      mapper = listingMapper::listingEntityToListingDtoPlusOffers;
    } else {
      executable = listingRepository::findAll;
      mapper = listingMapper::listingEntityToListingDto;
    }

    return executable.get().stream().map(mapper).collect(Collectors.toSet());
  }

  public ListingDto getListingById(final UUID id, final boolean includeOffers) {
    final Supplier<Optional<ListingEntity>> executable;
    final Function<ListingEntity, ListingDto> mapper;

    if (includeOffers) {
      executable = () -> listingRepository.findById(id);
      mapper = listingMapper::listingEntityToListingDtoPlusOffers;
    } else {
      executable = () -> listingRepository.findListingByIdWithOffers(id);
      mapper = listingMapper::listingEntityToListingDto;
    }

    return executable.get().map(mapper).orElse(null);
  }

  public ListingDto createListing(final ListingCreationDto listingCreationDto) {
    // TODO validate incomming request
    final ListingEntity listingEntity =
        listingMapper.listingCreationDtoToListingEntity(listingCreationDto);
    return listingMapper.listingEntityToListingDtoPlusOffers(listingRepository.save(listingEntity));
  }

  public ListingDto putListing(final UUID listingId, final ListingCreationDto listingCreationDto) {
    final ListingEntity entityToSave =
        listingRepository
            .findById(listingId)
            .map(
                existingListing ->
                    listingMapper.listingCreationDto_mergeInto_listingEntry(
                        existingListing, listingCreationDto))
            .orElseGet(() -> listingMapper.listingCreationDtoToListingEntity(listingCreationDto));

    return listingMapper.listingEntityToListingDtoPlusOffers(listingRepository.save(entityToSave));
  }
}
