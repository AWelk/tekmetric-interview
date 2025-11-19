package com.interview.model.dto.request;

import com.interview.model.common.PropertyType;
import lombok.Data;

@Data
public class ListingCreationDto {
  private String address;
  private String agentName;
  private PropertyType propertyType;
  private Double listingPrice;
}
