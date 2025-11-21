package com.interview.controller;

import com.interview.model.dto.request.OfferCreationDto;
import com.interview.model.dto.response.OfferDto;
import com.interview.service.OfferService;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(path = "offer", produces = "application/json")
public class OfferController {

  private final OfferService offerService;

  @GetMapping("/{offerId}")
  public OfferDto getOfferById(@PathVariable UUID offerId) {
    return offerService.getOfferById(offerId);
  }

  @PutMapping("/{offerId}")
  public OfferDto putOffer(
      @PathVariable UUID offerId, @RequestBody OfferCreationDto offerCreationDto) {
    return offerService.putOffer(offerId, offerCreationDto);
  }

  @PatchMapping("/{offerId}")
  public OfferDto updateOffer(
      @PathVariable UUID offerId, @RequestBody OfferCreationDto offerCreationDto) {
    return offerService.updateOffer(offerId, offerCreationDto);
  }

  @DeleteMapping("/{offerId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteOffer(@PathVariable UUID offerId) {
    offerService.deleteOffer(offerId);
  }
}
