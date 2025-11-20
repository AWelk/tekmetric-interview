package com.interview.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.interview.model.common.Status;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.Data;

@Entity
@Table(name = "offers")
@Data
public class OfferEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "offer_id")
  private UUID offerId;

  @Column(name = "offer_price")
  private Double offerPrice;

  @Column(name = "lender_name")
  private String lenderName;

  @Enumerated(EnumType.STRING)
  private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "listing_id", nullable = false)
    @JsonIgnore
//  @Column(name = "listing_id")
  private ListingEntity listing;
}
