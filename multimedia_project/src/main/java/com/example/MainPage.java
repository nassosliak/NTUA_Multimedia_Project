package com.example;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainPage {
    
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

        Text basic =new Text("Main Page");
        maingrid.add(basic,2,1);
        Label search = new Label("Search:");
        maingrid.add(search, 0, 2);
        maingrid.add(searchbar,1,2);
        maingrid.add(searchbar_writer,1,3);
        maingrid.add(searchbar_year,1,4);
        
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
      Scene searchScene = new Scene(root, 1000, 500);
      searchScene.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());


       
        

        Button signoutButton = new Button("Sign out");
        maingrid.add(signoutButton,10,2);
        int RowIndex = 10;
        int rowcounter=0;
        MainPage mainPageInstance = new MainPage(); // Create an instance of MainPage
        Collections.sort(books, mainPageInstance.new BookRatingComparator());


        for (Book book : books) {
        if(rowcounter==5) break;
        rowcounter++;
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
    if (book.bookcomments != null && !book.bookcomments.isEmpty()) {
        for(Comment c: book.bookcomments) {
            Text com = new Text(c.toString());
            maingrid.add(com,0,7+RowIndex);
        }
    }
    float averageRating = book.averagerating();
    String avgRatingText = String.format("%.2f", averageRating);
    Text avgRate = new Text(avgRatingText);
    maingrid.add(avgRate,0,8+RowIndex);
    String totalRatingsText = String.format("%d", book.gettotalratings());
    Text totalRatings = new Text(totalRatingsText);
    maingrid.add(totalRatings,0,9+RowIndex);
    RowIndex+=10;
    
}

        Button searchButton = new Button("Search");
        HBox hsearchButton = new HBox(10);
        hsearchButton.setAlignment(Pos.BOTTOM_RIGHT);
        hsearchButton.getChildren().add(searchButton);
        maingrid.add(searchButton, 2, 2);
        //My books page
        GridPane mybooksgrid = new GridPane();
        
        Scene mybooksScene = new Scene(mybooksgrid,1000,500);
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
