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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
        //bookpagegrid.getChildren().clear();
        bookpagegrid.setAlignment(Pos.CENTER);
        List<Book> books = Serialize.readAllBooks();
        Button mainpagenavButton = new Button();
        GridPane.setMargin(mainpagenavButton, new Insets(10,10,10,10));
        Image iconImage = new Image(MainPage.class.getResourceAsStream("resources/arrow_back_FILL0_wght400_GRAD0_opsz24.png"));
        mainpagenavButton.setGraphic(new ImageView(iconImage));
        mainpagenavButton.getStyleClass().add("mainpagenavButton");
        bookpagegrid.add(mainpagenavButton,0,0);
        Text booktexttitle= new Text("Book Title: " +b.getTitle());
        booktexttitle.setId("booktexttitle");
        bookpagegrid.add(booktexttitle,1,0);
        Text booktextwriter= new Text("Writer: " +b.getWriter());
        booktextwriter.setId("booktext");
        bookpagegrid.add(booktextwriter,1,1);
        Text booktextpublisher= new Text("Publisher: " + b.getPublisher());
        booktextpublisher.setId("booktext");
        bookpagegrid.add(booktextpublisher,1,2);
        Text booktextcategory= new Text("Category: " + b.getCategory());
        booktextcategory.setId("booktext");
        bookpagegrid.add(booktextcategory,1,3);
        Text booktextisbn= new Text("ISBN: "+(b.getISBN()));
        booktextisbn.setId("booktext");
        bookpagegrid.add(booktextisbn,1,4);
        Text booktextyear= new Text("Year of Publish: "+Integer.toString(b.getYear_of_Publish()));
        booktextyear.setId("booktext");
        bookpagegrid.add(booktextyear,1,5);
        String avgRatingText = String.format("%.2f", b.averagerating());

        Text bookavgrateText = new Text("Average User Rating: "+avgRatingText);
        bookavgrateText.setId("booktext");
        bookpagegrid.add(bookavgrateText,1,7);
        Text bookavText = new Text("Available books: "+Integer.toString(b.getNumberofBooks()));
        bookavText.setId("booktext");
        bookpagegrid.add(bookavText,1,6);
        Text bookrates = new Text("Total User Ratings: "+Integer.toString(b.gettotalratings()));
        bookrates.setId("booktext");
        bookpagegrid.add(bookrates,1,8);
        Ratingfx rating = new Ratingfx(5);
        rating.setRating((int)Math.round(b.averagerating()));
        bookpagegrid.add(rating, 1, 9);
        int rowindex=2;
       ScrollPane scrollPane = new ScrollPane();

