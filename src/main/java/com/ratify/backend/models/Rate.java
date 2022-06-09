package com.ratify.backend.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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

    @Size(min = 8, max = 1024)
    private String comment;

    @NotBlank
    private Integer estimate;

    public Rate(String username, String businessNormalizedName, String comment, Integer estimate) {
        this.username = username;
        this.businessNormalizedName = businessNormalizedName;
        this.comment = comment;
        this.estimate = estimate;
    }
}
