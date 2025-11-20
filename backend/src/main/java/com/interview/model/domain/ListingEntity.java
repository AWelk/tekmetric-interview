package com.interview.model.domain;

import com.interview.model.common.PropertyType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Entity
@Table(name = "listings")
@Data
public class ListingEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "listing_id")
  private UUID listingId;

  private String address;

  @Column(name = "agent_name")
  private String agentName;

  @Enumerated(EnumType.STRING)
  @Column(name = "property_type")
  private PropertyType propertyType;

  @Column(name = "listing_price")
  private Double listingPrice;

  @OneToMany(mappedBy = "listing", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<OfferEntity> offers;
}
