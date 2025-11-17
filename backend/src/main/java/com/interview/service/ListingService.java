package com.interview.service;

import com.interview.repo.ListingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ListingService {

    private final ListingRepository listingRepository;

    public void getAllListings() {
        listingRepository.findAll();
    }
}
