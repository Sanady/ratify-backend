package com.ratify.backend.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class BenefitRate extends Rate {
    private Rate rate;

    @NotBlank
    @Size(min = 32, max = 1024)
    private String comment;

    public BenefitRate(Rate rate,
                       String comment) {
        this.rate = rate;
        this.comment = comment;
    }
}
