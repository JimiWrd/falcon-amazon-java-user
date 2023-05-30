package com.jumar.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddAddressDto {
    @NotNull(message = "House Name/Number cannot be empty.")
    private String houseNameNumber;
    @NotNull(message = "Street cannot be empty.")
    private String street;
    private String addressLine2;
    private String addressLine3;
    @NotNull(message = "City cannot be empty.")
    private String city;
    @NotNull(message = "Postcode cannot be empty.")
    private String postcode;
    private boolean isBusiness;
}
