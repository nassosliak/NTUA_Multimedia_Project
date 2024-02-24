package com.example;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BookPage {
    public static void loadbookpage(Book b,GridPane bookpagegrid, Stage primaryStage, User currentUser, GridPane maingrid, Scene loginscene, TextField searchbar, TextField searchbar_writer, TextField searchbar_year, Scene mainScene) {
        List<Book> books = Serialize.readAllBooks();
    Text booktexttitle= new Text(b.getTitle());
        bookpagegrid.add(booktexttitle,0,0);
        Text booktextwriter= new Text("Writer: " +b.getWriter());
        bookpagegrid.add(booktextwriter,0,1);
        Text booktextpublisher= new Text("Publisher: " + b.getPublisher());
        bookpagegrid.add(booktextpublisher,0,2);
        Text booktextcategory= new Text("Category: " + b.getCategory());
        bookpagegrid.add(booktextcategory,0,3);
        Text booktextisbn= new Text(Integer.toString(b.getISBN()));
        bookpagegrid.add(booktextisbn,0,4);
        Text booktextyear= new Text(Integer.toString(b.getYear_of_Publish()));
        bookpagegrid.add(booktextyear,0,5);
        String avgRatingText = String.format("%.2f", b.averagerating());
        Text bookavgrateText = new Text("Average User Rating: "+avgRatingText);
        bookpagegrid.add(bookavgrateText,0,7);
        Text bookavText = new Text("Available books: "+Integer.toString(b.getNumberofBooks()));
        bookpagegrid.add(bookavText,0,6);
        Text bookrates = new Text("Total User Ratings: "+Integer.toString(b.gettotalratings()));
        bookpagegrid.add(bookrates,0,8);
        int rowindex=2;
        for(int i=0; i<b.bookcomments.size(); i++) {
        String byuser = b.bookcomments.get(i).username;
        String data = b.bookcomments.get(i).data;
        Text c = new Text("Comment by "+ byuser + '\n' + data+'\n');
        bookpagegrid.add(c,0,10+rowindex);
        ++rowindex;
        Ratingfx rating = new Ratingfx(5);
        rating.setRating((int)Math.round(b.averagerating()));
        bookpagegrid.add(rating, 0, 9);
        }
        Button addcommentButton = new Button("Add comment");
        Button addratingButton = new Button("Add Rating");
        boolean bookcontained =false;
        if(currentUser.getBorrowedBooks()!=null) {
        for(Book book: currentUser.getBorrowedBooks()) {
            if(book.getISBN()==b.getISBN()) {
bookcontained=true;
break;
            }
        }
    }
        if(currentUser!=null && currentUser.getBorrowedBooks() != null && bookcontained){
        bookpagegrid.add(addcommentButton,4,4);
        bookpagegrid.add(addratingButton,4,5);
        }
        Button lendbookButton = new Button("Borrow");
        final Text lendlimit = new Text();
        bookpagegrid.add(lendlimit, 0, 6);
        bookpagegrid.setColumnSpan(lendlimit, 2);
        //bookpagegrid.setHalignment(lendlimit, RIGHT);
        lendlimit.setId("lendlimit");
        Button returnbookButton = new Button("Return");
        
        
        if (currentUser != null && currentUser.getBorrowedBooks() != null && bookcontained) {
            bookpagegrid.getChildren().remove(lendbookButton); // Remove lendbookButton if it exists
            bookpagegrid.add(returnbookButton, 4, 7); // Add returnbookButton at the specified position
        }
        
        if (currentUser != null && currentUser.getBorrowedBooks() != null && !bookcontained)  {
        bookpagegrid.getChildren().remove(returnbookButton);
        bookpagegrid.add(lendbookButton,4,7);
        }
        //addcomment page
        GridPane commentgrid = new GridPane();
        Text commentTitle = new Text("Add a comment");
        commentgrid.add(commentTitle,1,0);
        TextArea commentfield = new TextArea();
        commentfield.setPrefWidth(200);
        commentfield.setPrefHeight(400);
        commentgrid.add(commentfield,1,3);
        
        Button publishcommentButton = new Button("Publish Comment");
        commentgrid.add(publishcommentButton,2,3);
        Scene addcommentScene = new Scene(commentgrid, 1000, 500);
        addcommentScene.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());


        //add rating page
        GridPane ratinggrid = new GridPane();
        Text ratingTitle = new Text("Add a Rating");
        ratinggrid.add(ratingTitle,1,0);
        Spinner<Integer> spinner = new Spinner<>(1, 5, 0);
        ratinggrid.add(spinner,2,4);
        
        Button publishratingButton = new Button("Publish Rating");
        ratinggrid.add(publishratingButton,2,3);
        Scene addratingScene = new Scene(ratinggrid, 1000, 500);
        addratingScene.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());

        
        addcommentButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent a) {
                
                primaryStage.setScene(addcommentScene);
            }
        });
        addratingButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent a) {
                
                primaryStage.setScene(addratingScene);
            }
        });
        publishcommentButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent a) {
                
                for(Book book:books) {
                    if(book.getISBN()==b.getISBN()) {
                        book.comment(currentUser.getusername(),commentfield.getText());
                        System.out.println("Book found");
                        break;
                }
            }
                        try {
                            Serialize.writeAllBooks(books);
                            System.out.println("Serialized comment");
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    
                
                System.out.println("Comment added from "+ currentUser.getusername()+' '+commentfield.getText());
                MainPage.updateMainGrid(maingrid,books,primaryStage,loginscene,currentUser,searchbar,searchbar_writer,searchbar_year,mainScene);
                primaryStage.setScene(mainScene);
                
            }
        });
        
        publishratingButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent a) {
                int bookrating = spinner.getValue();

                for(Book book:books) {
                    if(book.getISBN()==b.getISBN()) {
                        book.rate(currentUser.getusername(),bookrating);
                        System.out.println("Book found");
                        break;
                }
            }
                        
                        try {
                            Serialize.writeAllBooks(books);
                            System.out.println("Serialized rating for book "+ b.getISBN());
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            System.out.println("Exception rating for book "+ b.getISBN());
                            e.printStackTrace();
                        }
                   
                
                System.out.println("Rating added from "+ currentUser.getusername()+' '+bookrating);
                MainPage.updateMainGrid(maingrid,books,primaryStage,loginscene,currentUser,searchbar,searchbar_writer,searchbar_year,mainScene);
                primaryStage.setScene(mainScene);

            }
        });
        lendbookButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent a) {
                List<Book> books = Serialize.readAllBooks();
                        
                        boolean bookFound = false;
                        if(currentUser!=null) {
                        for (Book book : books) {
                            if(book.getISBN()==b.getISBN()) {
                                                        
                                bookFound = true;
                                
                                if (currentUser.number_of_borrowed_books() < 2) {
                                    if(currentUser.getBorrowedBooks()==null) {
                                        currentUser.borrowedbooks= new ArrayList();
                                      }
                                        if(book.getNumberofBooks()>0) {
                                            if(currentUser.getBorrowedBooks().size()==2) {
                                            if(currentUser.borrowedbooks.get(0).getISBN()==book.getISBN() ||
                                            currentUser.borrowedbooks.get(1).getISBN()==book.getISBN()) {
                                                System.out.println("Book "+book.getISBN()+" already borrowed");
                                                break;
                                            }
                                        }
                                        if(currentUser.getBorrowedBooks().size()==1) {
                                            if(currentUser.borrowedbooks.get(0).getISBN()==book.getISBN()) {
                                                System.out.println("Book "+book.getISBN()+" already borrowed");
                                                break;
                                            }
                                        }
                                    
                                    currentUser.borrowedbooks.add(book);
                                    currentUser.borrowingDates.add(LocalDateTime.now());
                                    System.out.println("Book borrowed: " + book+ "at "+ currentUser.borrowingDates);
                                    
                                    book.setNumberofBooks(book.Number_of_Books-1);
                                    
                                    try {
                                        
                                        Serialize.writeAllBooks(books);
                                        Serialize.updateUser(currentUser);
                                        showAlert("Book borrowed");
                                    } catch (ClassNotFoundException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                }
                                else {
                                    System.out.println("Unavailable books");
                                }
                                } else {
                                    System.out.println("Borrow limit Reached(2)");
                                    lendlimit.setFill(Color.FIREBRICK);
                                    lendlimit.setText("Borrow limit Reached (2)");
                                }
                                loadbookpage(b, bookpagegrid, primaryStage, currentUser, maingrid, loginscene, searchbar, searchbar_writer, searchbar_year, mainScene);
                                break;
                            }
                        }
        
                        if (!bookFound) {
                            System.out.println("Book with ISBN "+ searchbar.getText()+" not found.");
                        }
                        else{
                            if (currentUser.borrowedbooks != null) {
                        List<Book> borrowedBooks = currentUser.getBorrowedBooks();
                        System.out.println("Borrowed Books:");
                        for (Book borrowedBook : borrowedBooks) {
                            System.out.println(borrowedBook);
                        }
                        System.out.println("Number of borrowed books: "+currentUser.number_of_borrowed_books());
                        MainPage.updateMainGrid(maingrid, books, primaryStage, loginscene, currentUser, searchbar,searchbar_writer,searchbar_year, mainScene);
                        primaryStage.setScene(mainScene);
                        }
                    }
                        
                }
                else {
                    System.out.println("current user null");
                }
                        
            }
        });
        returnbookButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent a) {
                List<Book> books = Serialize.readAllBooks();
                        
                        boolean bookFound = false;
                        if(currentUser!=null) {
                        for (Book book : books) {
                            if(book.getISBN()==b.getISBN()) {
                                bookFound = true;
                                
                                if (currentUser.getBorrowedBooks().size() <= 2 && currentUser.number_of_borrowed_books() > 0 ) {
                                    if(currentUser.getBorrowedBooks()==null) {
                                        currentUser.borrowedbooks= new ArrayList<>();
                                        }
                                        if (currentUser.getBorrowedBooks() != null) {
                                            List<Book> borrowedBooks = currentUser.getBorrowedBooks();
                                            List<LocalDateTime> borrowingDates = currentUser.getBorrowingDates();
                                
                                            for (int i = 0; i < borrowedBooks.size(); i++) {
                                                Book borrowedBook = borrowedBooks.get(i);
                                                LocalDateTime borrowingDate = borrowingDates.get(i);
                                
                                                if (borrowedBook.getISBN() == b.getISBN()) {
                                                    // Remove the borrowed book and its borrowing date
                                                    borrowedBooks.remove(i);
                                                    borrowingDates.remove(i);
                                
                                                    System.out.println("Book Returned: " + borrowedBook);
                                
                                                    // Update book count, write to file, etc.
                                                    book.setNumberofBooks(book.getNumberofBooks() + 1);
                                
                                                    try {
                                                        Serialize.writeAllBooks(books);
                                                        Serialize.updateUser(currentUser);
                                                        showAlert("Book returned");
                                                    } catch (ClassNotFoundException | IOException e) {
                                                        e.printStackTrace();
                                                    }
                                                    loadbookpage(b, bookpagegrid, primaryStage, currentUser, maingrid, loginscene, searchbar, searchbar_writer, searchbar_year, mainScene);
                                                    break; // Exit the loop after finding and returning the book
                                                }
                                            }
                                        }
                                        MainPage.updateMainGrid(maingrid, books, primaryStage, loginscene, currentUser, searchbar,searchbar_writer,searchbar_year, mainScene);
                                        primaryStage.setScene(mainScene);
                                    }
                                }
                        
        
                        if (!bookFound) {
                            System.out.println("Book with ISBN "+ b.getISBN()+" not found.");
                        }
                        else{
                            if (currentUser.getBorrowedBooks() != null) {
                        List<Book> borrowedBooks = currentUser.getBorrowedBooks();
                        System.out.println("Borrowed Books:");
                        for (Book borrowedBook : borrowedBooks) {
                            System.out.println(borrowedBook);
                        }
                        System.out.println(currentUser.number_of_borrowed_books());
                        break;
                        }
                        else {
                            System.out.println("No borrowed books");
                        }
                    }
                }
                    
            }
            else {
                System.out.println("current user null");
            }
            }
        });
        
    }
    private static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
