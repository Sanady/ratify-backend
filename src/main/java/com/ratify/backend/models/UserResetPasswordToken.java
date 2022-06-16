package com.ratify.backend.models;

import com.ratify.backend.models.enums.EResetPasswordMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@NoArgsConstructor
@Document(collection = "user_reset_password_token")
public class UserResetPasswordToken {
    @Id
    private String id;

    @DBRef
    private User user;

    @NotBlank
    @Max(64)
    private String token;

    @Schema(allowableValues = {"EMAIL"})
    @NotBlank
    private EResetPasswordMethod method;

    @NotBlank
    @Field("used_token")
    private Boolean usedToken;

    @Field("consumed_at")
    private Date consumedAt;

    @Field("created_at")
    private Date createdAt;

    public UserResetPasswordToken(User user, String token, EResetPasswordMethod method, Boolean usedToken, Date createdAt) {
        this.user = user;
        this.token = token;
        this.method = method;
        this.usedToken = usedToken;
        this.createdAt = createdAt;
    }
}
