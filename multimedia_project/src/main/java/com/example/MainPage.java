package com.example;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainPage {
    private static void toggleTextField(GridPane gridPane, int row, boolean addTextField, TextField textField) {
        if (addTextField) {
            gridPane.add(textField,2,row);
        } else {
            gridPane.getChildren().remove(textField);
        }
    }
    public class BookRatingComparator implements Comparator<Book> {

    @Override
    public int compare(Book book1, Book book2) {
        // Compare books based on their average rating
        return Double.compare(book2.averagerating(), book1.averagerating());
    }
}
    public static void updateMainGrid(GridPane maingrid, List<Book> updatedBooks, Stage primaryStage,Scene loginscene, User currentUser,
    TextField searchbar,TextField searchbar_writer, TextField searchbar_year, Scene mainScene, Scene adminScene) {
        

List<Book> books=Serialize.readAllBooks();

maingrid.getChildren().clear();
        if(currentUser!=null) {
        Text basic =new Text("Hi "+ currentUser.getusername());
        basic.setId("mainpagetitle");
        maingrid.add(basic,0,1);
        }
        Text title = new Text("Library");
        title.setId("title");
        maingrid.add(title,0,0);
        RadioButton checkBoxTitle = new RadioButton("Search by Title");
        RadioButton checkBoxWriter = new RadioButton("Search by Writer");
        RadioButton checkBoxYear = new RadioButton("Search by Year");


        checkBoxTitle.setOnAction(e -> toggleTextField(maingrid, 2, checkBoxTitle.isSelected(), searchbar));
        checkBoxWriter.setOnAction(e -> toggleTextField(maingrid, 3,checkBoxWriter.isSelected(), searchbar_writer));
        checkBoxYear.setOnAction(e -> toggleTextField(maingrid, 4,checkBoxYear.isSelected(), searchbar_year));
        GridPane.setMargin(checkBoxTitle, new Insets(10, 10, 10, 10)); // Adjust the insets as needed
GridPane.setMargin(checkBoxWriter, new Insets(10, 10, 10, 10));
GridPane.setMargin(checkBoxYear, new Insets(10, 10, 10, 10));
        maingrid.add(checkBoxTitle,0,2);
        maingrid.add(checkBoxWriter,0,3);
        maingrid.add(checkBoxYear,0,4);
    
        Text top = new Text("Top Rated Books");
        maingrid.add(top,0,5);
        top.setId("top");
        GridPane.setMargin(top, new Insets(10, 10, 10, 10));
        Button mybooksButton = new Button("My books");
        maingrid.add(mybooksButton,10,3);
        Text borrowdbooksText = new Text("Borrowed books");
        maingrid.add(borrowdbooksText,12,0);
        
        if(currentUser!=null && currentUser.getBorrowedBooks()!=null){
        int rowindex_Borrow=1;
        for(int i=0; i<currentUser.number_of_borrowed_books(); i++) {
            Text bbtText= new Text(currentUser.getBorrowedBooks().get(i).getTitle()+", return time "+currentUser.getBorrowingDates().get(i).plusDays(5));
            maingrid.add(bbtText, 12, rowindex_Borrow);
            rowindex_Borrow++;
        }
    }
    else {
        System.out.println("No username");
    }
    
        

      VBox root = new VBox();
      root.setAlignment(Pos.CENTER);
      Scene searchScene = new Scene(root, 1400, 700);
      searchScene.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());


        Button signoutButton = new Button("Sign out");
        maingrid.add(signoutButton,10,2);
        int RowIndex = 10;
        int rowcounter=0;
        MainPage mainPageInstance = new MainPage(); // Create an instance of MainPage
        Collections.sort(books, mainPageInstance.new BookRatingComparator());
        HBox bookContainer = new HBox();
bookContainer.setSpacing(10);
        
        for (Book book : books) {
        if(rowcounter==5) break;
        rowcounter++;
        VBox bookVBox = new VBox();
    bookVBox.setSpacing(5);
        Text titleText = new Text("Title: " + book.getTitle());
        Text writerText = new Text("Writer: " + book.getWriter());
        Text publisherText = new Text("Publisher: " + book.getPublisher());
        Text isbnText = new Text("ISBN: " + book.getISBN());
        Text yearText = new Text("Year of Publish: " + book.getYear_of_Publish());
        Text categoryText = new Text("Category: " + book.getCategory());
        Text numberofbooksText = new Text("Available books: " + book.getNumberofBooks());
        
        maingrid.add(titleText, 0, 0+RowIndex);
        maingrid.add(writerText, 0, 1+RowIndex);
        maingrid.add(publisherText, 0, 2+RowIndex);
    maingrid.add(isbnText, 0, 3+RowIndex);
    maingrid.add(yearText, 0, 4+RowIndex);
    maingrid.add(categoryText, 0, 5+RowIndex);
    maingrid.add(numberofbooksText,0,6+RowIndex);
    Ratingfx rating = new Ratingfx(5);
    rating.setRating((int)Math.round(book.averagerating()));
    maingrid.add(rating, 1, 6 + RowIndex);
    /*
    if (book.bookcomments != null && !book.bookcomments.isEmpty()) {
        for(Comment c: book.bookcomments) {
            Text com = new Text(c.toString());
            maingrid.add(com,0,7+RowIndex);
        }
    }
    */
    
    float averageRating = book.averagerating();
    String avgRatingText = String.format("%.2f", averageRating);
    Text avgRate = new Text("Average Rating: "+avgRatingText);
    //maingrid.add(avgRate,2,6+RowIndex);
    String totalRatingsText = String.format("%d", book.gettotalratings());
    Text totalRatings = new Text("Total Ratings: "+totalRatingsText);
    //maingrid.add(totalRatings,0,9+RowIndex);
    RowIndex+=10;
    bookVBox.getChildren().addAll(titleText, writerText, publisherText, isbnText, yearText, categoryText,
                    numberofbooksText,avgRate,rating,totalRatings);

            bookContainer.getChildren().add(bookVBox);
            bookVBox.setOnMouseClicked(event -> {
                
                System.out.println("Clicked on book: " + book.getTitle());
                GridPane bookpagegrid = new GridPane();
                Scene bookpageScene = new Scene(bookpagegrid, 1000, 500);
                bookpageScene.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());
                BookPage.loadbookpage(book, bookpagegrid, primaryStage, currentUser, maingrid, loginscene, searchbar, searchbar_writer, searchbar_year, mainScene, adminScene, bookpageScene);
                primaryStage.setScene(bookpageScene);

            });
}
ScrollPane scrollPane = new ScrollPane();
scrollPane.setContent(bookContainer);
scrollPane.setPrefWidth(600);
scrollPane.setPrefHeight(250);
maingrid.add(scrollPane, 0, 10);
        Button searchButton = new Button("Search");
        HBox hsearchButton = new HBox(10);
        hsearchButton.setAlignment(Pos.BOTTOM_RIGHT);
        hsearchButton.getChildren().add(searchButton);
        maingrid.add(searchButton,3, 2);
        //My books page
        GridPane mybooksgrid = new GridPane();
        
        Scene mybooksScene = new Scene(mybooksgrid,1400,700);
        mybooksScene.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());
        MyBooksPage.loadmybookspage(currentUser,mybooksgrid,primaryStage,mybooksScene,loginscene,searchbar,searchbar_writer,searchbar_year,mainScene,maingrid,adminScene);

        //end my books page
       
        
signoutButton.setOnAction(new EventHandler<ActionEvent>() {


            @Override
            public void handle(ActionEvent ev) {
                
                primaryStage.setScene(loginscene);
            }
        });

        mybooksButton.setOnAction(new EventHandler<ActionEvent>() {


            @Override
            public void handle(ActionEvent ev) {
                
                primaryStage.setScene(mybooksScene);
            }
        });
        
        searchButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent a) {
                updateMainGrid(maingrid, updatedBooks, primaryStage, loginscene, currentUser,searchbar,searchbar_writer,searchbar_year,mainScene,adminScene);
                SearchPage.loadsearchPage(searchbar,searchbar_writer,searchbar_year,primaryStage,root,currentUser,maingrid,loginscene,mainScene,null,adminScene);
                primaryStage.setScene(searchScene);
            }
        });

}


}
