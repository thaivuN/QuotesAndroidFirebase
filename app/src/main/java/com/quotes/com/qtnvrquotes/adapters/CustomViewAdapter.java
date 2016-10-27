package com.quotes.com.qtnvrquotes.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.quotes.com.qtnvrquotes.MainActivity;
import com.quotes.com.qtnvrquotes.QuoteActivity;
import com.quotes.com.qtnvrquotes.R;

/**
 * Created by MDThai on 10/24/2016.
 */

public class CustomViewAdapter extends BaseAdapter {
    Context context;
    String[] categories;
    private static LayoutInflater inflater;
    public CustomViewAdapter(MainActivity mainActivity, String[] categories){
        this.context = mainActivity;
        this.categories = categories;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return categories.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = inflater.inflate(R.layout.category_list,null);

        TextView tv = (TextView) rowView.findViewById(R.id.categoryItem);
        tv.setText(categories[position]);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TO DO
                Intent intent = new Intent(context, QuoteActivity.class);

                //TO DO: PUT EXTRA

                context.startActivity(intent);
            }
        });

        return null;
    }
}
