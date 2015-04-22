package com.greghumphreys.com.idigit;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Map;

/**
 * Created by zachmathews on 4/22/15.
 */
public class ProductAdapter extends ArrayAdapter<Products>{


    private Context context;
    private List<Products> objects;
    public ProductAdapter(Context context, int resource, List<Products> objects) {
        super(context, resource, objects);

        this.context = context;
        this.objects = objects;
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

            view = inflater.inflate(R.layout.product_detail_view, null);

            TextView titleText = (TextView)view.findViewById(R.id.productTitleText);
            TextView descriptionText = (TextView)view.findViewById(R.id.productDescriptionText);

            Products product = objects.get(position);
            if(titleText != null && product.productname != null) {
                titleText.setText(objects.get(position).productname);

            descriptionText.setText(objects.get(position).productdescription);
            }
            ImageView img = (ImageView)view.findViewById(R.id.productImg);
            img.setImageResource(R.mipmap.ic_launcher);

        }


        return view;
    }
}
