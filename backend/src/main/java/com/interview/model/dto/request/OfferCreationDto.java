package com.interview.model.dto.request;

import com.interview.model.common.Status;
import lombok.Data;

@Data
public class OfferCreationDto {
    private Double offerPrice;
    private String lenderName;
    private Status status;
}
