package com.greghumphreys.com.idigit;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.microsoft.windowsazure.mobileservices.MobileServiceTable;

import java.util.List;

/**
 * Created by zachmathews on 4/22/15.
 */


/* Note:Should create reusable standard interface for our Array Adapters
rather than creating a new one every time we work with new data
 */

//Array Adapter provides logic for items in ListView
public class ProductAdapter extends ArrayAdapter<Products>{


    private Context context;
    private List<Products> objects;

    //Azure mobile services table / tuples that contains our product objects
    private MobileServiceTable<Products> productTable;

    public ProductAdapter(Context context, int resource, List<Products> objects) {
        super(context, resource, objects);

        this.context = context;
        this.objects = objects;

        this.productTable = Helpers.instance.mClient.getTable(Products.class);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Products getItem(int position) {
        return objects.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        //On first run, view will be null, so we will generate a view from our resource file with a LayoutInflater
        if(view == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.product_detail_view, parent, false);
         }


        //Retrieve widgets (textview, imageView, buttons) from newly inflated view
        TextView titleText = (TextView)view.findViewById(R.id.productViewTitle);
        TextView descriptionText = (TextView)view.findViewById(R.id.productViewDescription);

        Products product = objects.get(position);

        //Map data to UI
        titleText.setText(product.productname);
        descriptionText.setText(product.productdescription);

        ImageView img = (ImageView)view.findViewById(R.id.productImg);
        img.setImageResource(R.mipmap.ic_launcher);


        Button digit = (Button)view.findViewById(R.id.digitproductbutton);
        digit.setText("I DIG IT ("+product.digs +")");

        Button needsWork = (Button)view.findViewById(R.id.needsworkproductbutton);
        needsWork.setText("NEEDS WORK (" +product.needsworks+")");

        Button scrapit = (Button)view.findViewById(R.id.scrapitproductbutton);
        scrapit.setText("SCRAP IT (" + product.scraps +")");


        final int pos = position;


        //Sets logic for when button has been clicked
        digit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProductRatedClick(Helpers.I_DIG_IT, pos);
            }
        });


        needsWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProductRatedClick(Helpers.NEEDS_WORK, pos);
            }
        });

        scrapit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProductRatedClick(Helpers.SCRAP_IT, pos);
            }
        });
        return view;
    }


    protected void onProductRatedClick(final String operation, final int index){


        new AsyncTask<Void, Void, Void>() {




            @Override
            protected Void doInBackground(Void... params) {
                try {

                    //Increment respective operation on product and then push update to Azure table
                    Products product = objects.get(index);
                    if(operation.equals(Helpers.I_DIG_IT)){
                        product.digs++;
                    }

                    else if(operation.equals(Helpers.NEEDS_WORK)){
                        product.needsworks++;
                    }

                    else if(operation.equals(Helpers.SCRAP_IT)){
                        product.scraps++;
                    }


                    productTable.update(product, null);

                } catch (Exception exception) {

                }
                return null;
            }
        }.execute();




    }
}
