package com.example;
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

public class MyBooksPage {
    public static void loadmybookspage(User currentUser, GridPane mybooksgrid, Stage primaryStage, Scene mybooksScene, Scene loginscene, TextField searchbar, TextField searchbar_writer, TextField searchbar_year, Scene mainScene, GridPane maingrid) {
        VBox searchContent = new VBox();
        List<Book> books = Serialize.readAllBooks();
        if(!books.isEmpty() &&currentUser!=null) {
        String username = currentUser.getusername();
        User currentuser=null;
        List<User> users = Serialize.readAllUsers();
        for(User u: users){
            if(u.getusername().equals(username)) {
                currentuser=u;
                break;
            }
        }

        for (int i = 0; i < currentuser.number_of_borrowed_books(); i++) {
            Book book = currentuser.getBorrowedBooks().get(i);
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
 
 BookPage.loadbookpage(book,bookpagegrid,primaryStage,currentUser,maingrid,loginscene,searchbar,searchbar_writer,searchbar_year,mainScene);
 Scene bookpageScene = new Scene(bookpagegrid, 1000, 500);
 bookpageScene.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());
 primaryStage.setScene(bookpageScene);
            });
            searchContent.getChildren().add(bookContainer);
        }
    }
    else {
        System.out.println("No user");
    }
        
        ScrollPane searchScrollPane = new ScrollPane(searchContent);
        searchScrollPane.setPrefViewportHeight(200);
        searchScrollPane.setPrefViewportWidth(500);
        
       
        mybooksgrid.getChildren().add(searchScrollPane);
        
        // Create the scene with the main layout
        
        
        
        //end search page
    }
}
