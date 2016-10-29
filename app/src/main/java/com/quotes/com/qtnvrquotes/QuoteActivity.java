package com.quotes.com.qtnvrquotes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class QuoteActivity extends BaseActivity {

    private static final String TAG = QuoteActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);
        Bundle extras = getIntent().getExtras();
        String category = extras.getString("categoryExtra", "N/A");
        String quote = extras.getString("quoteExtra", "N/A");
        String blurb = extras.getString("blurbExtra", "N/A");
        String attribution = extras.getString("attributionExtra", "N/A");
        String date = extras.getString("dateExtra", "N/A");
        String url = extras.getString("urlExtra", "N/A");


        Log.i(TAG, "Received category - " + category);
        Log.i(TAG, "Received quote - " + quote);
        Log.i(TAG, "Received blurb - " + blurb);
        Log.i(TAG, "Received attribution - " + attribution);
        Log.i(TAG, "Received date - " + date);
        Log.i(TAG, "Received url - " + url);

    }
}
