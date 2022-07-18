package com.ratify.backend.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ReviewRate extends Rate {
    private Rate rate;

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

    public ReviewRate(Rate rate,
                      Boolean recommended,
                      Boolean businessOutlook,
                      String pros,
                      String cons) {
        this.rate = rate;
        this.recommended = recommended;
        this.businessOutlook = businessOutlook;
        this.pros = pros;
        this.cons = cons;
    }
}
