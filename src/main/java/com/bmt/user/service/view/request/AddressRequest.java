package com.bmt.user.service.view.request;

import lombok.Data;

import java.util.UUID;

@Data
public class AddressRequest {
    private UUID userId;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private Double latitude;
    private Double longitude;
}
