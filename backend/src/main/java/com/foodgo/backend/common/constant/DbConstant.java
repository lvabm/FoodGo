package com.foodgo.backend.common.constant;

import jakarta.persistence.Column;

public class DbConstant {
    // Table Name
    public static String ADVERTISEMENT_DB_NAME = "advertisement";
    public static String USER_ACCOUNT_DB_NAME = "user_account";
    public static String BOOKING_DB_NAME = "booking";
    public static String COUNTRY_DB_NAME = "country";
    public static String DISTRICT_DB_NAME = "district";
    public static String FEATURE_OF_FNB_DB_NAME = "feature_of_fnb";
    public static String FEATURE_OF_OUTLET_DB_NAME = "feature_of_outlet";
    public static String FNB_DB_NAME = "fnb";
    public static String FNB_CATEGORY_DB_NAME = "fnb_category";
    public static String FNB_SUB_CATEGORY_DB_NAME = "fnb_sub_category";




    // Common Field
    public static String IS_ACTIVE = "is_active";
    public static String OUTLET_ID = "outlet_id";
    public static String USER_ID = "user_id";
    public static String PROVINCE_ID = "province_id";
    public static String NAME = "name";
    public static String DESCRIPTION = "description";
    public static String SUB_CATEGORY_ID = "sub_category_id";
    public static String TYPE_ID = "type_id";
    public static String CATEGORY_ID = "category_id";





    // Individual Field
    // 0.UserAccount
    public static String USERNAME = "username";
    public static String PASSWORD_HASH = "password_hash";
    public static String EMAIL = "email";
    public static String PHONE_NUMBER = "phone_number";

    // 1. Advertisement
    public static String POSITION = "position";
    public static String START_DATE = "start_date";
    public static String END_DATE = "end_date";

    // 2. Booking
    public static String BOOKING_DATE = "booking_date";
    public static String BOOKING_TIME = "booking_time";
    public static String NUMBER_OF_GUESTS = "number_of_guests";
    public static String STATUS = "status";
    public static String USER_NOTES = "user_notes";
    public static String OWNER_NOTES = "owner_notes";

    // 3. Country
    public static String CODE = "code";

    // 4. District (chỉ có 1 Field bị trùng )
    // public static String CODE = "code";

    // 5. FeatureOfFnb (Common)

    // 6. FeatureOfOutlet

    // 7. Fnb

    // 8. FnbCategory





}
