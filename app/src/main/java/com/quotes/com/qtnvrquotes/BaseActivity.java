package com.quotes.com.qtnvrquotes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.quotes.com.qtnvrquotes.beans.QuoteBean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

/**
 * Base Class to give basic functionality to all Activities.
 * Will give activities that extends this Activity class a common menu
 * @author Thai-Vu Nguyen, Victor Ruggi
 * @version 10/30/2016
 */
public class BaseActivity extends AppCompatActivity {

    private final static String TAG = BaseActivity.class.getName();
    protected FirebaseAuth mFirebaseAuth;
    protected FirebaseUser mFirebaseUser;
    protected FirebaseDatabase mDatabase;
    protected QuoteBean randomQuote;

    /**
     * onCreate(Bundle bundle) lifecycle method
     * @param bundle Bundle
     */
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);

        mDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
    }

    /**
     * Loads the menu in the UI to all Activities that extends this class
     * @param menu Menu
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }



    /**
     * Setting up action on the selection of a menu item
     * @param item MenuItem
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.about_menu:
                Log.i(TAG, "Menu Item: About");

                //The about option selection
                Intent aboutIntent = new Intent(this, AboutActivity.class);

                //Launching AboutActivity
                startActivity(aboutIntent);
                return true;
            case R.id.random_menu:

                Log.i(TAG, "Menu Item: Random");
                Query query = mDatabase.getReference().child("categories");

                //Fetch data from FireBase
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //Fetching random category
                        String category = getRandomCategory(dataSnapshot);

                        Log.i(TAG, "Random Category chosen: " + category);

                        DataSnapshot quotesSnap = dataSnapshot.child(category);

                        //Fetching the random QuoteBean
                        randomQuote = getRandomQuote(dataSnapshot, category);
                        Log.i(TAG, "random QuoteBean object fetched: " + randomQuote);

                        Intent randomQuoteIntent = new Intent(BaseActivity.this, QuoteActivity.class);

                        //Setting up Intent extras
                        randomQuoteIntent.putExtra("categoryExtra", randomQuote.getCategory());
                        randomQuoteIntent.putExtra("attributionExtra", randomQuote.getAttribution());
                        randomQuoteIntent.putExtra("blurbExtra", randomQuote.getBlurb());
                        randomQuoteIntent.putExtra("quoteExtra", randomQuote.getQuote());
                        randomQuoteIntent.putExtra("dateExtra", randomQuote.getDate());
                        randomQuoteIntent.putExtra("urlExtra",randomQuote.getUrl());

                        //Launching QuoteActivity with the random Quote
                        startActivity(randomQuoteIntent);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, "onCancelled called - Unexpected Error ");
                        Log.e(TAG, "Code : " + databaseError.getCode()
                                + " - Details : " + databaseError.getDetails()
                                + " - Message : " + databaseError.getMessage()
                        );
                    }
                });

                return true;
            case R.id.last_run_menu:
                Log.i(TAG, "Menu Item: Last Run");
                //The last quote chosen Option
                SharedPreferences sharedPreferences = getSharedPreferences("EvilQuotes", MODE_PRIVATE);
                if (sharedPreferences.contains("categoryPrefs")) {
                    //Setting up Intent extras
                    Intent lastQuoteIntent = new Intent(this, QuoteActivity.class);
                    lastQuoteIntent.putExtra("categoryExtra", sharedPreferences.getString("categoryPrefs",""));
                    lastQuoteIntent.putExtra("attributionExtra", sharedPreferences.getString("attributionPrefs", ""));
                    lastQuoteIntent.putExtra("blurbExtra", sharedPreferences.getString("blurbPrefs", ""));
                    lastQuoteIntent.putExtra("quoteExtra", sharedPreferences.getString("quotePrefs", ""));
                    lastQuoteIntent.putExtra("dateExtra", sharedPreferences.getString("datePrefs", ""));
                    lastQuoteIntent.putExtra("urlExtra", sharedPreferences.getString("urlPrefs", ""));
                    //Start the quote activity
                    startActivity(lastQuoteIntent);
                }
                else
                {
                    //Could happen if QuoteActivity was never launched by the user
                    //Display a message
                    Toast toast = Toast.makeText
                            (this, getResources().getString(R.string.last_run_error),
                                    Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0,0);
                    toast.show();
                }
                return true;
            default:
                return false;
        }
    }

    /**
     * Fetches a random category in the Firebase database
     * @param categoriesSnap DataSnapshot
     * @return a random category in String data type
     */
    private String getRandomCategory(DataSnapshot categoriesSnap){
        int category_length = (int) categoriesSnap.getChildrenCount();
        Iterator<DataSnapshot> snapshotIterator = categoriesSnap.getChildren().iterator();
        List<String> categoriesFetched = new ArrayList<>();

        //Fetch all categories into a list
        while (snapshotIterator.hasNext()){
            categoriesFetched.add(snapshotIterator.next().getKey());
        }
        //Getting a random index based on the number of categories fetched
        int random_index = (int) (Math.random() * categoriesFetched.size());
        //Return the random category
        return categoriesFetched.get(random_index);

    }

    /**
     * Returns a random quote from a category
     *
     * @param categoriesSnap DataSnapshot
     * @param category String
     * @return a random quote from a category in String data type
     */
    private QuoteBean getRandomQuote(DataSnapshot categoriesSnap, String category){

        DataSnapshot quotesSnap = categoriesSnap.child(category);
        int quote_length = (int) quotesSnap.getChildrenCount();
        Iterator<DataSnapshot> snapshotIterator = quotesSnap.getChildren().iterator();
        List<QuoteBean> quotesFetched = new ArrayList<>();

        //Fetching all quotes into a list
        while (snapshotIterator.hasNext()){
            DataSnapshot currentQuoteSnap = snapshotIterator.next();
            QuoteBean quote = new QuoteBean();
            quote.setCategory(category);
            quote.setAttribution((String)currentQuoteSnap.child("attribution").getValue());
            quote.setQuote((String)currentQuoteSnap.child("quote").getValue());
            quote.setUrl((String) currentQuoteSnap.child("url").getValue());
            quote.setBlurb((String)currentQuoteSnap.child("blurb").getValue());
            quote.setDate((String)currentQuoteSnap.child("date").getValue());
            quotesFetched.add(quote);

        }
        //Getting a random index based on the list of quotes fetched
        int random_index = (int) (Math.random() * quotesFetched.size());
        //Return the random quote
        return quotesFetched.get(random_index);

    }
}
