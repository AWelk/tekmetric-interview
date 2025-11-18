package com.interview.model.dto;

import com.interview.model.common.PropertyType;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class ListingDto {
  private UUID listingId;
  private String address;
  private String agentName;
  private PropertyType propertyType;
  private Double listingPrice;
  private List<OfferDto> offers;
}
