package com.ratify.backend.payloads.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class BenefitRateRequest {
    @NotBlank
    private String username;

    @JsonProperty("business_name")
    @NotBlank
    private String businessName;

    @Size(min = 32, max = 1024)
    private String comment;

    @NotBlank
    private Integer estimate;
}
