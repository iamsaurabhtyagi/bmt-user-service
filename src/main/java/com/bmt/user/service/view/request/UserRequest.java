package com.bmt.user.service.view.request;

import com.common.model.user.UserStatus;
import com.common.model.user.UserType;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRequest {
    private String username;

    @Email(message = "Please provide valid Email.")
    private String email;

    @Pattern(regexp = "\\d{10}", message = "Mobile must be 10-digit number")
    private String mobile;

    @Size(min = 8, max = 50, message = "Please Enter password as per criteria")
    private String password;

    @Size(min = 6, max = 6, message = "PIN must be exactly 6 digits")
    @Pattern(regexp = "\\d+", message = "PIN must be numeric")
    private String pin;

    private UserType userType;
    private UserStatus userStatus;

    @AssertTrue(message = "Provide either email+password or mobile+pin (not both or incomplete).")
    public boolean isValidLoginCombination() {
        boolean hasEmail = email != null && !email.isBlank();
        boolean hasPassword = password != null && !password.isBlank();
        boolean hasMobile = mobile != null && !mobile.isBlank();
        boolean hasPin = pin != null && !pin.isBlank();

        // Valid if email + password is provided and mobile + pin is NOT
        if (hasEmail && hasPassword && !hasMobile && !hasPin) return true;

        // Valid if mobile + pin is provided and email + password is NOT
        if (hasMobile && hasPin && !hasEmail && !hasPassword) return true;

        return false;
    }

    // Conditional validation: Only one of password or pin must be provided
    /*@AssertTrue(message = "Either password or pin must be provided, not both or none.")
    public boolean isOnlyOneProvided() {
        boolean hasPassword = password != null && !password.isBlank();
        boolean hasPin = pin != null && !pin.isBlank();
        return hasPassword ^ hasPin; // XOR: true only if exactly one is true
    }*/

    /*@AssertTrue(message = "Either password or pin must be provided, but not both.")
    public boolean isEitherPasswordOrPinProvided() {
        return (password != null && !password.isBlank() && (pin == null || pin.isBlank())) ||
                (pin != null && !pin.isBlank() && (password == null || password.isBlank()));
    }*/
}
