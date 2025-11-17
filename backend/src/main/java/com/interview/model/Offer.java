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
  private Long offerPrice;

  private String lenderName;
}
