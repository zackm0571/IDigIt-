package com.greghumphreys.com.idigit;


import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;


import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceQuery;
import com.microsoft.windowsazure.mobileservices.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.QueryOrder;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableQueryCallback;

import java.util.List;


public class ProductViewActivity extends ListActivity {


    private MobileServiceTable<Products> productTable;
    private ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setData();
      //  this.setListAdapter(new ProductAdapter(this, R.layout.product_detail_view, ));

    }




    protected void setData(){
        MobileServiceClient client = Helpers.instance.mClient;

        productTable = client.getTable(Products.class);

        MobileServiceQuery query = new MobileServiceQuery<>();
        query.orderBy("_createdAt", QueryOrder.Descending);



        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {

                    productTable.execute(new TableQueryCallback<Products>() {
                        @Override
                        public void onCompleted(List<Products> result, int count, Exception exception, ServiceFilterResponse response) {

                            final List<Products> products = result;
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {



                                    adapter = new ProductAdapter(ProductViewActivity.this,
                                            R.layout.product_detail_view, products);


                                    ProductViewActivity.this.setListAdapter(adapter);
                                }
                            });
                        }
                    });

                } catch (Exception exception) {

                }
                return null;
            }
        }.execute();

//        MobileServiceTable<Products> table =    client.getTable(Products.class).exe
//      .execute(query, new TableQueryCallback<Products>() {
//            @Override
//            public void onCompleted(List result, int count, Exception exception, ServiceFilterResponse response) {
//                setListAdapter(new ProductAdapter(ProductViewActivity.this, R.layout.product_detail_view, result));
//            }
//        });

    }

}
