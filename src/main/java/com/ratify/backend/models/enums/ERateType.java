package com.ratify.backend.models.enums;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum ERateType {
    REVIEWS("reviews"),
    SALARIES("salaries"),
    INTERVIEWS("interviews"),
    BENEFITS("benefits");

    private String type;

    ERateType(String type) {
        this.type = type;
    }
}
