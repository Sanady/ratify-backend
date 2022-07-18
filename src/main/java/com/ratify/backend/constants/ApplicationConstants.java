package com.ratify.backend.constants;

public class ApplicationConstants {
    //==================================================================================================================
    //--> URL CONSTANTS
    //==================================================================================================================
    //Root application url
    public static final String API = "/api";
    public static final String V1 = API + "/v1";

    //Authentication
    public static final String AUTHORIZATION = V1 + "/auth";
    public static final String SIGN_IN = "/signin";
    public static final String SIGN_UP = "/signup";

    //Business
    public static final String BUSINESS = V1 + "/business";
    public static final String CREATE_BUSINESS = "/create";
    public static final String GET_BUSINESS = "/get";
    public static final String BUSINESS_STATUS = "/status";
    public static final String DELETE_BUSINESS = "/delete";
    public static final String GET_BUSINESSES = "/get/businesses";
    public static final String ADD_BUSINESS_TYPE = "/add/business-type";
    public static final String GET_ALL_BUSINESS_TYPES = "/get/all/business-types";
    public static final String GET_BUSINESS_TYPE = "/get/business-type";
    public static final String REMOVE_BUSINESS_TYPE = "/remove/business-type";

    //Address
    public static final String GET_ALL_ADDRESSES = "/get/addresses";

    //Roles
    public static final String ROLE = V1 + "/role";
    public static final String GET_ROLES_FROM_USER = "/get";
    public static final String ADD_ROLE = "/add";
    public static final String REMOVE_ROLE = "/remove";

    //User
    public static final String USER = V1 + "/user";
    public static final String GET_USER = "/get";
    public static final String FORGET_PASSWORD = "/forget-password";
    public static final String RESET_PASSWORD = "/reset-password";
    public static final String DELETE_USER = "/delete";
    public static final String CHANGE_PASSWORD = "/change-password";

    //Rate
    public static final String RATE = V1 + "/rate";
    public static final String POST_BENEFIT_RATE = "/create/benefit";
    public static final String POST_INTERVIEW_RATE = "/create/interview";
    public static final String POST_REVIEW_RATE = "/create/review";
    public static final String GET_REVIEW_RATES = "/get/reviews";


    private ApplicationConstants() {}
}
