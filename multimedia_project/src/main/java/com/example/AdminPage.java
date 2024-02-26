package com.example;
import static javafx.geometry.HPos.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AdminPage {
    public static void loadadminPage(GridPane admingrid, Stage primaryStage, Scene adminScene) {
        
        Text title_admin = new Text("Admin Page");
        admingrid.add(title_admin, 2, 1);
        Button addButton = new Button("Add Book");
        HBox haddButton = new HBox(10);
        haddButton.setAlignment(Pos.BOTTOM_RIGHT);
        haddButton.getChildren().add(addButton);
        admingrid.add(addButton, 3, 4);
        Button openborrowsButton = new Button("See open Borrows");
        admingrid.add(openborrowsButton, 3, 6);
        Button usersButton = new Button("See all users");
        admingrid.add(usersButton, 3, 7);
        Button addcatButton = new Button("Add Category");
        admingrid.add(addcatButton, 3, 5);
        TextField deletebookisbn = new TextField();
        admingrid.add(deletebookisbn, 3, 1);
        TextField deleteuserfield = new TextField("username to delete/edit");
        admingrid.add(deleteuserfield, 3, 2);
        
        Button deleteButton = new Button("Delete Book");
        Button editButton = new Button("Edit Book");
        Button editreturntimeButton = new Button("Edit Return Time");
        admingrid.add(editreturntimeButton, 1, 7);
        HBox hdeleteButton = new HBox(10);
        hdeleteButton.setAlignment(Pos.BOTTOM_RIGHT);
        hdeleteButton.getChildren().add(deleteButton);
        admingrid.add(deleteButton, 1, 4);
        admingrid.add(editButton, 2, 4);
        // Create a Map of books grouped by category (assuming you have this method)
        List<Book> books = Serialize.readAllBooks();
        Map<String, List<Book>> groupedBooks = Book.groupBooksByCategory(books);

        // Create a VBox to hold the content of books and categories
        VBox content = new VBox();

        // Iterate over each category
        for (String category : groupedBooks.keySet()) {
            // Add category label to the VBox
            Text categoryText = new Text("Category: " + category);
            content.getChildren().add(categoryText);

            // Iterate over books in the current category
            for (Book book : groupedBooks.get(category)) {
                // Add book title to the VBox
                Text bookText = new Text("  Book Title: " + book.getTitle() + " ISBN: " + book.getISBN());
                content.getChildren().add(bookText);
            }
        }

        // Create a ScrollPane and add the VBox to it
        ScrollPane scrollPane = new ScrollPane(content);

        // Set adjustable height and width for the ScrollPane
        scrollPane.setPrefHeight(200);
        scrollPane.setPrefWidth(300);

        // Add the ScrollPane to the GridPane
        admingrid.add(scrollPane, 0, 10, 10, 1);

       
        //addbook page
        GridPane addbookgrid =new GridPane();
        Text addbooktitle=new Text("Add book Page");
        addbookgrid.add(addbooktitle,2,1);
        
        
        
        TextField booktitle = new TextField();
        addbookgrid.add(booktitle,2,4);
        
        TextField bookwriter = new TextField("Writer");
        addbookgrid.add(bookwriter,2,6);

        TextField bookpublisher = new TextField("Publisher");
        addbookgrid.add(bookpublisher,2,8);

        TextField bookisbn = new TextField("ISBN");
        addbookgrid.add(bookisbn,2,10);

        TextField book_year_of_publish = new TextField("Year of Publish");
        addbookgrid.add(book_year_of_publish,2,12);
        
        ComboBox<String> comboBox = new ComboBox<>();

        // Create some items to add to the ComboBox
        List<Category> categories = Serialize.readAllCategories();
        ObservableList<String> items =FXCollections.observableArrayList();
        for(Category cat:categories) {
        items.add(cat.getTitle());
        }
        comboBox.setItems(items);

        comboBox.getSelectionModel().selectFirst();

        VBox category = new VBox(comboBox);
        addbookgrid.add(category,2,14);
        TextField numberofbooksfield = new TextField("# of books");
        addbookgrid.add(numberofbooksfield,2,16);
        
        Button savebookButton = new Button("Add Book");
        addbookgrid.add(savebookButton,3,4);
        final Text nullcategory = new Text("");
        addbookgrid.add(nullcategory, 4, 14);
        addbookgrid.setColumnSpan(nullcategory, 2);
        addbookgrid.setHalignment(nullcategory, RIGHT);
        nullcategory.setId("nullcategory");
        
        final Text invalidyear = new Text("");
        addbookgrid.add(invalidyear, 4, 12);
        addbookgrid.setColumnSpan(invalidyear, 2);
        addbookgrid.setHalignment(invalidyear, RIGHT);
        invalidyear.setId("invalidyear");

        final Text invalidisbn = new Text("");
        addbookgrid.add(invalidisbn, 4, 10);
        addbookgrid.setColumnSpan(invalidisbn, 2);
        addbookgrid.setHalignment(invalidisbn, RIGHT);
        invalidisbn.setId("invalidisbn");

        final Text invalidbooks = new Text("");
        addbookgrid.add(invalidbooks, 4, 16);
        addbookgrid.setColumnSpan(invalidbooks, 2);
        addbookgrid.setHalignment(invalidbooks, RIGHT);
        invalidbooks.setId("invalidbooks");

        Scene addbookScene = new Scene(addbookgrid, 1000, 500);
        addbookScene.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());


         //editbook page
        GridPane editbookgrid =new GridPane();
        Text editbookpagetitle=new Text("Edit book Page");
        editbookgrid.add(editbookpagetitle,2,1);
        
        
        TextField editbooktitle = new TextField();
        editbookgrid.add(editbooktitle,2,4);
        
        TextField editbookwriter = new TextField("Writer");
        editbookgrid.add(editbookwriter,2,6);
 
         TextField editbookpublisher = new TextField("Publisher");
         editbookgrid.add(editbookpublisher,2,8);
 
         TextField editbookisbn = new TextField("ISBN");
         editbookgrid.add(editbookisbn,2,10);
 
         TextField editbook_year_of_publish = new TextField("Year of Publish");
         editbookgrid.add(editbook_year_of_publish,2,12);
 
         TextField editcategory = new TextField("Category");
         editbookgrid.add(editcategory,2,14);
         TextField editnumberofbooksfield = new TextField("# of books");
         editbookgrid.add(editnumberofbooksfield,2,16);
         Button editbookButton = new Button("Edit Book");
         editbookgrid.add(editbookButton,3,4);
         
         Scene editbookScene = new Scene(editbookgrid, 1000, 500);
         editbookScene.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());

        //add category page
        GridPane addcategorygrid = new GridPane();
        TextField categorytitle = new TextField();
        addcategorygrid.add(categorytitle,2,4);
        TextField newcategorytitle = new TextField();
        addcategorygrid.add(newcategorytitle,4,4);
        Button savecategoryButton = new Button("Add Category");
        addcategorygrid.add(savecategoryButton,3,4);
        Button deletecategoryButton = new Button("Delete Category");
        addcategorygrid.add(deletecategoryButton,3,5);
        Button editcategoryButton = new Button("Edit Category Name");
        addcategorygrid.add(editcategoryButton,3,6);
        Scene addCategoryScene = new Scene(addcategorygrid, 1000, 500);
        addCategoryScene.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());

        
        //edit return time
        GridPane editreturnGridPane = new GridPane();

