package com.example.melidemoapp.model;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by alejandro.gaidolfi on 13/07/20.
 */
public class Response {

    private String keywords;
    private ArrayList<Product> results;

    public ArrayList<Product> getResults() {
        return results;
    }

    public void setResults(ArrayList<Product> results) {
        this.results = results;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}
