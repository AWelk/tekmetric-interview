package com.interview.repo;

import com.interview.model.db.OfferEntity;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OfferRepository extends JpaRepository<OfferEntity, UUID> {

  @Query(value = "SELECT o FROM OfferEntity o WHERE o.listing.listingId = :listingId")
  Set<OfferEntity> findByListingId(@Param("listingId") UUID listingId);

  @Query(
      value =
          "SELECT o from OfferEntity o WHERE o.listing.listingId = :listingId and o.offerId = :offerId")
  Optional<OfferEntity> findByListingAndOfferId(UUID listingId, UUID offerId);
}
