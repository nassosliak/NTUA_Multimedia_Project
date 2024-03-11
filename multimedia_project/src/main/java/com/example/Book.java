package com.example;

//Class for Books
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class Book implements Serializable{
    private String Title;
    private String Writer;
    private String Publisher;
    private String ISBN;
    private int Year_of_Publish;
    private String Category;
    public  int Number_of_Books;
    public List<Comment> bookcomments;
    public List<BookRating> bookratings;
    Book(String ISBN) {this.ISBN=ISBN; this.bookcomments = new ArrayList<>(); this.bookratings=new ArrayList<>();}
    Book(String Title,String Writer,String Publisher,String ISBN, int Year_of_Publish, String Category,int Number_of_Books) {
        this.Title=Title;
        this.Writer=Writer;
        this.Publisher=Publisher;
        this.ISBN=ISBN;
        this.Year_of_Publish=Year_of_Publish;
        this.Category=Category;
        this.Number_of_Books=Number_of_Books;
        this.bookcomments = new ArrayList<>();
        this.bookratings = new ArrayList<>();
    }
    private static final long serialVersionUID = 1L;
    public String getTitle() {
        return Title;
    }
    public String getWriter() {
        return Writer;
    }
    public String getPublisher() {
        return Publisher;
    }
    public String getISBN() {
        return ISBN;
    }
    public int getYear_of_Publish() {
        return Year_of_Publish;
    }
    public String getCategory() {
        return Category;
    }
    public int getNumberofBooks() {
        return Number_of_Books;
    }

    public int getnumberofcomments() {
        return bookcomments.size();
    }
    public void setNumberofBooks(int number) {
        Number_of_Books=number;
    }
    public void setTitle(String title) {
        Title=title;
    }
    public void setyearofpublish(int year) {
        Year_of_Publish=year;
    }
    public void setWriter(String writer) {
        Writer=writer;
    }
    public void setCategory(String category) {
        Category=category;
    }
    public void setPublisher(String publisher) {
        Publisher=publisher;
    }
    public void comment(String user, String data) {
        if (bookcomments == null) {
            
            bookcomments = new ArrayList<>();
        }
        Comment c= new Comment(user,data);
        System.out.println("Comment added succesfully");
        bookcomments.add(c);
        System.out.println(bookcomments);
    }
    public void rate(String user, int rate) {
        if (bookratings == null) {
            
            bookratings = new ArrayList<>();
        }
        BookRating br = new BookRating(user, rate);
        System.out.println("Rating added succesfully");
        bookratings.add(br);
        System.out.println(bookratings);
    }

    public List<Comment> getComments() {
        return bookcomments;
    }
    public float averagerating() {
        float result=0f;
        for(BookRating i:bookratings) {
            result+=i.getRating();
        }
        if(bookratings.size()>0)
        return result/bookratings.size();
        else return 0f;
    }

    public int gettotalratings() {
        return bookratings.size();
    }
    public static Map<String, List<Book>> groupBooksByCategory(List<Book> books) {
        Map<String, List<Book>> groupedBooks = new HashMap<>();
        
        // Iterate through each book
        for (Book book : books) {
            // Get the category of the current book
            String category = book.getCategory();
            
            // Check if the category already exists in the map
            if (!groupedBooks.containsKey(category)) {
                // If the category doesn't exist, create a new list for it
                groupedBooks.put(category, new ArrayList<>());
            }
            
            // Add the current book to the list corresponding to its category
            groupedBooks.get(category).add(book);
        }
        
        return groupedBooks;
    }
    @Override
    public String toString() {
        return "Book{" +
                "title=" + Title + '\'' +
                ", writer=" + Writer + '\'' +
                ", publisher=" + Publisher + '\'' +
                ", ISBN=" + ISBN + '\'' +
                ", yearOfPublish=" + Year_of_Publish +
                ", category=" + Category + '\'' +
                ", number of books=" + Number_of_Books + '\'' +
                ", comments=" +  bookcomments+ '\'' +
                ", ratings=" +  bookratings+ '\'' +
                '}'+'\n';
    }
}