// TextFields for days, hours, and seconds
TextField daysTextField = new TextField("5");
TextField hoursTextField = new TextField("0");
TextField minutesTextField = new TextField("0");

// Labels for each field
Label daysLabel = new Label("Days:");
Label hoursLabel = new Label("Hours:");
Label minutesLabel = new Label("Minutes:");

// Add labels and text fields to the grid pane
editreturnGridPane.add(daysLabel, 0, 0);
editreturnGridPane.add(daysTextField, 1, 0);
editreturnGridPane.add(hoursLabel, 2, 0);
editreturnGridPane.add(hoursTextField, 3, 0);
editreturnGridPane.add(minutesLabel, 4, 0);
editreturnGridPane.add(minutesTextField, 5, 0);

Button savereturntimeButton = new Button("Save Return Time");
editreturnGridPane.add(savereturntimeButton, 6, 0);

Scene editreturntimeScene = new Scene(editreturnGridPane, 1000, 500);
editreturntimeScene.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());



        addButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent a) {
                
                primaryStage.setScene(addbookScene);
            }
        });

        
        editreturntimeButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent ev) {
                
                primaryStage.setScene(editreturntimeScene);
            }
        });

        openborrowsButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent e) {
                AdminBorrowPage.loadAdminBorrowPage(primaryStage);
            }
        });

        usersButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent e) {
                AdminUsersPage.loadadminuserspage(primaryStage,adminScene,admingrid);
            }
        });

        
        editButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent a) {
                
                primaryStage.setScene(editbookScene);
            }
        });

        

        addcatButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent a) {
                
                primaryStage.setScene(addCategoryScene);
            }
        });
        savereturntimeButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent a) {
                AdminReturnTime adminReturnTime= new AdminReturnTime(5*86400);
                if(!daysTextField.getText().isEmpty()&& !hoursTextField.getText().isEmpty() && !minutesTextField.getText().isEmpty()) {
                adminReturnTime.setdate(Integer.parseInt(daysTextField.getText())*86400+Integer.parseInt(hoursTextField.getText())*3600+Integer.parseInt(minutesTextField.getText())*60);

                System.out.println("Return time set to "+ daysTextField.getText()+ " days "+ hoursTextField.getText()+ " hours "+ minutesTextField.getText()+ " minutes ");
                }
                try {
                    Serialize.writereturntime(adminReturnTime);
                    System.out.println("Serialized return time to "+ adminReturnTime.getdate());
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            System.out.println(adminReturnTime.getdate());
            primaryStage.setScene(adminScene);}
        });
        savebookButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent s) {
                boolean valid=true;
                String bookTitle;
                bookTitle=booktitle.getText();
                System.out.println(bookTitle);
                String bookWriter;
                bookWriter=bookwriter.getText();
                System.out.println(bookWriter);
                String bookPublisher;
                bookPublisher=bookpublisher.getText();
                System.out.println(bookPublisher);
                int bookISBN;
                bookISBN=Integer.parseInt(bookisbn.getText());
                System.out.println(bookISBN);
                int year_of_publish=0;
                try {
                    year_of_publish = Integer.parseInt(book_year_of_publish.getText());
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
                String bookCategory;
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
                int numberofbooks;
                numberofbooks=Integer.parseInt(numberofbooksfield.getText());
                System.out.println(numberofbooks);
                if(valid) {
                Book b = new Book(bookTitle, bookWriter, bookPublisher, bookISBN, year_of_publish, bookCategory,numberofbooks);
                System.out.println(b.getNumberofBooks());
                try {
                Serialize.saveBook(b);
                System.out.println("Book added succesfully");
                }
                catch (IOException e) {
                    System.out.println("Error saving book: " + e.getMessage());
    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                loadadminPage(admingrid, primaryStage, adminScene);
                primaryStage.setScene(adminScene);
            }
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
                bookCategory=editcategory.getText();
                System.out.println(bookCategory);
                int numberofbooks;
                numberofbooks=Integer.parseInt(editnumberofbooksfield.getText());
                System.out.println(numberofbooks);
                
                try {
                Serialize.editBook(bookISBN, bookTitle, bookPublisher, bookWriter, year_of_publish, bookCategory,numberofbooks);
                System.out.println("Book editted succesfully");
                }
                catch (IOException e) {
                    System.out.println("Error saving book: " + e.getMessage());
    e.printStackTrace();
                }
                
        primaryStage.setScene(adminScene);

            }
        });

        

        savecategoryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent s) {
                
                String Category;
                Category=categorytitle.getText();
                System.out.println(Category);
                Category cat = new Category(Category);
                try {
                Serialize.saveCategory(cat);
                System.out.println("Category added succesfully");
                }
                catch (IOException e) {
                    System.out.println("Error saving category: " + e.getMessage());
    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
                List<Category> categories = Serialize.readAllCategories();
        
                for (Category cate : categories) {
                 System.out.println(cate);
                }
        loadadminPage(admingrid, primaryStage, adminScene);
        primaryStage.setScene(adminScene);

            }
        });

        deletecategoryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent s) {
                
                String Category;
                Category=categorytitle.getText();
                System.out.println(Category);
                try {
                Serialize.deleteCategory(Category);
                
                }
                catch (IOException e) {
                    System.out.println("Error saving category: " + e.getMessage());
    e.printStackTrace();
                }
                
                List<Category> categories = Serialize.readAllCategories();
                
                for (Category cate : categories) {
                 System.out.println(cate);
                }
               
        primaryStage.setScene(adminScene);

            }
        });

        

        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent s) {
                
                int ISBN;
                ISBN=Integer.parseInt(deletebookisbn.getText());
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
                
        primaryStage.setScene(adminScene);

            }
        });

        editcategoryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent s) {
                
                String Category;
                Category=categorytitle.getText();
                System.out.println(Category);
                try {
                Serialize.editCategory(Category, newcategorytitle.getText());
                
                }
                catch (IOException e) {
                    System.out.println("Error saving category: " + e.getMessage());
    e.printStackTrace();
                }
                
                List<Category> categories = Serialize.readAllCategories();
        
                for (Category cate : categories) {
                 System.out.println(cate);
                }
        primaryStage.setScene(adminScene);

            }
        });
    }
}
