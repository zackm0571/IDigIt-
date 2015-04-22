package com.greghumphreys.com.idigit;

import android.content.Context;
import android.content.SharedPreferences;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceUser;

/**
 * Created by zachmathews on 4/20/15.
 */
public class Helpers {

    public static final Helpers instance = new Helpers();

    public static final String ACCOUNT_TYPE_SELECTED_ID = "account_type_selected_bool";
    public static final String ACCOUNT_TYPE_ID = "account_identifier";

    public static final String PRODUCT_TABLE_ID = "products";


    public static final String ACCOUNT_TYPE_JUDGER = "I am a judge";
    public static final String ACCOUNT_TYPE_PRODUCER = "I am a producer";

    private SharedPreferences pref;
    public MobileServiceUser user;

    public MobileServiceClient mClient;

    public boolean checkForUserTypeSet(){
        return pref.getBoolean("hasSetAccountType", false);
    }

    public SharedPreferences getSharedPref(Context context){
        if(pref == null){
            pref = context.getSharedPreferences(context.getPackageName() + ".prefs", Context.MODE_PRIVATE);
        }

        return pref;
    }

}
