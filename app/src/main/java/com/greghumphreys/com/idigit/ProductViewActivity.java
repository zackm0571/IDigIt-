package com.greghumphreys.com.idigit;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.Menu;

import android.view.View;
import android.widget.ListView;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceQuery;
import com.microsoft.windowsazure.mobileservices.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.QueryOrder;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableQueryCallback;

import java.util.List;


public class ProductViewActivity extends ActionBarActivity {


    private MobileServiceTable<Products> productTable;
    private ProductAdapter adapter;
    private ListView productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        productList = (ListView)findViewById(R.id.listView);
        setData();
      //  this.setListAdapter(new ProductAdapter(this, R.layout.product_detail_view, ));

    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_product_view, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_product_view, menu);
        return super.onCreateOptionsMenu(menu);
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


                                    productList.setAdapter(adapter);
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
