package com.quotes.com.qtnvrquotes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Base Class to give basic functionality to all Activities.
 * Will give activities that extends this Activity class a common menu
 * Created by Thai-Vu Nguyen on 10/26/2016.
 */
public class BaseActivity extends AppCompatActivity {

    protected List<String> categories;
    protected FirebaseAuth mFirebaseAuth;
    protected FirebaseUser mFirebaseUser;
    protected FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        if (categories == null)
            this.categories = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.about_menu:
                Intent i = new Intent(this, AboutActivity.class);
                startActivity(i);
                return true;
            case R.id.random_menu:

                return true;
            case R.id.last_run_menu:
                SharedPreferences sharedPreferences = getSharedPreferences("EvilQuotes", MODE_PRIVATE);


                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        savedInstanceState.putStringArrayList("categories", (ArrayList<String>) categories);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        categories = outState.getStringArrayList("categories");
    }
}
