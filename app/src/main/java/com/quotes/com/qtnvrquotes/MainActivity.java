package com.quotes.com.qtnvrquotes;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends BaseActivity {

    private final static String TAG = MainActivity.class.getName();
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpAuthenticationListener();

    }

    @Override
    public void onStart(){
        super.onStart();
        performAnonymousLogin();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.category_list, R.id.categoryItem);
        ListView lv = (ListView) findViewById(R.id.categoryListView);

        lv.setAdapter(arrayAdapter);

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
        mFirebaseAuth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "signInAnonymously() reached");

                if(!task.isSuccessful()) {
                    Log.i(TAG, "Anonymous sign in failed");

                }
                else
                    Log.i(TAG, "Anonymous sign in succeeded");
            }

        });
    }
}
