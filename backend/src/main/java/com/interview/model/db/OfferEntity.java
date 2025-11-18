package com.interview.model.db;

import com.interview.model.common.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

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
  private ListingEntity listing;
}
