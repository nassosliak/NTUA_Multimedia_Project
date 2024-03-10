package com.example;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class MyBooksPage {
    public static void loadmybookspage(User currentUser, GridPane mybooksgrid, Stage primaryStage, Scene mybooksScene, Scene loginscene, TextField searchbar, TextField searchbar_writer, TextField searchbar_year, Scene mainScene, GridPane maingrid,Scene adminScene) {
        VBox searchContent = new VBox();
        List<Book> books = Serialize.readAllBooks();
        Button mainpagenavButton = new Button();
        Image iconImage = new Image(MainPage.class.getResourceAsStream("resources/arrow_back_FILL0_wght400_GRAD0_opsz24.png"));
        mainpagenavButton.setGraphic(new ImageView(iconImage));
        mainpagenavButton.getStyleClass().add("mainpagenavButton");
        mybooksgrid.add(mainpagenavButton,0,0);
        Label mybookstitle = new Label("Borrowed Books");
    
        mybooksgrid.add(mybookstitle,1,0);
        mybookstitle.setStyle("-fx-font-size: 28px;");
        GridPane.setMargin(mybookstitle, new Insets(0, 0, 0, 30));
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
            Label bookisbnLabel = new Label("ISBN: " + (book.getISBN()));
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
            
            bookContainer.setOnMouseClicked(event -> {
                
                System.out.println("Clicked on book: " + book.getTitle());
                

                GridPane bookpagegrid = new GridPane();
                Scene bookpageScene = new Scene(bookpagegrid, 1400, 700);
                bookpageScene.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());
 BookPage.loadbookpage(book,bookpagegrid,primaryStage,currentUser,maingrid,loginscene,searchbar,searchbar_writer,searchbar_year,mainScene,adminScene,bookpageScene);
 
 primaryStage.setScene(bookpageScene);
            });
            searchContent.getChildren().add(bookContainer);
        }
    }
    else {
        ;
    }
        
        ScrollPane searchScrollPane = new ScrollPane(searchContent);
        searchScrollPane.setPrefViewportHeight(200);
        searchScrollPane.setPrefViewportWidth(500);
        
       
        mybooksgrid.add(searchScrollPane,1,1);
        mybooksgrid.setAlignment(Pos.CENTER);
        mainpagenavButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                primaryStage.setScene(mainScene);
            }
        });
    }
}
