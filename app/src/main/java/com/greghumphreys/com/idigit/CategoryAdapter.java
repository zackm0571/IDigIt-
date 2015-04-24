package com.greghumphreys.com.idigit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.microsoft.windowsazure.mobileservices.MobileServiceTable;

import java.util.List;

/**
 * Created by zachmathews on 4/23/15.
 */

/* Note:Should create reusable standard interface for our Array Adapters
rather than creating a new one every time we work with new data
 */

//Array Adapter provides logic for items in ListView
public class CategoryAdapter  extends ArrayAdapter<String> {

//Context points to current activity
    private Context context;

    //Objects to populate list
    private List<String> objects;


    public CategoryAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);

        this.context = context;
        this.objects = objects;


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

        //On first run, view will be null, so we will generate a view from our resource file with a LayoutInflater
        if(view == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.category_view, parent, false);
        }

        //Find the category textview from our newly generated view
        TextView categoryText = (TextView)view.findViewById(R.id.categoryText);

        //Retrieve the category string at the current ListView index
        String category= objects.get(position);

        //Maps category text to data
        categoryText.setText(category);

        //return the populated view
      return  view;
    }






}

