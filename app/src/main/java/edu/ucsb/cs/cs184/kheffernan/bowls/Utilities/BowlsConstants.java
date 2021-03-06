package edu.ucsb.cs.cs184.kheffernan.bowls.Utilities;

public class BowlsConstants {

    private BowlsConstants() {
        // Restrict abstraction
    }

    // Responses
    public static final int ORDER_CREATED = 10;
    public static final int ORDER_EDITED = 11;
    public static final int ORDER_DELETED = 12;
    public static final int PROFILE_EDITED = 13;
    public static final int ORDER_RENTED = 14;


    public static final int SEND_OWNER_EMAIL = 20;

    // Requests
    public static final int REQUEST_EDIT_ORDER = 100;
    public static final int REQUEST_ORDER_DETAILS = 101;
    public static final int REQUEST_CREATE_ORDER = 102;
    public static final int REQUEST_PICK_IMAGE = 103;
    public static final int REQUEST_EDIT_PROFILE = 104;

    // Firebase
    public static final String ORDER_PATH = "orders";
    public static final String USER_PATH = "users";

    //User object constants
    public static final String ACCOUNT_TYPE_CUSTOMER = "customer";
    public static final String ACCOUNT_TYPE_BUSINESS = "manager";

    //Order Status
    public static final String ORDER_STATUS_CREATED = "created";
    public static final String ORDER_STATUS_IN_PROGRESS = "in-progress";
    public static final String ORDER_STATUS_READY_FOR_PICK_UP = "ready for pickup";
    public static final String ORDER_STATUS_COMPLETED = "completed";




}
