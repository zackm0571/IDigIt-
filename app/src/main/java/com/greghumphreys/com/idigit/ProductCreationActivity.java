package com.greghumphreys.com.idigit;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;


public class ProductCreationActivity extends Activity {



    EditText productTitle, productDescription;

    //Spinner provides a dropdown box to select category
    Spinner categoryPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Sets layout from xml
        setContentView(R.layout.product_creation_layout);

        productTitle = (EditText)findViewById(R.id.productTitleText);
        productDescription = (EditText)findViewById(R.id.productDescriptionText);


        //Initialize spinner and attach adapter to populate dropdown
        categoryPicker = (Spinner)findViewById(R.id.categoryPicker);
        ArrayAdapter<String> a =new ArrayAdapter<String>(ProductCreationActivity.this,android.R.layout.simple_spinner_item, Helpers.categories);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryPicker.setAdapter(a);

    }


    public void submitProduct(View v){

        //Get helper and client instance
        Helpers helpers = Helpers.instance;
        MobileServiceClient client = helpers.mClient;

        //Create product by mapping fields to object
        Products product = new Products();
        product.productname = productTitle.getText().toString();
        product.productdescription = productDescription.getText().toString();
        product.category = categoryPicker.getSelectedItem().toString();
        product.userid = Helpers.instance.user.getUserId();

        //Get table and insert with callback
        client.getTable(Products.class).insert(product, new TableOperationCallback<Products>() {
            @Override
            public void onCompleted(Products entity, Exception exception, ServiceFilterResponse response) {

                if(exception == null){
                    //success
                }

                else{
                    Log.e("Upload Failure", exception.getMessage().toString());
                }

                //Starts productview activity
                startActivity(new Intent(ProductCreationActivity.this, ProductViewActivity.class));

            }
        });



    }


    public void setProductImg(View v){
            takePhoto();
    }

    String path;
    public void takePhoto()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 2);
        }
    }



    public class GetImage extends AsyncTask<Void, Void, Void> {
       // public ProgressDialog progDialog = null;
        Bitmap productImg;
        protected void onPreExecute() {
//            progDialog = ProgressDialog.show(ProductCreationActivity.this, "", "Loading...", true);
        }

        @Override
        protected Void doInBackground(Void... params) {


            productImg = BitmapFactory.decodeFile(path.toString().trim());
            productImg = Bitmap.createScaledBitmap(productImg, 500, 500, true);

            return null;
        }

        protected void onPostExecute(Void result) {
//            if (progDialog.isShowing()) {
//                progDialog.dismiss();
//            }

            if(productImg != null) {
                Drawable d = new BitmapDrawable(ProductCreationActivity.this.getResources(), productImg);
                Button btn = (Button) findViewById(R.id.loadProductImageButton1);
                btn.setBackground(d);
            }

            else{
                Toast.makeText(ProductCreationActivity.this, "Load img failed", Toast.LENGTH_LONG).show();
            }


        }
    }


//Called after startActivityForResult returns from new activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode==2 && resultCode == RESULT_OK)
        {
            Log.v("Load Image", "Camera File Path=====>>>"+path);

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            Button btn = (Button) findViewById(R.id.loadProductImageButton1);
            btn.setBackground(new BitmapDrawable(getResources(), imageBitmap));

        }
            //new GetImage().execute();
      }

}
