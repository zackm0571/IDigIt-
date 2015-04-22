package com.greghumphreys.com.idigit;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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


            client.login(MobileServiceAuthenticationProvider.WindowsAzureActiveDirectory, new UserAuthenticationCallback() {
                @Override
                public void onCompleted(MobileServiceUser user, Exception exception, ServiceFilterResponse response) {
                    if(user != null){
                        helpers.user = user;
                        setContentView(getInitialView(pref.getBoolean(Helpers.ACCOUNT_TYPE_SELECTED_ID, false)));

                    }
                }
            });
        }
    }
    public void initAzureClient(){
        try {
            helpers.mClient = new MobileServiceClient(
                    "https://idigitapp.azure-mobile.net/",
                    "FfeGWKgPFGHkIwaCRpSTiToxmQJKPL61", this);

            this.mClient = helpers.mClient;
            login(mClient);


        }

        catch(Exception e){

        }
    }

    public void chooseAccountType(View v){

        Button sender = (Button)v;
        pref.edit().putString(Helpers.ACCOUNT_TYPE_ID, sender.getText().toString()).commit();
        pref.edit().putBoolean(Helpers.ACCOUNT_TYPE_SELECTED_ID, true).commit();


        Log.d(sender.getText().toString(), sender.getText().toString());
        setContentView(getInitialView(true));

    }

    public int getInitialView(boolean hasSetAccountType){

        if(!hasSetAccountType){
            return R.layout.choose_account_type_layout;
        }

        else if(hasSetAccountType == true){
           String accountType = pref.getString(Helpers.ACCOUNT_TYPE_ID, "");

            Log.v(accountType, accountType);

           if(accountType.equals(Helpers.ACCOUNT_TYPE_PRODUCER)){
               startActivity(new Intent(this, ProductCreationActivity.class));
           }

            else if(accountType.equals(Helpers.ACCOUNT_TYPE_JUDGER)){
               return R.layout.activity_main;
           }


        }

        return R.layout.activity_main;
    }


}
