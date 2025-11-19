package com.interview.mapper;

import com.interview.model.domain.ListingEntity;
import com.interview.model.dto.request.ListingCreationDto;
import com.interview.model.dto.response.ListingDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = OfferMapper.class)
public interface ListingMapper {

  ListingDto listingEntityToListingDtoPlusOffers(ListingEntity listingEntity);

  @Mapping(target = "offers", ignore = true)
  ListingDto listingEntityToListingDto(ListingEntity listingEntity);

  ListingEntity listingDtoToListingEntity(ListingDto listingDto);

  ListingEntity listingCreationDtoToListingEntity(ListingCreationDto listingCreationDto);

  ListingEntity listingCreationDto_mergeInto_listingEntry(
      @MappingTarget ListingEntity listingEntity, ListingCreationDto listingCreationDto);
}
