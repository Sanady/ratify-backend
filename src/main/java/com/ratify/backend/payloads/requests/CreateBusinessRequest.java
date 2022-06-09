package com.ratify.backend.payloads.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ratify.backend.models.Address;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CreateBusinessRequest {
    @NotNull
    private String name;

    @Valid
    @JsonProperty("business_type")
    private List<String> businessType;

    @Valid
    private List<Address> addresses;
}
