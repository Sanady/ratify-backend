package com.ratify.backend.payloads.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChangePasswordRequest {
    @JsonProperty("current_password")
    public String currentPassword;

    @JsonProperty("new_password")
    public String newPassword;

    @JsonProperty("confirm_password")
    public String confirmPassword;
}
