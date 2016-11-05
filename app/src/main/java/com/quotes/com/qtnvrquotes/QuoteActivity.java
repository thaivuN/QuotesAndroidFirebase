package com.quotes.com.qtnvrquotes;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Activity class that displays the quote and its related information
 * @author Thai-Vu Nguyen, Victor Ruggi
 */
public class QuoteActivity extends BaseActivity {

    private static final String TAG = QuoteActivity.class.getName();

    /**
     * onCreate(Bundle) lifecycle method
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);

        //Get the data from the calling activity
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

        //Get a handle on the TextView objects that will be used to display the data
        TextView categoryView = (TextView) findViewById(R.id.quote_category);
        TextView authorView = (TextView) findViewById(R.id.quote_whosaidit);
        TextView linkView = (TextView) findViewById(R.id.quote_link);
        TextView dateView = (TextView) findViewById(R.id.quote_added);
        TextView quoteView = (TextView) findViewById(R.id.quote_text);

        //Display the data
        categoryView.setText(category);
        quoteView.setText(quote);
        authorView.setText(attribution);
        linkView.setText(url);
        dateView.setText(date);


        //Set the onclick listener on the Attribution
        final Context context = this;
        final String displayedBlurb = blurb;
        authorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Blurb").setMessage(displayedBlurb);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        //Linkify the TextView for the URL
        Linkify.addLinks(linkView, Linkify.WEB_URLS);
        

        //Save to SharedPreferences for the "Last Run"
        SharedPreferences pref = getSharedPreferences("EvilQuotes", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString("categoryPrefs", category);
        editor.putString("quotePrefs", quote);
        editor.putString("blurbPrefs", blurb);
        editor.putString("attributionPrefs", attribution);
        editor.putString("datePrefs", date);
        editor.putString("urlPrefs", url);
        editor.commit();

    }


}
