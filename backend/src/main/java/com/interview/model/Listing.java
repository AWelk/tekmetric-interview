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

    @Enumerated(EnumType.STRING)
    @Column(name = "property_type")
    private PropertyType propertyType;

    @Column(name = "listing_price")
    private Double listingPrice;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "listing")
    private List<Offer> offers;
}
