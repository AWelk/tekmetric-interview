package com.interview.repo;

import com.interview.model.domain.ListingEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ListingRepository extends JpaRepository<ListingEntity, UUID> {

  @Query(value = "SELECT l FROM ListingEntity l LEFT JOIN FETCH l.offers")
  List<ListingEntity> findAllListingsWithOffers();

  @Query(
      value =
          "SELECT l FROM ListingEntity l LEFT JOIN FETCH l.offers where l.listingId = :listingId")
  Optional<ListingEntity> findListingByIdWithOffers(@Param("listingId") UUID listingId);
}
