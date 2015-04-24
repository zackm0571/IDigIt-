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
 * Created by zachmathews on 4/23/15.
 */
public class CategoryAdapter  extends ArrayAdapter<String> {


    private Context context;
    private List<String> objects;
    private MobileServiceTable<Products> productTable;

    public CategoryAdapter(Context context, int resource, List<String> objects) {
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
    public String getItem(int position) {
        return objects.get(position);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.category_view, parent, false);
        }


        TextView categoryText = (TextView)view.findViewById(R.id.categoryText);

        String category= objects.get(position);

            categoryText.setText(category);

      return  view;
    }






}

