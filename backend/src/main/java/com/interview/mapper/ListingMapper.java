package com.interview.mapper;

import com.interview.model.domain.ListingEntity;
import com.interview.model.dto.request.ListingCreationDto;
import com.interview.model.dto.response.ListingDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = OfferMapper.class)
public interface ListingMapper {

  ListingDto listingEntity_to_listingDtoPlusOffers(ListingEntity listingEntity);

  @Mapping(target = "offers", ignore = true)
  ListingDto listingEntity_to_listingDto(ListingEntity listingEntity);

  ListingEntity listingCreationD_to_listingEntity(ListingCreationDto listingCreationDto);

  ListingEntity listingCreationDto_mergeInto_listingEntry(
      @MappingTarget ListingEntity listingEntity, ListingCreationDto listingCreationDto);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  ListingEntity listingCreationDto_patchInto_ListingEntity(
      @MappingTarget ListingEntity listingEntity, ListingCreationDto listingCreationDto);
}
