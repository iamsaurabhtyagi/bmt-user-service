package com.bmt.user.service.view.response;

import lombok.Data;

@Data
public class AddressResponse {
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private Double latitude;
    private Double longitude;
}
