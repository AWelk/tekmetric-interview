package com.interview.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "listings")
@Data
public class Listing {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "listing_id")
    private UUID listingId;

    private String address;

    @Column(name = "agent_name")
    private String agentName;

    @Column(name = "property_type")
    private PropertyType propertyType;

    @Column(name = "listing_price")
    private Long listingPrice;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Offer> offers;
}
