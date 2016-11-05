package com.quotes.com.qtnvrquotes;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.quotes.com.qtnvrquotes.adapters.CustomViewAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Shows the categories from a FireBase database
 *
 * @author Thai-Vu Nguyen, Victor Ruggi
 * @version 10/30/2016
 */
public class MainActivity extends BaseActivity {

    private final static String TAG = MainActivity.class.getName();
    private FirebaseAuth.AuthStateListener mAuthListener;
    protected List<String> categories;

    /**
     * onCreate() lifecycle method
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate() triggered");
        super.onCreate(savedInstanceState);
        categories = new ArrayList<>();
        setContentView(R.layout.activity_main);
        setUpAuthenticationListener();

    }

    /**
     * onStart() lifecycle method
     * Will perform Login and loading of the category
     */
    @Override
    public void onStart(){
        Log.i(TAG, "onStart() triggered");
        super.onStart();
        if (mFirebaseUser == null)
            performBackEndLogin();
        else
            loadCategories();

    }

    /**
     * Sets up an AuthStateListener
     */
    private void setUpAuthenticationListener(){
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
                if (mFirebaseUser !=null)
                {
                    Log.i(TAG, "User is signed in - " + mFirebaseUser.getUid());
                }
                else
                {
                    Log.i(TAG, "User is signed out");
                }

            }
        };
    }

    /**
     * Logs in to a Firebase account
     */
    private void performBackEndLogin(){
        mFirebaseAuth.addAuthStateListener(mAuthListener);
        mFirebaseAuth.signInWithEmailAndPassword("thaivictor_quotes@admin.com", "evilquotesoflove").addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "back-end sign in reached");

                if(!task.isSuccessful()) {
                    Log.i(TAG, "back-end sign in failed");

                }
                else {
                    Log.i(TAG, "back-end sign in succeeded");
                    loadCategories();
                }
            }
        });


    }

    /**
     * Load the categories in the ListView
     */
    private void loadCategories(){
        //final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.category_list, R.id.categoryItem);
        final ListView lv = (ListView) findViewById(R.id.categoryListView);

        final MainActivity currentActivity = this;

        Query query = mDatabase.getReference().child("categories");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                categories = new ArrayList<String>();
                Log.i(TAG, "Data Snap - Getting the Categories" );
                Log.i(TAG, "Data Snap Shot count - " + dataSnapshot.getChildrenCount());

                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();

                //Fetch each category and add to the list
                while(iterator.hasNext()){
                    String category = iterator.next().getKey();
                    Log.i(TAG, "Category fetched - " + category);

                    categories.add(category);
                }
                Log.i(TAG, "List size - " + categories.size());

                //Set the adaptor
                lv.setAdapter(new CustomViewAdapter(currentActivity, categories, dataSnapshot));
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

    }

    /**
     * Will log out of the account
     */
    @Override
    public void onStop(){
        mFirebaseAuth.signOut();
        super.onStop();

    }



}
