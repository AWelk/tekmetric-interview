package com.interview.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "offers")
public class Offer {

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
  private Listing listing;
}
