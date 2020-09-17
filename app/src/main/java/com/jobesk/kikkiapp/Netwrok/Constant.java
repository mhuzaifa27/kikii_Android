package com.jobesk.kikkiapp.Netwrok;

public class Constant {

    /**
     * -------------------- EDIT THIS WITH YOURS -------------------------------------------------
     */

    // Edit WEB_URL with your url. Make sure you have backslash('/') in the end url
//    public static String WEB_URL = "http://demo.dream-space.web.id/markeet_panel/";
   // public static String WEB_URL = "https://thinkgeniux.live/livewave1/public/api/";
   // public static String IMAGE_URL = "https://thinkgeniux.live/livewave1/public/";
    public static String WEB_URL ="https://kikii.jobesk.com/api/";
    public static String IMAGE_URL ="http://livewaveapp.com/";
//    public static String WEB_URL = "https://thinkgeniux.live/livewave1/public/api/";
//    public static String IMAGE_URL = "https://thinkgeniux.live/livewave1/public/";


    public static final String MEMBER_SEARCH_PARAM="member_search_param";
    public static final String CATEGORY_SEARCH_PARAM="category_search_param";
    public static final String SUBCATEGORY_SEARCH_PARAM="subcategory_search_param";
    public static final String LAT_SEARCH_PARAM="lat_search_param";
    public static final String LONG_SEARCH_PARAM="long_search_param";

    public static final String SPECIFIC_USER_ID="specific_user_id";
    public static boolean COMING_FROM_ALERT=false;

    /* [ IMPORTANT ] be careful when edit this security code */
    /* This string must be same with security code at Server, if its different android unable to submit order */
    public static final String SECURITY_CODE = "8V06LupAaMBLtQqyqTxmcN42nn27FlejvaoSM3zXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";

    // Device will re-register the device data to server when open app N-times
    public static int OPEN_COUNTER = 50;


    /**
     * ------------------- DON'T EDIT THIS -------------------------------------------------------
     */

    // this limit value used for give pagination (request and display) to decrease payload
    public static int NEWS_PER_REQUEST = 10;
    public static int PRODUCT_PER_REQUEST = 10;
    public static int NOTIFICATION_PAGE = 20;
    public static int WISHLIST_PAGE = 20;

    // retry load image notification
    public static int LOAD_IMAGE_NOTIF_RETRY = 3;

    // Method get path to image
    public static String getURLimgProduct(String file_name) {
        return WEB_URL + "uploads/product/" + file_name;
    }

    public static String getURLimgNews(String file_name) {
        return WEB_URL + "uploads/news/" + file_name;
    }

    public static String getURLimgCategory(String file_name) {
        return WEB_URL + "uploads/category/" + file_name;
    }

    public static int SPAN_COUNTS=0;
    public static boolean IS_CHANGED = false;

    public static final String TITLE="title";
    public static final String CATEGORY_ID="category_id";
    public static final String EVENT_ID="event_id";
    public static final String SUBCATEGORY_ID="subcategory_id";
    public static final String STARTS_AT="starts_at";
    public static final String ENDS_AT="ends_at";
    public static final String ATTACHMENT="attachment";
    public static final String PAID="paid";
    public static final String LIMITED="limited";
    public static final String AMOUNT="amount";
    public static final String TOKEN="token";
    public static final String TYPE="type";
    public static final String TICKETS="tickets";
    public static final String COMMENT="comment";
    public static final String TAGS="tags";
    public static final String IDS="ids";
    public static final String COMMENT_ID="comment_id";
    public static final String PROFILE_ID="profile_id";
    public static final String LATITUDE="latitude";
    public static final String LONGITUDE="longitude";
    public static final String PHONE="phone";
    public static final String CODE="code";
    public static final String NAME="name";
    public static final String EMAIL="email";
    public static final String BIRTHDAY="birthday";
    public static final String PROFILE_PIC="profile_pic";
    public static final String SINGLE="single";
    public static final String MULTIPLE="multiple";

}
