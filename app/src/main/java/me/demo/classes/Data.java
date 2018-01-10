package me.demo.classes;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Rui Oliveira on 10/01/18.
 */

public class Data {

    @SerializedName("results")
    private ArrayList<User> results;

    public Data() {
    }

    public ArrayList<User> getResults() {
        return results;
    }

    public void setResults(ArrayList<User> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
