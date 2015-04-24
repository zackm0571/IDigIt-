package com.greghumphreys.com.idigit;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.microsoft.windowsazure.mobileservices.MobileServiceAuthenticationProvider;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceUser;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.UserAuthenticationCallback;

public class MainActivity extends Activity {

    private Helpers helpers; //Helpers is a singleton class that provides ease of use functions and variable identifiers
    private SharedPreferences pref; //Shared preferences writes key value pairs to persistent storage via XML

    private MobileServiceClient mClient; //Azure mobile services client

    private Intent viewProductsIntent; //Intent sends application to a new activity, this one being view products

    private Handler retryLogin = new Handler(); //Handler to retry loading login page in event of failure
    private Runnable runLogin; //Run login thread


    private long loginTimeOut = 500; //Time out time



    /***** onCreate is called when the Activity has finished initialization.
      Use this method to handle any initial logic such as UI or variable instantiation ****/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        helpers = Helpers.instance; //get helper instance
        pref = helpers.getSharedPref(this); //get shared preferences instance

        /** initialize view product intent (format: SenderActivity.this, NewActivity.class **/
        viewProductsIntent = new Intent(MainActivity.this, ProductViewActivity.class);

        initAzureClient(); //initialize Azure client and handle auth flow
  }


/** Handles login logic **/
    public void login(final MobileServiceClient client){

        //check for valid client
        if(client != null){

            //try login
           runLogin = new Runnable() {
                @Override
                public void run() {

                    //Azure login with Microsoft Account
                    client.login(MobileServiceAuthenticationProvider.MicrosoftAccount, new UserAuthenticationCallback() {
                        @Override
                        public void onCompleted(MobileServiceUser user, Exception exception, ServiceFilterResponse response) {

                            if (exception == null) {

                                //set current user
                                if (user != null) {
                                    helpers.user = user;

                                    //Check SharedPreferences if account type (producer / judge) has been selected via boolean flag
                                  //  if (!pref.getBoolean(Helpers.ACCOUNT_TYPE_SELECTED_ID, false)) {

                                        //Account type not set, show view to select account type

                                    //Set Account type chooser layout
                                        setContentView(R.layout.choose_account_type_layout);

                                    //} else {

                                        //Account type set, show products / categories

                                    //}
                                }
                            } else {
                                //Initial connection failed, retry after timeout time
                                retryLogin.postDelayed(runLogin, loginTimeOut);

                            }
                        }
                    });
                }
            };

            //Call initial login flow on Runnable thread
            retryLogin.postDelayed(runLogin, loginTimeOut);

        }
    }
    public void initAzureClient(){
        try {

            //init azure client
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

        //Inserts var into Preferences, changes are not saved unless ".commit()" has been called
        pref.edit().putString(Helpers.ACCOUNT_TYPE_ID, sender.getText().toString()).commit();
        pref.edit().putBoolean(Helpers.ACCOUNT_TYPE_SELECTED_ID, true).commit();


        //Log.d prints message to logcat (Console / logger output in Android Studio)
        Log.d(sender.getText().toString(), sender.getText().toString());

        //Starts product view activity by passing intent
        startActivity(viewProductsIntent);

    }

//    public int getInitialView(boolean hasSetAccountType){
//
//        if(!hasSetAccountType){
//            return R.layout.choose_account_type_layout;
//        }
//
//        else {
//            this.finish();
//        }
//
//        return R.layout.activity_main;
//    }


}
