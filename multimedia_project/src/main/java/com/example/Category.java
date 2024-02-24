package com.example;
import java.io.Serializable;

public class Category implements Serializable{
    private String Title;
    Category(String Title) {
        this.Title=Title;
    }
    @Override
    public String toString() {
        return "Categories{" +
                "title='" + Title + '\'' +
                '}';
    }
    public String getTitle() {
        return Title;
    }
    public void setTitle(String newCategoryName) {
       Title=newCategoryName;
    }
}
