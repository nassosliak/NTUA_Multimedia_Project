package com.example;
import static javafx.geometry.HPos.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BookPage {
    public static void loadbookpage(Book b,GridPane bookpagegrid, Stage primaryStage, User currentUser, GridPane maingrid, Scene loginscene, TextField searchbar, TextField searchbar_writer, TextField searchbar_year, Scene mainScene, Scene adminScene, Scene bookpageScene) {
        List<Book> books = Serialize.readAllBooks();
        Button mainpagenavButton = new Button();
        Image iconImage = new Image(MainPage.class.getResourceAsStream("resources/arrow_back_FILL0_wght400_GRAD0_opsz24.png"));
        mainpagenavButton.setGraphic(new ImageView(iconImage));
        mainpagenavButton.getStyleClass().add("mainpagenavButton");
        bookpagegrid.add(mainpagenavButton,2,0);
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
        Ratingfx rating = new Ratingfx(5);
        rating.setRating((int)Math.round(b.averagerating()));
        bookpagegrid.add(rating, 0, 9);
        int rowindex=2;
       ScrollPane scrollPane = new ScrollPane();
scrollPane.setFitToWidth(true); // Allow horizontal scrolling if necessary
scrollPane.setPrefViewportHeight(200); // Set preferred height for the scroll pane

// Create a VBox to hold the comments
VBox commentsBox = new VBox();
commentsBox.setSpacing(10); // Adjust spacing between comments if needed

// Add comments to the VBox
for (int i = 0; i < b.bookcomments.size(); i++) {
    String byuser = b.bookcomments.get(i).username;
    String data = b.bookcomments.get(i).data;
    Text commentText = new Text("Comment by " + byuser + '\n' + data + '\n');
    commentsBox.getChildren().add(commentText);
}

// Set the VBox as the content of the ScrollPane
scrollPane.setContent(commentsBox);

// Add the ScrollPane to the GridPane
bookpagegrid.add(scrollPane, 0, 10); // Adjust the row index as needed
        Button addcommentButton = new Button("Add comment");
        Button addratingButton = new Button("Add Rating");
        boolean bookcontained =false;
        if(currentUser.number_of_borrowed_books()!=0) {
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
        bookpagegrid.add(lendlimit, 7, 7);
        bookpagegrid.setColumnSpan(lendlimit, 2);
        bookpagegrid.setHalignment(lendlimit, RIGHT);
        lendlimit.setId("lendlimit");
        final Text unavbooks = new Text();
        bookpagegrid.add(unavbooks, 7, 7);
        bookpagegrid.setColumnSpan(unavbooks, 2);
        bookpagegrid.setHalignment(unavbooks, RIGHT);
        unavbooks.setId("avbooks");
        Button returnbookButton = new Button("Return");
        Button deleteButton = new Button("Delete Book");
        Button editButton = new Button("Edit Book");
    
        HBox hdeleteButton = new HBox(10);
        hdeleteButton.setAlignment(Pos.BOTTOM_RIGHT);
        hdeleteButton.getChildren().add(deleteButton);
        
        if(currentUser.getusername().equals("Admin")) {
            bookpagegrid.add(deleteButton, 7, 2);
        bookpagegrid.add(editButton, 7, 4);
        }
        else {
        if (currentUser != null && currentUser.getBorrowedBooks() != null && bookcontained) {
            bookpagegrid.getChildren().remove(lendbookButton);
            bookpagegrid.add(returnbookButton, 4, 7);
        }
        
        if (currentUser != null && currentUser.getBorrowedBooks() != null && !bookcontained)  {
        bookpagegrid.getChildren().remove(returnbookButton);
        bookpagegrid.add(lendbookButton,4,7);
        }
    }
        //addcomment page
        GridPane commentgrid = new GridPane();
        Button bookpagenavButton3 = new Button();
        Image iconImage4 = new Image(MainPage.class.getResourceAsStream("resources/arrow_back_FILL0_wght400_GRAD0_opsz24.png"));
        bookpagenavButton3.setGraphic(new ImageView(iconImage4));
        bookpagenavButton3.getStyleClass().add("bookpagenavButton");
        commentgrid.add(bookpagenavButton3,0,0);
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
        Button bookpagenavButton = new Button();
        Image iconImage2 = new Image(MainPage.class.getResourceAsStream("resources/arrow_back_FILL0_wght400_GRAD0_opsz24.png"));
        bookpagenavButton.setGraphic(new ImageView(iconImage2));
        bookpagenavButton.getStyleClass().add("bookpagenavButton");
        ratinggrid.add(bookpagenavButton,0,0);
        Text ratingTitle = new Text("Add a Rating");
        ratinggrid.add(ratingTitle,1,0);
        Spinner<Integer> spinner = new Spinner<>(1, 5, 0);
        ratinggrid.add(spinner,2,4);
        
        Button publishratingButton = new Button("Publish Rating");
        ratinggrid.add(publishratingButton,2,3);
        Scene addratingScene = new Scene(ratinggrid, 1000, 500);
        addratingScene.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());

        //editbook page
        GridPane editbookgrid =new GridPane();
        Button bookpagenavButton2 = new Button();
        Image iconImage3 = new Image(MainPage.class.getResourceAsStream("resources/arrow_back_FILL0_wght400_GRAD0_opsz24.png"));
        bookpagenavButton2.setGraphic(new ImageView(iconImage3));
        bookpagenavButton2.getStyleClass().add("bookpagenavButton");
        ratinggrid.add(bookpagenavButton2,0,0);
        Text editbookpagetitle=new Text("Edit book Page");
        editbookgrid.add(editbookpagetitle,2,1);
        
        
        TextField editbooktitle = new TextField(b.getTitle());
        editbookgrid.add(editbooktitle,2,4);
        
        TextField editbookwriter = new TextField(b.getWriter());
        editbookgrid.add(editbookwriter,2,6);
 
        TextField editbookpublisher = new TextField(b.getPublisher());
        editbookgrid.add(editbookpublisher,2,8);
        TextField editbookisbn = new TextField(Integer.toString(b.getISBN()));
        editbookgrid.add(editbookisbn,2,10);
 
        TextField editbook_year_of_publish = new TextField(b.getPublisher());
        editbookgrid.add(editbook_year_of_publish,2,11);
        ComboBox<String> comboBox = new ComboBox<>();
        List<Category> categories = Serialize.readAllCategories();
        ObservableList<String> items =FXCollections.observableArrayList();
        for(Category cat:categories) {
        items.add(cat.getTitle());
        }
        comboBox.setItems(items);

        comboBox.getSelectionModel().selectFirst();

        VBox category = new VBox(comboBox);
        editbookgrid.add(category,2,12);
         TextField editnumberofbooksfield = new TextField(Integer.toString(b.getNumberofBooks()));
         editbookgrid.add(editnumberofbooksfield,2,16);
         Button editbookButton = new Button("Edit Book");
         editbookgrid.add(editbookButton,3,4);
         
         Scene editbookScene = new Scene(editbookgrid, 1000, 500);
         editbookScene.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());
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
            Book newbook;
            @Override
            public void handle(ActionEvent a) {
                
                for(Book book:books) {
                    if(book.getISBN()==b.getISBN()) {
                        book.comment(currentUser.getusername(),commentfield.getText());
                        System.out.println("Book found");
                        newbook=book;
                        break;
                }
            }
            List<User> users = Serialize.readAllUsers();
            for(User u:users) {
                if(u.number_of_borrowed_books()!=0) {
                    for (int i = 0; i < u.getBorrowedBooks().size(); i++) {
                        Book borrowedBook = u.borrowedbooks.get(i);
                        if (borrowedBook.getISBN() == b.getISBN()) {
                            //System.out.println("removed "+u.borrowedbooks.get(i));
                            u.borrowedbooks.remove(i);
                            
                            u.borrowedbooks.add(newbook);
                            //System.out.println("added "+u.getBorrowedBooks());
                            break;
                }
            }}
        }
                        try {
                            Serialize.writeAllBooks(books);
                            Serialize.writeAllUsers(users);
                            System.out.println("Serialized comment");
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    
                
                System.out.println("Comment added from "+ currentUser.getusername()+' '+commentfield.getText());
                loadbookpage(b, bookpagegrid, primaryStage, currentUser, maingrid, loginscene, searchbar, searchbar_writer, searchbar_year, mainScene, adminScene, bookpageScene);
                MainPage.updateMainGrid(maingrid,books,primaryStage,loginscene,currentUser,searchbar,searchbar_writer,searchbar_year,mainScene,adminScene);
                primaryStage.setScene(mainScene);
                
            }
        });
        
        publishratingButton.setOnAction(new EventHandler<ActionEvent>() {
            Book newbook;
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
            List<User> users = Serialize.readAllUsers();
            for(User u:users) {
                if(u.number_of_borrowed_books()!=0) {
                    for (int i = 0; i < u.getBorrowedBooks().size(); i++) {
                        Book borrowedBook = u.borrowedbooks.get(i);
                        if (borrowedBook.getISBN() == b.getISBN()) {
                            System.out.println("removed "+u.borrowedbooks.get(i));
                            u.borrowedbooks.remove(i);
                            u.borrowedbooks.add(newbook);
                            System.out.println("added "+u.getBorrowedBooks());
                            break;
                }
            }}
        }
                        try {
                            Serialize.writeAllBooks(books);
                            Serialize.writeAllUsers(users);
                            System.out.println("Serialized comment");
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }                
                
                System.out.println("Rating added from "+ currentUser.getusername()+' '+bookrating);
                MainPage.updateMainGrid(maingrid,books,primaryStage,loginscene,currentUser,searchbar,searchbar_writer,searchbar_year,mainScene,adminScene);
                primaryStage.setScene(mainScene);

            }
        });
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent s) {
                
                int ISBN;
                ISBN=(b.getISBN());
                System.out.println(ISBN);
                try {
                Serialize.deleteBook(ISBN);
                System.out.println("Book deleted succesfully");
                }
                catch (IOException e) {
                    System.out.println("Error deleting book: " + e.getMessage());
    e.printStackTrace();
                }
                
                List<Book> books = Serialize.readAllBooks();
        
                for (Book b : books) {
                 System.out.println(b);
                }
        AdminPage.loadadminPage(maingrid, primaryStage, adminScene, loginscene);
        primaryStage.setScene(adminScene);

            }
        });
        editButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent a) {
                
         

                primaryStage.setScene(editbookScene);
            }
        });
        mainpagenavButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if(currentUser.getusername().equals("Admin")) {
                    primaryStage.setScene(adminScene);
                }
                else {
            primaryStage.setScene(mainScene);
                }
            }
        });
        bookpagenavButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                
                    primaryStage.setScene(bookpageScene);
              
            }
        });
        bookpagenavButton2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                
                    primaryStage.setScene(bookpageScene);
              
            }
        });
        bookpagenavButton3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                
                    primaryStage.setScene(bookpageScene);
              
            }
        });

        editbookButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent s) {

                String bookTitle;
                bookTitle=editbooktitle.getText();
                System.out.println(bookTitle);
                String bookWriter;
                bookWriter=editbookwriter.getText();
                System.out.println(bookWriter);
                String bookPublisher;
                bookPublisher=editbookpublisher.getText();
                System.out.println(bookPublisher);
                int bookISBN;
                bookISBN=Integer.parseInt(editbookisbn.getText());
                System.out.println(bookISBN);
                int year_of_publish;
                year_of_publish=Integer.parseInt(editbook_year_of_publish.getText());
                System.out.println(year_of_publish);
                String bookCategory;
                bookCategory=comboBox.getValue();
                System.out.println(bookCategory);
                int numberofbooks;
                numberofbooks=Integer.parseInt(editnumberofbooksfield.getText());
                System.out.println(numberofbooks);
                
                try {
                Serialize.editBook(b.getISBN(), bookTitle, bookPublisher, bookWriter, year_of_publish, bookCategory,numberofbooks);
                System.out.println("Book editted succesfully");
                }
                catch (IOException e) {
                    System.out.println("Error saving book: " + e.getMessage());
    e.printStackTrace();
                }
                List<User> users = Serialize.readAllUsers();
                for(User u:users) {
                    if(u.number_of_borrowed_books()!=0) {
                        for (int i = 0; i < u.getBorrowedBooks().size(); i++) {
                            Book borrowedBook = u.borrowedbooks.get(i);
                            if (borrowedBook.getISBN() == b.getISBN()) {
                                System.out.println("removed "+u.borrowedbooks.get(i));
                                u.borrowedbooks.remove(i);
                                Book newbook = new Book(bookTitle, bookWriter, bookPublisher,b.getISBN(), year_of_publish, bookCategory,numberofbooks);
                                u.borrowedbooks.add(newbook);
                                System.out.println("added "+u.getBorrowedBooks());
                                break;
                    }
                }}
            }
            try {
                Serialize.writeAllUsers(users);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        AdminPage.loadadminPage(maingrid, primaryStage, adminScene, loginscene);
        primaryStage.setScene(adminScene);
            }
        });
        lendbookButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent a) {
                List<Book> books = Serialize.readAllBooks();
                        unavbooks.setText("");
                        lendlimit.setText("");
                        boolean limitreached=false;
                        boolean bookFound = false;
                        boolean avbooks=true;
                        if(currentUser!=null) {
                        for (Book book : books) {
                            if(book.getISBN()==b.getISBN()) {
                                                        
                                bookFound = true;
                                
                                if (currentUser.number_of_borrowed_books() < 2) {
                                    if(currentUser.getBorrowedBooks()==null) {
                                        currentUser.borrowedbooks= new ArrayList();
                                      }
                                        if(book.getNumberofBooks()>0) {
                                            if(currentUser.getBorrowedBooks().size()==2 && (currentUser.borrowedbooks.get(0).getISBN()==book.getISBN() ||
                                            currentUser.borrowedbooks.get(1).getISBN()==book.getISBN())) {
                                                System.out.println("Book "+book.getISBN()+" already borrowed");
                                                break;
                                            
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
                                    unavbooks.setFill(Color.FIREBRICK);
                                    unavbooks.setText("Sorry this book is out of copies");
                                    avbooks=false;
                                }
                                } else {
                                    System.out.println("Borrow limit Reached(2)");
                                    lendlimit.setFill(Color.FIREBRICK);
                                    lendlimit.setText("Borrow limit Reached (2)");
                                    limitreached=true;
                                    break;
                                }
                                loadbookpage(b, bookpagegrid, primaryStage, currentUser, maingrid, loginscene, searchbar, searchbar_writer, searchbar_year, mainScene,adminScene,bookpageScene);
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
                        if(!limitreached && avbooks) {
                        MainPage.updateMainGrid(maingrid, books, primaryStage, loginscene, currentUser, searchbar,searchbar_writer,searchbar_year, mainScene,adminScene);
                        primaryStage.setScene(mainScene);
                        }
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
                                                    loadbookpage(b, bookpagegrid, primaryStage, currentUser, maingrid, loginscene, searchbar, searchbar_writer, searchbar_year, mainScene,adminScene,bookpageScene);
                                                    break; // Exit the loop after finding and returning the book
                                                }
                                            }
                                        }
                                        MainPage.updateMainGrid(maingrid, books, primaryStage, loginscene, currentUser, searchbar,searchbar_writer,searchbar_year, mainScene,adminScene);
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
