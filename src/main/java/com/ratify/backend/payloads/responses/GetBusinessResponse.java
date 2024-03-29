package com.ratify.backend.payloads.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ratify.backend.models.Address;
import com.ratify.backend.models.enums.EBusiness;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetBusinessResponse {
    private String name;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("updated_at")
    private Date updatedAt;

    @Schema(allowableValues = {"NIGHTCLUB", "BAR", "COFFEE_SHOP", "RESTAURANT", "STREET_FOOD"})
    @JsonProperty("business_type")
    private List<EBusiness> businessType;

    private List<Address> addresses;

    private GetBusinessOwnerResponse owner;
}
