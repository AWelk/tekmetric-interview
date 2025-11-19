package com.interview.mapper;

import com.interview.model.domain.OfferEntity;
import com.interview.model.dto.request.OfferCreationDto;
import com.interview.model.dto.response.OfferDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OfferMapper {

    OfferDto offerEntity_to_offerDto(OfferEntity offerEntity);

    OfferEntity offerCreationDto_to_offerEntity(OfferCreationDto offerCreationDto);

    OfferEntity offerCreationDto_mergeInto_offerEntity(@MappingTarget OfferEntity offerEntity, OfferCreationDto offerCreationDto);
}
