package com.interview.model.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.interview.model.common.Status;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "offers")
@Getter
@Setter
public class OfferEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "offer_id", nullable = false)
  private UUID offerId;

  @Column(name = "offer_price", nullable = false)
  private Double offerPrice;

  @Column(name = "lender_name", nullable = false)
  private String lenderName;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private Status status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "listing_id", nullable = false)
  @JsonBackReference
  private ListingEntity listing;
}
