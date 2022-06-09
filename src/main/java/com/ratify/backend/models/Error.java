package com.ratify.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class Error {
    private Integer status;
    private List<String> errors;
    private Date timestamp;

    public Error(List<String> errors, Date timestamp) {
        this.errors = errors;
        this.timestamp = timestamp;
    }
}
