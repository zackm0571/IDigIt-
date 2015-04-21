package com.greghumphreys.com.idigit;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    private Helpers helpers;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        helpers = Helpers.instance;
        pref = helpers.getSharedPref(this);

        setContentView(getInitialView(pref.getBoolean(Helpers.ACCOUNT_TYPE_SELECTED_ID, false)));

    }


    public void chooseAccountType(View v){

        Button sender = (Button)v;
        pref.edit().putString(Helpers.ACCOUNT_TYPE_ID, sender.getText().toString()).apply();
        pref.edit().putBoolean(Helpers.ACCOUNT_TYPE_SELECTED_ID, true).apply();

    }

    public int getInitialView(boolean hasSetAccountType){

        if(!hasSetAccountType){
            return R.layout.choose_account_type_layout;
        }

        return R.layout.activity_main;
    }


}
