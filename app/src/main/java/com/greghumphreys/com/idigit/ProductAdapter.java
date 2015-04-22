package com.greghumphreys.com.idigit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;

import java.util.List;
import java.util.Map;

/**
 * Created by zachmathews on 4/22/15.
 */
public class ProductAdapter extends ArrayAdapter<Products>{


    private Context context;
    public ProductAdapter(Context context, int resource, List<Products> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public Products getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.product_detail_view);

        }


        return super.getView(position, convertView, parent);
    }
}
