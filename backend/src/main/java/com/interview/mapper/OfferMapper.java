package com.interview.mapper;

import com.interview.model.db.OfferEntity;
import com.interview.model.dto.OfferDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OfferMapper {

//    ListingMapper INSTANCE = Mappers.getMapper(ListingMapper.class);

    OfferDto offerEntityToOfferDto(OfferEntity offerEntity);
}
