package com.interview.repo;

import com.interview.model.Listing;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface ListingRepository extends CrudRepository<Listing, UUID> {}