scrollPane.setPrefWidth(400);
scrollPane.setPrefHeight(220);
if(!b.bookcomments.isEmpty()) {
VBox commentsBox = new VBox();
commentsBox.setSpacing(10);

for (int i = 0; i < b.bookcomments.size(); i++) {
    String byuser = b.bookcomments.get(i).username;
    String data = b.bookcomments.get(i).data;
    Text commentText = new Text("Comment by " + byuser + '\n' + data + '\n');
    commentsBox.getChildren().add(commentText);
}

scrollPane.setContent(commentsBox);

bookpagegrid.add(scrollPane, 1, 10);
    }
    else {
        Text nocomments = new Text("No comments have been added to this book yet");
        bookpagegrid.add(nocomments, 1, 10);
    }
        Button addcommentButton = new Button("Add comment");
        Button addratingButton = new Button("Add Rating");
        boolean bookcontained =false;
        if(currentUser.number_of_borrowed_books()!=0) {
        for(Book book: currentUser.getBorrowedBooks()) {
            if(book.getISBN().equals(b.getISBN())) {
bookcontained=true;
break;
            }
        }
    }
        if(currentUser!=null && currentUser.getBorrowedBooks() != null && bookcontained){
        bookpagegrid.add(addcommentButton,2,4);
        bookpagegrid.add(addratingButton,2,6);
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
            bookpagegrid.add(deleteButton,2, 4);
        bookpagegrid.add(editButton, 2, 6);
        }
        else {
        if (currentUser != null && currentUser.getBorrowedBooks() != null && bookcontained) {
            bookpagegrid.getChildren().remove(lendbookButton);
            bookpagegrid.add(returnbookButton, 2, 2);
        }
        
        if (currentUser != null && currentUser.getBorrowedBooks() != null && !bookcontained)  {
        bookpagegrid.getChildren().remove(returnbookButton);
        bookpagegrid.add(lendbookButton,2,2);
        }
    }
        //addcomment page
        GridPane commentgrid = new GridPane();
        commentgrid.setAlignment(Pos.CENTER);
        Button bookpagenavButton3 = new Button();
        Image iconImage4 = new Image(MainPage.class.getResourceAsStream("resources/arrow_back_FILL0_wght400_GRAD0_opsz24.png"));
        bookpagenavButton3.setGraphic(new ImageView(iconImage4));
        bookpagenavButton3.getStyleClass().add("bookpagenavButton");
        commentgrid.add(bookpagenavButton3,0,0);
        Text commentTitle = new Text("Add a Comment...");
        GridPane.setMargin(commentTitle, new Insets(10,10,10,10));
        commentTitle.setStyle("-fx-font-size:20px;");
        commentgrid.add(commentTitle,1,0);
        TextArea commentfield = new TextArea();
        commentfield.setPrefWidth(200);
        commentfield.setPrefHeight(400);
        commentgrid.add(commentfield,1,3);
        commentfield.setWrapText(true);
        Button publishcommentButton = new Button("Publish Comment");
        commentgrid.add(publishcommentButton,2,3);
        Scene addcommentScene = new Scene(commentgrid, 1400, 700);
        addcommentScene.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());

        //add rating page
        GridPane ratinggrid = new GridPane();
        ratinggrid.setAlignment(Pos.CENTER);
        Button bookpagenavButton = new Button();
        Image iconImage2 = new Image(MainPage.class.getResourceAsStream("resources/arrow_back_FILL0_wght400_GRAD0_opsz24.png"));
        bookpagenavButton.setGraphic(new ImageView(iconImage2));
        bookpagenavButton.getStyleClass().add("bookpagenavButton");
        ratinggrid.add(bookpagenavButton,0,0);
        Text ratingTitle = new Text("Add a Rating");
        ratingTitle.setStyle("-fx-font-size:20px;");
        GridPane.setMargin(ratingTitle, new Insets(10,10,10,10));
        ratinggrid.add(ratingTitle,1,0);
        Spinner<Integer> spinner = new Spinner<>(1, 5, 0);
        ratinggrid.add(spinner,1,2);
        GridPane.setMargin(spinner, new Insets(30,10,30,10));
        
        Button publishratingButton = new Button("Publish Rating");
        ratinggrid.add(publishratingButton,1,3);
        Scene addratingScene = new Scene(ratinggrid, 1400, 700);
        addratingScene.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());

        //editbook page
        GridPane editbookgrid =new GridPane();
        editbookgrid.setAlignment(Pos.CENTER);
        Button bookpagenavButton2 = new Button();
        Image iconImage3 = new Image(MainPage.class.getResourceAsStream("resources/arrow_back_FILL0_wght400_GRAD0_opsz24.png"));
        bookpagenavButton2.setGraphic(new ImageView(iconImage3));
        bookpagenavButton2.getStyleClass().add("bookpagenavButton");
        editbookgrid.add(bookpagenavButton2,0,0);
        Text editbookpagetitle=new Text("Edit Book");
        editbookgrid.add(editbookpagetitle,1,0);
        editbookpagetitle.setStyle("-fx-font-size: 20px;");
        Text a = new Text("Book Title: ");
        editbookgrid.add(a,1,1);
        TextField editbooktitle = new TextField(b.getTitle());
        editbookgrid.add(editbooktitle,2,1);
        Text c = new Text("Writer: ");
        editbookgrid.add(c,1,2);
        TextField editbookwriter = new TextField(b.getWriter());
        editbookgrid.add(editbookwriter,2,2);
        Text d = new Text("Publisher: ");
        editbookgrid.add(d,1,3);
        TextField editbookpublisher = new TextField(b.getPublisher());
        editbookgrid.add(editbookpublisher,2,3);
        Text editbookisbn = new Text("ISBN: "+(b.getISBN()));
        editbookgrid.add(editbookisbn,1,4);
        Text e= new Text("Year of Publish: ");
        editbookgrid.add(e,1,5);
        TextField editbook_year_of_publish = new TextField(Integer.toString(b.getYear_of_Publish()));
        editbookgrid.add(editbook_year_of_publish,2,5);
        Text f = new Text("Category: ");
        editbookgrid.add(f,1,6);
        ComboBox<String> comboBox = new ComboBox<>();
        List<Category> categories = Serialize.readAllCategories();
        ObservableList<String> items =FXCollections.observableArrayList();
        for(Category cat:categories) {
        items.add(cat.getTitle());
        }
        comboBox.setItems(items);

        comboBox.getSelectionModel().selectFirst();

        VBox category = new VBox(comboBox);
        editbookgrid.add(category,2,6);
        Text g = new Text("Number of Books: ");
        editbookgrid.add(g,1,7);
         TextField editnumberofbooksfield = new TextField(Integer.toString(b.getNumberofBooks()));
         editbookgrid.add(editnumberofbooksfield,2,7);
         Button editbookButton = new Button("Edit Book");
         editbookgrid.add(editbookButton,1,8);
         final Text nullcategory = new Text("");
         editbookgrid.add(nullcategory, 4, 6);
         editbookgrid.setColumnSpan(nullcategory, 2);
         editbookgrid.setHalignment(nullcategory, RIGHT);
        nullcategory.setId("lendlimit");
        
        final Text invalidyear = new Text("");
        editbookgrid.add(invalidyear, 4, 5);

        editbookgrid.setHalignment(invalidyear, RIGHT);
        invalidyear.setId("lendlimit");

        final Text invalidbooks = new Text("");
        editbookgrid.add(invalidbooks, 4, 7);

        editbookgrid.setHalignment(invalidbooks, RIGHT);
        invalidbooks.setId("lendlimit");

        final Text emptytitle = new Text();
        editbookgrid.add(emptytitle, 4, 1);
        editbookgrid.setHalignment(emptytitle, RIGHT);
        emptytitle.setId("lendlimit");

        final Text emptywriter = new Text();
        editbookgrid.add(emptywriter, 4, 2);
        editbookgrid.setHalignment(emptywriter, RIGHT);
        emptywriter.setId("lendlimit");

        final Text emptypublisher = new Text();
        editbookgrid.add(emptypublisher, 4, 3);
        editbookgrid.setHalignment(emptypublisher, RIGHT);
        emptypublisher.setId("lendlimit");
         Scene editbookScene = new Scene(editbookgrid, 1400, 700);
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
                    if(book.getISBN().equals(b.getISBN())) {
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
                        if (borrowedBook.getISBN().equals(b.getISBN())) {
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
                    
                
                System.out.println("Comment added from "+ currentUser.getusername()+' '+commentfield.getText());
                searchbar_writer.setText("");
            searchbar_year.setText("");
            searchbar.setText("");
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
                    if(book.getISBN().equals(b.getISBN())) {
                        book.rate(currentUser.getusername(),bookrating);
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
                        if (borrowedBook.getISBN().equals(b.getISBN())) {
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
                searchbar_writer.setText("");
            searchbar_year.setText("");
            searchbar.setText("");
                MainPage.updateMainGrid(maingrid,books,primaryStage,loginscene,currentUser,searchbar,searchbar_writer,searchbar_year,mainScene,adminScene);
                primaryStage.setScene(mainScene);

            }
        });
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
    @Override
    public void handle(ActionEvent s) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete Book");
        alert.setContentText("Are you sure you want to delete this book?");

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == buttonTypeYes) {
                // User clicked Yes, proceed with deletion
                String ISBN = b.getISBN();
                System.out.println(ISBN);
                try {
                    Serialize.deleteBook(ISBN);
                    System.out.println("Book deleted successfully");
                } catch (IOException e) {
                    System.out.println("Error deleting book: " + e.getMessage());
                    e.printStackTrace();
                }

                // Reload admin page after deletion
                List<Book> books = Serialize.readAllBooks();
                for (Book b : books) {
                    System.out.println(b);
                }
                AdminPage.loadadminPage(maingrid, primaryStage, adminScene, loginscene);
                primaryStage.setScene(adminScene);
            } else {
                // User clicked No, do nothing
                System.out.println("Delete operation canceled");
            }
        });
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
                    searchbar_writer.setText("");
            searchbar_year.setText("");
            searchbar.setText("");
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
                
                int year_of_publish=0;
                String bookCategory;
                bookCategory=comboBox.getValue();
                System.out.println(bookCategory);
                
                
                
                boolean valid=true;
                
                try {
                    year_of_publish = Integer.parseInt(editbook_year_of_publish.getText());
                    if (year_of_publish < 0 || year_of_publish > 2024) {
                        invalidyear.setFill(Color.FIREBRICK);
                        invalidyear.setText("Invalid Year of Publish");
                        valid = false;
                    }
                    else {
                        invalidyear.setText("");
                    }
                } catch (NumberFormatException e) {
                    invalidyear.setFill(Color.FIREBRICK);
                    invalidyear.setText("Invalid Year of Publish");
                    valid = false;
                }
                
                System.out.println(year_of_publish);
                if(editbooktitle.getText().equals("")) {
                    emptytitle.setFill(Color.FIREBRICK);
                    emptytitle.setText("Please Enter a Book Title");
                    valid=false;
                    
                }
                else {
                    emptytitle.setText("");
                }

                if(editbookwriter.getText().equals("")) {
                    emptywriter.setFill(Color.FIREBRICK);
                    emptywriter.setText("Please Enter a Writer");
                    valid=false;
                    
                }
                else {
                    emptywriter.setText("");
                }

                if(editbookpublisher.getText().equals("")) {
                    emptypublisher.setFill(Color.FIREBRICK);
                    emptypublisher.setText("Please Enter a Publisher");
                    valid=false;
                    
                }
                else {

                    emptypublisher.setText("");
                }

                bookCategory=comboBox.getValue();
                if(bookCategory==(null)) {
                    nullcategory.setFill(Color.FIREBRICK);
                    nullcategory.setText("Please Create a Category First");
                    valid=false;
                }
                else {
                    nullcategory.setText("");
                }
                System.out.println(bookCategory);
                int numberofbooks=0;
                try {
                numberofbooks=Integer.parseInt(editnumberofbooksfield.getText());
                if(numberofbooks<0) {
                    invalidbooks.setFill(Color.FIREBRICK);
                    invalidbooks.setText("Please Enter a Valid Number of Books");
                    valid=false;
                }
                else {
                    invalidbooks.setText("");
                }
                }
                catch (NumberFormatException e){
                    invalidbooks.setFill(Color.FIREBRICK);
                    invalidbooks.setText("Please Enter a Valid Number of Books");
                    valid=false;
                }
                System.out.println(numberofbooks);
                if(valid) {
                try {
                Serialize.editBook(b.getISBN(), editbooktitle.getText(), editbookwriter.getText(), editbookpublisher.getText(), year_of_publish, bookCategory,numberofbooks);
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
                            if (borrowedBook.getISBN().equals( b.getISBN())) {
                                System.out.println("removed "+u.borrowedbooks.get(i));
                                u.borrowedbooks.remove(i);
                                Book newbook = new Book(editbooktitle.getText(), editbookwriter.getText(), editbookpublisher.getText(),b.getISBN(), year_of_publish, bookCategory,numberofbooks);
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
            else {
                AdminPage.loadadminPage(maingrid, primaryStage, adminScene, loginscene);
            }
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
                            if(book.getISBN().equals(b.getISBN())) {
                                                        
                                bookFound = true;
                                
                                if (currentUser.number_of_borrowed_books() < 2) {
                                    if(currentUser.getBorrowedBooks()==null) {
                                        currentUser.borrowedbooks= new ArrayList();
                                      }
                                        if(book.getNumberofBooks()>0) {
                                            if(currentUser.getBorrowedBooks().size()==2 && (currentUser.borrowedbooks.get(0).getISBN().equals(book.getISBN()) ||
                                            currentUser.borrowedbooks.get(1).getISBN().equals(book.getISBN()))) {
                                                System.out.println("Book "+book.getISBN()+" already borrowed");
                                                break;
                                            
                                        }
                                        if(currentUser.getBorrowedBooks().size()==1) {
                                            if(currentUser.borrowedbooks.get(0).getISBN().equals(book.getISBN())) {
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
                                    return;
                                    
                                }
                                } else {
                                    System.out.println("Borrow limit Reached(2)");
                                    lendlimit.setFill(Color.FIREBRICK);
                                    lendlimit.setText("Borrow limit Reached (2)");
                                    limitreached=true;
                                    return;
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
                            searchbar_writer.setText("");
            searchbar_year.setText("");
            primaryStage.setScene(mainScene);
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
                            if(book.getISBN().equals(b.getISBN())) {
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
                                
                                                if (borrowedBook.getISBN() .equals(b.getISBN())) {
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
                                        searchbar_writer.setText("");
            searchbar_year.setText("");
            primaryStage.setScene(mainScene);
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
