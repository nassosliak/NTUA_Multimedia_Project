package com.example;
import java.io.Serializable;

public class Comment implements Serializable{
    public String username;
    public String data;
    Comment(String username, String data) {
        this.username=username;
        this.data=data;
    }
    public String getdata() {
        return data;
    }
    public String getusername() {
        return username;
    }
    @Override
    public String toString() {
        return "Comment by "+ username+'\n'+data;
    }
}
