package com.example;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
public class User implements Serializable{
    private String firstname;
    private String username;
    private String lastname;
    private String password;
    private String email;
    private String ID;
    public static int number_of_users;
    public List<LocalDateTime> borrowingDates = new ArrayList<>();
    public List<Book> borrowedbooks= new ArrayList<>();
        User() {
}
        User(String firstname, String lastname, String username, String password, String email, String ID,List<Book> borrowedbooks){
        this.firstname=firstname;
        this.lastname=lastname;
        this.username=username;
        this.password=password;
        this.email=email;
        this.ID=ID;
        ++number_of_users;
        if (borrowedbooks == null) {
            borrowedbooks = new ArrayList<>();
        }
        else {
            this.borrowedbooks=borrowedbooks;
        }

        }
        
        public void setUsername(String username) {
            this.username = username;
        }
        public void setPassword(String password) {
            this.password = password;
        }
        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }
        public void setLastname(String lastname) {
            this.lastname = lastname;
        }
        public void setEmail(String email) {
            this.email = email;
        }
        public void setId(String ID) {
            this.ID=ID;
        }
        
    public String getfirstname() {
        return firstname;
    }

    public String getlastname() {
        return lastname;
    }

    public String getusername() {
        return username;
    }

    public String getpassword() {
        return password;
    }

    public String getemail() {
        return email;
    }

    public String getID() {
        return ID;
    }
    public static int getNumberofUsers() {
        return number_of_users;
    }
    public int number_of_borrowed_books() {
        if(getBorrowedBooks()==null) return 0;
        else
        return getBorrowedBooks().size();
    }
   
    
    public List<LocalDateTime> getBorrowingDates() {
            return borrowingDates;
    }
    
    public List<Book> getBorrowedBooks(){

        if(borrowedbooks==null) {
           List<Book>newborrowedbooks= new ArrayList();
            return newborrowedbooks;}
        return borrowedbooks;
    }
    @Override
public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("User{")
            .append("firstname='").append(firstname).append('\n')
            .append(", lastname='").append(lastname).append('\n')
            .append(", username='").append(username).append('\n')
            .append(", password='").append(password).append('\n')
            .append(", email=").append(email).append('\n')
            .append(", ID='").append(ID).append('\n')
            .append(", borrowedbooks=\n");

    if (borrowedbooks != null) {
        for (Book book : borrowedbooks) {
            stringBuilder.append(book.toString()).append('\n');
        }
    } else {
        stringBuilder.append("No books borrowed\n");
    }

    stringBuilder.append('}');

    return stringBuilder.toString();
}

}
