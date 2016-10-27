package com.quotes.com.qtnvrquotes.dao;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.quotes.com.qtnvrquotes.beans.QuoteBean;

/**
 * Created by MDThai on 10/24/2016.
 */

public class DAOAction {
    private DatabaseReference mDatabase;

    public DAOAction(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public QuoteBean getQuoteFromCategory(String categoryId){
        return null;
    }

    public String[] getCategories(){
        String[] categories = new String[0];

        return categories;
    }

}
