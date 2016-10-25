package com.quotes.com.qtnvrquotes.beans;

import java.util.Date;

/**
 * Created by MDThai on 10/24/2016.
 */

public class QuoteBean {
    private String category;
    private String blurb;
    private String quote;
    private Date date;
    private String reference;

    public QuoteBean(){

    }

    public QuoteBean(String category, String blurb, String quote, String reference, Date date){
        this.category = category;
        this.blurb = blurb;
        this.quote = quote;
        this.reference = reference;
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBlurb() {
        return blurb;
    }

    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
