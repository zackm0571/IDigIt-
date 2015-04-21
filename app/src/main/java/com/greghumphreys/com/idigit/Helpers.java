package com.greghumphreys.com.idigit;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zachmathews on 4/20/15.
 */
public class Helpers {

    public static final Helpers instance = new Helpers();

    public static String ACCOUNT_TYPE_SELECTED_ID = "account_type_selected_bool";
    public static String ACCOUNT_TYPE_ID = "account_identifier";

    private SharedPreferences pref;
    public boolean checkForUserTypeSet(){
        return pref.getBoolean("hasSetAccountType", false);
    }

    public SharedPreferences getSharedPref(Context context){
        if(pref == null){
            pref = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        }

        return pref;
    }

}
