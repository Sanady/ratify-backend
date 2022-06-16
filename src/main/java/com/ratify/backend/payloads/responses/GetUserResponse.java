package com.ratify.backend.payloads.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserResponse {
    private String username;

    private String email;

    @JsonProperty("created_at")
    private Date createAt;

    @Schema(allowableValues = {"ROLE_USER", "ROLE_MODERATOR", "ROLE_ADMIN"})
    private List<String> roles;
}
