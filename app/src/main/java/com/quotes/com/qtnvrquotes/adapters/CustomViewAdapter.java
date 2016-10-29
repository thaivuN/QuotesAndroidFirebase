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
 *
 * Created by Thai-Vu Nguyen on 10/24/2016.
 */

public class CustomViewAdapter extends BaseAdapter {
    private Context context;
    private List<String> categories;
    private DataSnapshot snapshot;
    String TAG = CustomViewAdapter.class.getName();
    private static LayoutInflater inflater;

    /**
     * Constructor
     * @param mainActivity MainActivity
     * @param categories List<String>
     * @param snapshot DataSnapshot
     */
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

        //Displaying the current category in the UI
        tv.setText(categories.get(position));

        //Container of the list of Quotes in the current category
        final List<QuoteBean> quoteBeanList = new ArrayList<>();


        /**
         * Setting an onClickListener on the current row view
         */
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick(View view), view clicked, category is " + categories.get(position)  );
                Log.i(TAG, "number of quotes in current Category list in onClick(): " + quoteBeanList.size());
                //Getting the random quote
                QuoteBean launchQuote = getRandomQuote(quoteBeanList);

                //Setting up intent extra data
                Intent intent = new Intent(context, QuoteActivity.class);
                intent.putExtra("categoryExtra", launchQuote.getCategory());
                intent.putExtra("attributionExtra", launchQuote.getAttribution());
                intent.putExtra("blurbExtra", launchQuote.getBlurb());
                intent.putExtra("quoteExtra", launchQuote.getQuote());
                intent.putExtra("dateExtra", launchQuote.getDate());
                intent.putExtra("urlExtra",launchQuote.getUrl());

                //Launching the quote activity
                context.startActivity(intent);
            }
        });

        /**
         * Querying the list of quotes in the current category and updating the list of QuoteBean
         */
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

    private QuoteBean getRandomQuote(List<QuoteBean> list){
        int randomIndex = (int) (Math.random() * list.size());
        return list.get(randomIndex);
    }
}
