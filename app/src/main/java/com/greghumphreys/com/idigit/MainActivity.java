package com.greghumphreys.com.idigit;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.microsoft.windowsazure.mobileservices.*;

public class MainActivity extends Activity {

    private Helpers helpers;
    private SharedPreferences pref;

    private MobileServiceClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        helpers = Helpers.instance;
        pref = helpers.getSharedPref(this);

        initAzureClient();

        setContentView(getInitialView(pref.getBoolean(Helpers.ACCOUNT_TYPE_SELECTED_ID, false)));

    }



    public void login(MobileServiceClient client){

        if(client != null){
            client.login(MobileServiceAuthenticationProvider.Facebook, new UserAuthenticationCallback() {
                @Override
                public void onCompleted(MobileServiceUser user, Exception exception, ServiceFilterResponse response) {

                }
            });
        }
    }
    public void initAzureClient(){
        try {
            mClient = new MobileServiceClient(
                    "https://idigitapp.azure-mobile.net/",
                    "FfeGWKgPFGHkIwaCRpSTiToxmQJKPL61",
                    this
            );
        }

        catch(Exception e){

        }
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
