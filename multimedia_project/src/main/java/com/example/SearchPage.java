package com.example;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
public class SearchPage {
    public static class BookTitleComparator implements Comparator<Book> {

        @Override
        public int compare(Book book1, Book book2) {
            // Compare books based on their titles
            return book1.getTitle().compareToIgnoreCase(book2.getTitle());
        }
    }

    public static void loadsearchPage(TextField searchbar, TextField searchbar_writer, TextField searchbar_year,Stage primaryStage,VBox root,User currentUser,GridPane maingrid, Scene loginscene, Scene mainScene, TextField isbnfield, Scene adminScene) {
       //search page
        // Create a VBox to hold the content for the search section
        VBox searchContent = new VBox();
        List<Book> books = Serialize.readAllBooks();
         Collections.sort(books, new BookTitleComparator());
        List<Book> searchbooks = new ArrayList<>();
        if(currentUser.getusername().equals("Admin")) {
            if(isbnfield.getText().isEmpty()) {
                searchbooks=books;
            }
            else {
                for (Book b : books) {
                    int isbn = Integer.parseInt(isbnfield.getText()); // Convert ISBN to int
                    if (String.valueOf(b.getISBN()).contains(String.valueOf(isbn)) && (!searchbooks.contains(b))) {
                        searchbooks.add(b);
                    }
                }
                
        }
    }
        else {
        if(searchbar.getText().isEmpty() && searchbar_writer.getText().isEmpty() && searchbar_year.getText().isEmpty()) {
            searchbooks=books;
            System.out.println("Empty search");
        }
        if(!searchbar.getText().isEmpty()) {
            System.out.println(searchbar.getText() + " search");
            for(Book b: books) {
                if(b.getTitle().toLowerCase().contains(searchbar.getText().toLowerCase()) && (!searchbooks.contains(b))) {
                        searchbooks.add(b);
                    
                    
                }
            }
        }
        if(!searchbar_writer.getText().isEmpty()) {
            System.out.println(searchbar_writer.getText() + " search");
            for(Book b: books) {
                if(b.getWriter().toLowerCase().contains(searchbar_writer.getText().toLowerCase()) && (!searchbooks.contains(b))) {
                        searchbooks.add(b);
                    
                    
                    
                }
            }
        }
        if(!searchbar_year.getText().isEmpty()) {
            System.out.println(searchbar_year.getText() + " search");
            for(Book b: books) {
                if(Integer.toString(b.getYear_of_Publish()).contains(searchbar_year.getText().toLowerCase()) && (!searchbooks.contains(b))) {
                        searchbooks.add(b);
                    
                    
                }
            }
        }
    }
        // Add content for each book
        if(!searchbooks.isEmpty()) {
        for (int i = 0; i < searchbooks.size(); i++) {
            Book book = searchbooks.get(i);
            VBox bookContainer = new VBox(); // VBox for each book
            Label booktitleLabel = new Label("Title: "+ book.getTitle());
            Label bookwriterLabel = new Label("Writer: "+ book.getWriter());
            Label bookisbnLabel = new Label("ISBN: " + Integer.toString(book.getISBN()));
            String avgRatingText = String.format("%.2f", book.averagerating());
            Label bookavgratingLabel = new Label("User Rating: "+ avgRatingText);
            Label totalratesLabel = new Label("Total Ratings:"  + Integer.toString(book.gettotalratings()));
            Label whitespaceLabel = new Label("\n");
            double x1 = 50;
        double y1 = 100;
        double x2 = 250;
        double y2 = 100;
        Line line = new Line(x1, y1, x2, y2);

        // Set the line color
        line.setStroke(Color.BLUE);
 
            // Add content to the book container
            bookContainer.getChildren().addAll(booktitleLabel, bookwriterLabel, bookisbnLabel,
                                                bookavgratingLabel, totalratesLabel, whitespaceLabel);
            searchContent.getChildren().add(line);
            // Add mouse click event handler to navigate to another page
            bookContainer.setOnMouseClicked(event -> {
                // Perform navigation action here
                System.out.println("Clicked on book: " + book.getTitle());
                //book page

 GridPane bookpagegrid = new GridPane();
 
 BookPage.loadbookpage(book,bookpagegrid,primaryStage,currentUser,maingrid,loginscene,searchbar,searchbar_writer,searchbar_year,mainScene,adminScene);
 Scene bookpageScene = new Scene(bookpagegrid, 1000, 500);
 bookpageScene.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());
 //end book  page
                primaryStage.setScene(bookpageScene);
            });
            
            // Add the book container to the search content
            searchContent.getChildren().add(bookContainer);
        }
    }
    else {
        System.out.println("No results for search");
    }
        // Create a ScrollPane for the search section
        ScrollPane searchScrollPane = new ScrollPane(searchContent);
        searchScrollPane.setFitToHeight(true);
        searchScrollPane.setPrefViewportHeight(200); // Set preferred viewport height
        searchScrollPane.setPrefViewportWidth(500);
        
       
        root.getChildren().add(searchScrollPane);
        
        // Create the scene with the main layout
        
        
        
        //end search page
}


    }

