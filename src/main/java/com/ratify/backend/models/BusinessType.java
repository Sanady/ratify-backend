package com.ratify.backend.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@Document(collection = "business_types")
public class BusinessType {
    @Id
    private String id;

    @NotBlank
    private String name;

    public BusinessType(String name) {
        this.name = name;
    }
}
