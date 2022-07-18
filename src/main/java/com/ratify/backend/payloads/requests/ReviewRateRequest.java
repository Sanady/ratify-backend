package com.ratify.backend.payloads.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ReviewRateRequest {
    @NotBlank
    private String username;

    @JsonProperty("business_name")
    @NotBlank
    private String businessName;

    @NotBlank
    private Integer estimate;

    @NotBlank
    private Boolean recommended;

    @NotBlank
    @JsonProperty("business_outlook")
    private Boolean businessOutlook;

    @NotBlank
    @Size(min = 16, max = 2048)
    private String pros;

    @NotBlank
    @Size(min = 16, max = 2048)
    private String cons;
}
