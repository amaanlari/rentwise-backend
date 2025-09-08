package com.rentwise.constants;

public class ApiEndpoints {
    public static final String BASE_URL = "/rentwise/api";
    public static final String V1 = "v1";
    public static final String V2 = "v2";

    /// ### CRUD Endpoints
    public static final String CREATE_OWNER = "/v1/owner/create";
    public static final String UPDATE_OWNER = "/v1/owner/update";
    public static final String GET_OWNER = "/v1/owner/get";
    public static final String GET_OWNER_BY_ID = "/v1/owner/get/{id}";
    public static final String DELETE_OWNER = "/v1/owner/delete";

    public static final String CREATE_ROOM = "/v1/room/create";
    public static final String UPDATE_ROOM = "/v1/room/update";
    public static final String GET_ROOM_BY_ID = "/v1/room/get/{id}";
    public static final String DELETE_ROOM = "/v1/room/delete";

    public static final String CREATE_TENANT = "/v1/tenant/create";
    public static final String UPDATE_TENANT = "/v1/tenant/update";
    public static final String GET_TENANT_BY_ID = "/v1/tenant/get/{id}";
    public static final String DELETE_TENANT = "/v1/tenant/delete";

    public static final String CREATE_RENT =  "/v1/rent/create";
    public static final String UPDATE_RENT =  "/v1/rent/update";
    public static final String GET_RENT_BY_ID =  "/v1/rent/get/{id}";
    public static final String DELETE_RENT =  "/v1/rent/delete";
}
