package com.quotes.com.qtnvrquotes.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.quotes.com.qtnvrquotes.MainActivity;
import com.quotes.com.qtnvrquotes.QuoteActivity;
import com.quotes.com.qtnvrquotes.R;
import com.quotes.com.qtnvrquotes.beans.QuoteBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by MDThai on 10/24/2016.
 */

public class CustomViewAdapter extends BaseAdapter {
    private Context context;
    private List<String> categories;
    private DataSnapshot snapshot;
    String TAG = CustomViewAdapter.class.getName();
    private static LayoutInflater inflater;
    public CustomViewAdapter(MainActivity mainActivity, List<String> categories, DataSnapshot snapshot){
        this.context = mainActivity;
        this.categories = categories;
        this.snapshot = snapshot;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public int getCount() {
        return categories.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = inflater.inflate(R.layout.category_list,null);

        TextView tv = (TextView) rowView.findViewById(R.id.categoryItem);
        tv.setText(categories.get(position));
        final List<QuoteBean> quoteBeanList = new ArrayList<>();




        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick(View view), view clicked, category is " + categories.get(position)  );
                Log.i(TAG, "number of quotes in current Category list in onClick(): " + quoteBeanList.size());
                //TO DO
                //Intent intent = new Intent(context, QuoteActivity.class);

                //TO DO: PUT EXTRA

                //context.startActivity(intent);
            }
        });

        final Query query = snapshot.getRef().child(categories.get(position));
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i(TAG,"Category length for " + categories.get(position) + " is: "+ dataSnapshot.getChildrenCount());


                Iterator<DataSnapshot> quotes = dataSnapshot.getChildren().iterator();
                while(quotes.hasNext()){
                    DataSnapshot quoteSnap = quotes.next();
                    QuoteBean quoteBean = new QuoteBean();
                    quoteBean.setCategory(categories.get(position));
                    Log.i(TAG, "Iterating quotes of " + categories.get(position));
                    Log.i(TAG, "attribution: " + quoteSnap.child("attribution").getValue());
                    quoteBean.setAttribution((String)quoteSnap.child("attribution").getValue());
                    quoteBean.setQuote((String)quoteSnap.child("quote").getValue());
                    quoteBean.setUrl((String) quoteSnap.child("url").getValue());
                    quoteBean.setBlurb((String)quoteSnap.child("blurb").getValue());
                    quoteBean.setDate((String) quoteSnap.child("date").getValue());
                    quoteBeanList.add(quoteBean);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });







        return rowView;
    }
}
