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

    public static final String PULL_CATEGORIES = "categories";
    public static final String PULL_PRODUCTS = "products";

    public static final String I_DIG_IT = "digit";
    public static final String NEEDS_WORK = "needswork";
    public static final String SCRAP_IT = "scrapit";


    public static final String[] categories = {"Clothing", "Home", "Entertainment", "Game concept", "App concept", "Misc",
            "Outdoor living", "Technology", "Travel", "Utilities"};




    private SharedPreferences pref;
    public MobileServiceUser user;

    public MobileServiceClient mClient;


    public static final String USERIDPREF = "uid";
    public static final String TOKENPREF = "tkn";


    public void cacheUserToken(MobileServiceUser user, SharedPreferences pref)
    {

        pref.edit().putString(USERIDPREF, user.getUserId()).commit();
        pref.edit().putString(TOKENPREF, user.getAuthenticationToken()).commit();

    }

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
