package com.ratify.backend.payloads.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ResetPasswordRequest {
    @JsonProperty("new_password")
    @NotBlank
    @Size(max = 120)
    private String newPassword;

    @JsonProperty("confirm_password")
    @NotBlank
    @Size(max = 120)
    private String confirmPassword;
}
