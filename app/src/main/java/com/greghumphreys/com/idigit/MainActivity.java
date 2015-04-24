package com.greghumphreys.com.idigit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceAuthenticationProvider;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceUser;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.UserAuthenticationCallback;

public class MainActivity extends Activity {

    private Helpers helpers;
    private SharedPreferences pref;

    private MobileServiceClient mClient;

    private Intent viewProductsIntent;

    private Handler retryLogin = new Handler();

    private Runnable runLogin;


    long loginTimeOut = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        helpers = Helpers.instance;
        pref = helpers.getSharedPref(this);

        viewProductsIntent = new Intent(MainActivity.this, ProductViewActivity.class);
        initAzureClient();



    }


    private boolean loadUserTokenCache(MobileServiceClient client)
    {

        String userId = pref.getString(Helpers.USERIDPREF, "undefined");
        if (userId == "undefined")
            return false;
        String token = pref.getString(Helpers.TOKENPREF, "undefined");
        if (token == "undefined")
            return false;

        MobileServiceUser user = new MobileServiceUser(userId);
        user.setAuthenticationToken(token);
        client.setCurrentUser(user);

        return true;
    }
    public void login(final MobileServiceClient client){

        if(client != null){
           runLogin = new Runnable() {
                @Override
                public void run() {
                    client.login(MobileServiceAuthenticationProvider.MicrosoftAccount, new UserAuthenticationCallback() {
                        @Override
                        public void onCompleted(MobileServiceUser user, Exception exception, ServiceFilterResponse response) {

                            if (exception == null) {
                                if (user != null) {
                                    helpers.user = user;


                                    if (!pref.getBoolean(Helpers.ACCOUNT_TYPE_SELECTED_ID, false)) {
                                        setContentView(getInitialView(pref.getBoolean(Helpers.ACCOUNT_TYPE_SELECTED_ID, false)));

                                    } else {
                                        startActivity(viewProductsIntent);
                                    }
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "Connection failed: retrying", Toast.LENGTH_LONG).show();
                                retryLogin.postDelayed(runLogin, loginTimeOut);

                            }
                        }
                    });
                }
            };
            retryLogin.postDelayed(runLogin, loginTimeOut);

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
        startActivity(viewProductsIntent);

    }

    public int getInitialView(boolean hasSetAccountType){

        if(!hasSetAccountType){
            return R.layout.choose_account_type_layout;
        }

        else {
            this.finish();
        }

        return R.layout.activity_main;
    }


}
