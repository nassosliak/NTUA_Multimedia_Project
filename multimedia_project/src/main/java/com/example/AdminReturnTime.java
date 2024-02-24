package com.example;
import java.io.IOException;
import java.io.Serializable;

public class AdminReturnTime implements Serializable{
    public int date;
    public AdminReturnTime returntime() throws IOException, ClassNotFoundException {
    return Serialize.readreturntime();
}
    AdminReturnTime(int date) {
        this.date=date;
    }
    public int getdate() {
        return date;
    }
    public void setdate(int date) {
        this.date=date;
    }
}
