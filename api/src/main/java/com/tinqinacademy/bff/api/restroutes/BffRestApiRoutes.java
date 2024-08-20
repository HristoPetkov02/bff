package com.tinqinacademy.bff.api.restroutes;

public class BffRestApiRoutes {
    public final static String API = "/api/v1";

    //Auth rest api routes
    public final static String API_AUTH_CHECK_JWT = API + "/auth/validate-jwt";


    //Hotel rest api routes
    public final static String HOTEL_API_HOTEL = API + "/hotel";
    public final static String HOTEL_API_HOTEL_GET_ROOM = HOTEL_API_HOTEL + "/{roomId}";
    public final static String HOTEL_API_HOTEL_CHECK_AVAILABILITY = HOTEL_API_HOTEL + "/rooms";
    public final static String HOTEL_API_HOTEL_BOOK_ROOM = HOTEL_API_HOTEL + "/{roomId}";
    public final static String HOTEL_API_HOTEL_UNBOOK_ROOM = HOTEL_API_HOTEL + "/{bookingId}";


    public final static String HOTEL_API_SYSTEM = API + "/system";
    public final static String HOTEL_API_SYSTEM_ADD_ROOM = HOTEL_API_SYSTEM + "/room";
    public final static String HOTEL_API_SYSTEM_REGISTER_VISITOR = HOTEL_API_SYSTEM + "/register";
    public final static String HOTEL_API_SYSTEM_VISITOR_REPORT = HOTEL_API_SYSTEM + "/register";
    public final static String HOTEL_API_SYSTEM_DELETE_ROOM = HOTEL_API_SYSTEM + "/room/{roomId}";
    public final static String HOTEL_API_SYSTEM_UPDATE_ROOM = HOTEL_API_SYSTEM + "/room/{roomId}";
    public final static String HOTEL_API_SYSTEM_UPDATE_PARTIALLY_ROOM = HOTEL_API_SYSTEM + "/room/{roomId}";

    //Comments rest api routes
    public final static String COMMENTS_API_HOTEL = API + "/hotel";
    public final static String COMMENTS_API_HOTEL_GET_ROOM_COMMENT = COMMENTS_API_HOTEL +"/{roomId}/comment";
    public final static String COMMENTS_API_HOTEL_LEAVE_COMMENT = COMMENTS_API_HOTEL +"/{roomId}/comment";
    public final static String COMMENTS_API_HOTEL_UPDATE_OWN_COMMENT = COMMENTS_API_HOTEL +"/comment/{commentId}";



    public final static String COMMENTS_API_SYSTEM = API + "/system";
    public final static String COMMENTS_API_SYSTEM_UPDATE_COMMENT = COMMENTS_API_SYSTEM + "/comment/{commentId}";
    public final static String COMMENTS_API_SYSTEM_DELETE_COMMENT = COMMENTS_API_SYSTEM + "/system/comment/{commentId}";
}
