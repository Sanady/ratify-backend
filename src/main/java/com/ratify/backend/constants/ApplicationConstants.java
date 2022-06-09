package com.ratify.backend.constants;

public class ApplicationConstants {
    //==================================================================================================================
    //--> URL CONSTANTS
    //==================================================================================================================
    //Authentication
    public static final String AUTHORIZATION = "/api/auth";
    public static final String SIGN_IN = "/signin";
    public static final String SIGN_UP = "/signup";

    //Business
    public static final String BUSINESS = "/api/business";
    public static final String CREATE_BUSINESS = "/create";
    public static final String GET_BUSINESS = "/get";
    public static final String BUSINESS_STATUS = "/status";
    public static final String DELETE_BUSINESS = "/delete";
    public static final String GET_BUSINESSES = "/get/businesses";

    //Address
    public static final String GET_ALL_ADDRESSES = "/get/addresses";

    //Roles
    public static final String ROLE = "/api/role";
    public static final String GET_ROLES_FROM_USER = "/get";
    public static final String ADD_ROLE = "/add";
    public static final String REMOVE_ROLE = "/remove";

    //User
    public static final String USER = "/api/user";
    public static final String GET_USER = "/get";
    public static final String FORGET_PASSWORD = "/forget-password";
    public static final String RESET_PASSWORD = "/reset-password";
    public static final String DELETE_USER = "/delete";
    public static final String CHANGE_PASSWORD = "/change-password";

    //Rate
    public static final String RATE = "/api/rate";
    public static final String POST_RATE = "/create";


    private ApplicationConstants() {}
}
