package com.greghumphreys.com.idigit;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;


public class ProductCreationActivity extends Activity {



    EditText productTitle, productDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_creation_layout);

        productTitle = (EditText)findViewById(R.id.productTitleText);
        productDescription = (EditText)findViewById(R.id.productDescriptionText);
    }


    public void submitProduct(View v){

        Helpers helpers = Helpers.instance;
        MobileServiceClient client = helpers.mClient;

        Products product = new Products();
        product.productName = productTitle.getText().toString();
        product.productDescription = productDescription.getText().toString();

        client.getTable(Products.class).insert(product, new TableOperationCallback<Products>() {
            @Override
            public void onCompleted(Products entity, Exception exception, ServiceFilterResponse response) {

                if(exception == null){
                    //success
                }

                else{
                    Log.e("Upload Failure", exception.getMessage().toString());
                }

            }
        });



    }


}
