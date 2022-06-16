package com.ratify.backend.models;

import com.ratify.backend.models.enums.ERole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "roles")
public class Role {
    @Id
    private String id;

    @Schema(allowableValues = {"ROLE_USER", "ROLE_MODERATOR", "ROLE_ADMIN"})
    private ERole name;

    public Role(ERole name) {
        this.name = name;
    }
}
