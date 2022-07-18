package com.ratify.backend.constants;

import lombok.Getter;

@Getter
public enum ErrorsEnum {
    ERROR_AUTHENTICATION_001("Username is already in use!"),
    ERROR_AUTHENTICATION_002("Email is already in use!"),

    ERROR_USER_001("User is not found with that username!"),
    ERROR_USER_002("Current password does not match with inserted one!"),
    ERROR_USER_003("New password and current one are the same!"),
    ERROR_USER_004("New passwords does not match!"),
    ERROR_USER_005("User is not found with that email!"),
    ERROR_USER_006("Your token does not exists, please copy valid token!"),

    ERROR_BUSINESS_001("Business with same address already exists!"),
    ERROR_BUSINESS_002("Undefined business type!"),
    ERROR_BUSINESS_003("Business with that name does not exists!"),
    ERROR_BUSINESS_004("You are not allowed to change active status of this business!"),
    ERROR_BUSINESS_005("Business already have status which you are trying to set!"),
    ERROR_BUSINESS_006("Business addresses are empty!"),
    ERROR_BUSINESS_007("Business type is empty!"),
    ERROR_BUSINESS_009("Business name or username cannot be null!"),

    ERROR_BUSINESS_TYPE_001("Business type already exists!"),
    ERROR_BUSINESS_TYPE_002("Business type does not exists!"),

    ERROR_RATE_001("Estimates must be between 1 and 5"),
    ERROR_RATE_002("Field is too short or too long, please check out your request!"),
    ERROR_RATE_003("You already rated this business!"),
    ERROR_RATE_004("Type of the rate cannot be null, empty or invalid!"),
    ERROR_RATE_005("Rate does not exists!"),

    ERROR_ROLE_001("Role is not found."),
    ERROR_ROLE_002("Roles are empty!"),
    ERROR_ROLE_003("One of the roles does not exists!"),
    ERROR_ROLE_004("Role parameter is empty!");

    private final String code;

    ErrorsEnum(String code) {
        this.code = code;
    }
}
