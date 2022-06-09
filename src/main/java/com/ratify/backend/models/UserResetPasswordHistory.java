package com.ratify.backend.models;

import com.ratify.backend.models.enums.EResetPasswordMethod;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@NoArgsConstructor
@Document(collection = "user_reset_password_history")
public class UserResetPasswordHistory {
    @Id
    private String id;

    @DBRef
    private User user;

    @NotBlank
    private EResetPasswordMethod method;

    @Field("created_at")
    private Date createdAt;

    public UserResetPasswordHistory(User user, EResetPasswordMethod method, Date createdAt) {
        this.user = user;
        this.method = method;
        this.createdAt = createdAt;
    }
}
