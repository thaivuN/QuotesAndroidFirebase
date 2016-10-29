package com.quotes.com.qtnvrquotes.beans;

/**
 * Created by MDThai on 10/24/2016.
 */

public class QuoteBean {
    private String attribution;
    private String category;
    private String blurb;
    private String quote;
    private String date;
    private String url;

    public QuoteBean(){

        this("","","", "", "", null);
    }

    public QuoteBean(String attribution, String category, String blurb, String quote, String url, String date){
        this.attribution = attribution;
        this.category = category;
        this.blurb = blurb;
        this.quote = quote;
        this.url = url;
        this.date = date;
    }

    public String getAttribution() {
        return attribution;
    }

    public void setAttribution(String attribution) {
        this.attribution = attribution;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
