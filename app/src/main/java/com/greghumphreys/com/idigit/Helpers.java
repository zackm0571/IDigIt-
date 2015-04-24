package com.greghumphreys.com.idigit;

import android.content.Context;
import android.content.SharedPreferences;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceUser;

/**
 * Created by zachmathews on 4/20/15.
 */
public class Helpers {

    //instance
    public static final Helpers instance = new Helpers();

    //Account selected flag id
    public static final String ACCOUNT_TYPE_SELECTED_ID = "account_type_selected_bool";

    //Account type chosen id
    public static final String ACCOUNT_TYPE_ID = "account_identifier";

    //Account type identifiers
    public static final String ACCOUNT_TYPE_JUDGER = "I am a judge";
    public static final String ACCOUNT_TYPE_PRODUCER = "I am a producer";

  //Rating operation identifiers
    public static final String I_DIG_IT = "digit";
    public static final String NEEDS_WORK = "needswork";
    public static final String SCRAP_IT = "scrapit";


    //Static categories for sorting products
    public static final String[] categories = {"Clothing", "Home", "Entertainment", "Game concept", "App concept", "Misc",
            "Outdoor living", "Technology", "Travel", "Utilities"};



    //Shared preferences
    private SharedPreferences pref;

    //Current azure user logged in
    public MobileServiceUser user;

    //Azure client
    public MobileServiceClient mClient;


    //Shared preferences encapsulated getter
    public SharedPreferences getSharedPref(Context context){
        if(pref == null){
            pref = context.getSharedPreferences(context.getPackageName() + ".prefs", Context.MODE_PRIVATE);
        }

        return pref;
    }

    public String getAccountType(Context context){
        return getSharedPref(context).getString(Helpers.ACCOUNT_TYPE_ID, "null");
    }

}
