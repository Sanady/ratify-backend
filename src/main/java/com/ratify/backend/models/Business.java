package com.ratify.backend.models;

import com.ratify.backend.models.enums.EBusiness;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "businesses")
public class Business {
    @Id
    private String id;

    @NotBlank
    @Size(max = 64)
    private String name;

    private String normalizedName;

    @Field("created_at")
    private Date createdAt;

    @Field("updated_at")
    private Date updatedAt;

    private Boolean active;

    @Field("normalized_username")
    private String normalizedUsername;

    @Schema(allowableValues = {"NIGHTCLUB", "BAR", "COFFEE_SHOP", "RESTAURANT", "STREET_FOOD"})
    @Field("business_type")
    private List<EBusiness> businessType;

    @Valid
    private List<Address> addresses;

    public Business(String name, String normalizedName, String normalizedUsername, List<EBusiness> businessType, List<Address> addresses, Date createdAt, Boolean active) {
        this.name = name;
        this.normalizedName = normalizedName;
        this.normalizedUsername = normalizedUsername;
        this.businessType = businessType;
        this.addresses = addresses;
        this.createdAt = createdAt;
        this.updatedAt = createdAt;
        this.active = active;
    }

    public Business(String name, String normalizedUsername, List<Address> addresses, Date updatedAt, Boolean active) {
        this.name = name;
        this.normalizedUsername = normalizedUsername;
        this.addresses = addresses;
        this.updatedAt = updatedAt;
        this.active = active;
    }
}
