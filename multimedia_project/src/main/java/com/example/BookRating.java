package com.example;
import java.io.Serializable;

public class BookRating implements Serializable{
    public String username;
    public Integer rating;
    BookRating(String username, Integer rating) {
        this.username=username;
        this.rating=rating;
    }
    public Integer getrating() {
        return rating;
    }
    public String getusername() {
        return username;
    }
    @Override
    public String toString() {
        return "Rating by "+ username+'\n'+rating;
    }
    
}

