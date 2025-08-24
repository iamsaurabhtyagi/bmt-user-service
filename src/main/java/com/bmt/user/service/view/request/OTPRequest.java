package com.bmt.user.service.view.request;

import com.bmt.model.user.OtpPurpose;
import jakarta.validation.constraints.AssertTrue;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OTPRequest {
    private String otp;
    private String phone;
    private String email;
    private OtpPurpose otpPurpose;

    @AssertTrue(message = "Either phone or email must be provided, but not both or none.")
    public boolean isOnlyOneProvided() {
        boolean hasPhone = phone != null && !phone.isBlank();
        boolean hasEmail = email != null && !email.isBlank();
        return hasPhone ^ hasEmail; // XOR: true if exactly one is true
    }
}
