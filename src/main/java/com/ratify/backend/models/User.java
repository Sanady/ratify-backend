package com.ratify.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    private String id;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @Field("created_at")
    private Date createdAt;

    @Field("updated_at")
    private Date updatedAt;

    @Field("normalized_username")
    @JsonIgnore
    private String normalizedUsername;

    @Schema(allowableValues = {"ROLE_USER", "ROLE_MODERATOR", "ROLE_ADMIN"})
    private Set<Role> roles = new HashSet<>();

    public User(String username, String email, String password, String normalizedUsername, Date createdAt, Date updatedAt) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.normalizedUsername = normalizedUsername;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
