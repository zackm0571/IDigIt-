package com.greghumphreys.com.idigit;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceQuery;
import com.microsoft.windowsazure.mobileservices.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.QueryOrder;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableQueryCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ProductViewActivity extends ActionBarActivity {


    private MobileServiceTable<Products> productTable;
    private ProductAdapter adapter;
    private CategoryAdapter categoryAdapter;
    private ListView productList;

    protected String VIEW_PRODUCTS = "products";
    protected String VIEW_CATEGORIES = "categories";

    protected String CURRENT_VIEW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        productList = (ListView)findViewById(R.id.listView);

        getSupportActionBar().show();
        setCategories();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_product_view, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_product_view, menu);

        menu.findItem(R.id.back_to_categories).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(CURRENT_VIEW.equals(VIEW_PRODUCTS)){
                    setCategories();
                }
                else{
                    startActivity(new Intent(ProductViewActivity.this, MainActivity.class));
                }

                return false;
            }
        });

        menu.findItem(R.id.add_product).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(ProductViewActivity.this, ProductCreationActivity.class));
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    protected void setCategories(){

        CURRENT_VIEW = VIEW_CATEGORIES;
        List<String> categories = Arrays.asList(Helpers.categories);
        categoryAdapter = new CategoryAdapter(ProductViewActivity.this, R.layout.category_view, categories);
        productList.setAdapter(categoryAdapter);

        productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setData(Helpers.categories[position]);
            }
        });
    }


    protected void setData(final String category){

        CURRENT_VIEW = VIEW_PRODUCTS;
        MobileServiceClient client = Helpers.instance.mClient;

        productTable = client.getTable(Products.class);




        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {


                    productTable.where().field("category").eq(category).execute(new TableQueryCallback<Products>() {
                        @Override
                        public void onCompleted(List<Products> result, int count, Exception exception, ServiceFilterResponse response) {


                            final List<Products> products = result;

                            //When updating UI, logic must be run on UI thread to avoid runtime exception
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


    }

}
