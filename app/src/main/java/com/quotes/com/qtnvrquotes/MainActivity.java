package com.quotes.com.qtnvrquotes;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.quotes.com.qtnvrquotes.adapters.CustomViewAdapter;
import com.quotes.com.qtnvrquotes.beans.QuoteBean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MainActivity extends BaseActivity {

    private final static String TAG = MainActivity.class.getName();
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpAuthenticationListener();
        if (mFirebaseUser == null)
            performAnonymousLogin();
        loadCategories();

    }

    @Override
    public void onStart(){
        super.onStart();


    }

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

    private void performAnonymousLogin(){
        mFirebaseAuth.addAuthStateListener(mAuthListener);
        mFirebaseAuth.signInWithEmailAndPassword("thaivictor_quotes@admin.com", "evilquotesoflove").addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "back-end sign in reached");

                if(!task.isSuccessful()) {
                    Log.i(TAG, "back-end sign in failed");

                }
                else
                    Log.i(TAG, "back-end sign in succeeded");
            }
        });


    }

    private void loadCategories(){
        //final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.category_list, R.id.categoryItem);
        final ListView lv = (ListView) findViewById(R.id.categoryListView);

        final MainActivity currentActivity = this;

        //lv.setAdapter(arrayAdapter);


        //final List<String> categories = new ArrayList<>();

        Query query = mDatabase.getReference().child("categories");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i(TAG, "Data Snap - Getting the Categories" );
                Log.i(TAG, "Data Snap Shot count - " + dataSnapshot.getChildrenCount());


                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();

                while(iterator.hasNext()){
                    String category = iterator.next().getKey();
                    Log.i(TAG, "Category fetched - " + category);
                    //IGNORE those lines for now
                    categories.add(category);
                    //arrayAdapter.add(category);

                }
                Log.i(TAG, "List size - " + categories.size());

                lv.setAdapter(new CustomViewAdapter(currentActivity, categories, dataSnapshot));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onStop(){

        super.onStop();
    }



}
