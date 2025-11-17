package com.interview.repo;

import com.interview.model.Listing;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListingRepository extends ReactiveCrudRepository<Listing, Long> {}
