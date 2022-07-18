package com.ratify.backend.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@Document(collection = "business_rates")
public class Rate {
    @Id
    private String id;

    @NotBlank
    private String username;

    @JsonProperty("business_normalized_name")
    private String businessNormalizedName;

    @NotBlank
    private Integer estimate;

    @NotBlank
    private String type;

    public Rate(String username,
                String businessNormalizedName,
                Integer estimate,
                String type) {
        this.username = username;
        this.businessNormalizedName = businessNormalizedName;
        this.estimate = estimate;
        this.type = type;
    }
}
