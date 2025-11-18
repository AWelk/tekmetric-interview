package com.interview.mapper;

import com.interview.model.db.ListingEntity;
import com.interview.model.dto.ListingDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = OfferMapper.class)
public interface ListingMapper {

  ListingDto listingEntityToListingDtoPlusOffers(ListingEntity listingEntity);

  @Mapping(target = "offers", ignore = true)
  ListingDto listingEntityToListingDto(ListingEntity listingEntity);

  ListingEntity listingDtoToListingEntity(ListingDto listingDto);
}
