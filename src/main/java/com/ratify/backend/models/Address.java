package com.ratify.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @NotNull(message = "Street may not be empty")
    @Size(min = 2, max = 256)
    private String street;

    @NotEmpty(message = "Number may not be empty")
    @Size(min = 1, max = 5)
    private String number;

    @NotEmpty(message = "Postal code may not be empty")
    @Field("postal_code")
    @JsonProperty("postal_code")
    @Size(min = 5, max = 6)
    private String postalCode;

    @NotEmpty(message = "City may not be empty")
    @Size(min = 2, max = 256)
    private String city;

    @NotEmpty(message = "Province may not be empty")
    @Size(min = 2, max = 256)
    private String province;

    @NotEmpty(message = "Country may not be empty")
    @Size(min = 2, max = 256)
    private String country;

    @Field("normalized_address")
    @JsonIgnore
    private String normalizedAddress;
}
