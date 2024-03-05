package com.example;
import static javafx.geometry.HPos.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AdminPage {
    public static void loadadminPage(GridPane admingrid, Stage primaryStage, Scene adminScene, Scene loginScene) {
        admingrid.getChildren().clear();
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
        Button addcatButton = new Button("Category Management");
        admingrid.add(addcatButton, 3, 5);
        TextField searchField = new TextField();
        searchField.setPromptText("Search Books by ISBN...");
        admingrid.add(searchField, 3, 1);
        Button searchbyisbn = new Button("Search");
        admingrid.add(searchbyisbn,4,1);
        Button editreturntimeButton = new Button("Edit Return Time");
        admingrid.add(editreturntimeButton, 1, 7);
       
        Button signoutButton = new Button("Sign out");
        admingrid.add(signoutButton,10,2);
        // Create a Map of books grouped by category (assuming you have this method)
        List<Book> books = Serialize.readAllBooks();
        Map<String, List<Book>> groupedBooks = Book.groupBooksByCategory(books);

        // Create a VBox to hold the content of books and categories
        VBox content = new VBox();
        VBox categorycontent = new VBox();
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
        List<Category> categories = Serialize.readAllCategories();
        for(Category cat:categories) {
            Text categoryTextForCategoryContent = new Text("Category: " + cat.getTitle());
            categorycontent.getChildren().add(categoryTextForCategoryContent);
        }
        // Create a ScrollPane and add the VBox to it
        ScrollPane scrollPane = new ScrollPane(content);
        ScrollPane categoryScrollPane = new ScrollPane(categorycontent);
        // Set adjustable height and width for the ScrollPane
        scrollPane.setPrefHeight(200);
        scrollPane.setPrefWidth(300);

        // Add the ScrollPane to the GridPane
        admingrid.add(categoryScrollPane, 0, 10, 10, 1);

        categoryScrollPane.setPrefHeight(200);
        categoryScrollPane.setPrefWidth(300);

        // Add the ScrollPane to the GridPane
        admingrid.add(scrollPane, 10, 10, 10, 1);
        //addbook page
        GridPane addbookgrid =new GridPane();
        Button adminpagenavButton = new Button();
        Image iconImage3 = new Image(MainPage.class.getResourceAsStream("resources/arrow_back_FILL0_wght400_GRAD0_opsz24.png"));
        adminpagenavButton.setGraphic(new ImageView(iconImage3));
        adminpagenavButton.getStyleClass().add("bookpagenavButton");
        addbookgrid.add(adminpagenavButton,0,0);
        Text addbooktitle=new Text("Add book Page");
        addbookgrid.add(addbooktitle,2,1);
        
        TextField booktitle = new TextField();
        booktitle.setPromptText("Title");
        addbookgrid.add(booktitle,2,4);
        
        TextField bookwriter = new TextField();
        bookwriter.setPromptText("Writer");
        addbookgrid.add(bookwriter,2,6);

        TextField bookpublisher = new TextField();
        bookpublisher.setPromptText("Publisher");
        addbookgrid.add(bookpublisher,2,8);

        TextField bookisbn = new TextField();
        bookisbn.setPromptText("ISBN");
        addbookgrid.add(bookisbn,2,10);

        TextField book_year_of_publish = new TextField();
        book_year_of_publish.setPromptText("Year of Publish");
        addbookgrid.add(book_year_of_publish,2,12);
        
        ComboBox<String> comboBox = new ComboBox<>();

        // Create some items to add to the ComboBox
        
        ObservableList<String> items =FXCollections.observableArrayList();
        for(Category cat:categories) {
        items.add(cat.getTitle());
        }
        comboBox.setItems(items);

        comboBox.getSelectionModel().selectFirst();

        VBox category = new VBox(comboBox);
        addbookgrid.add(category,2,14);
        TextField numberofbooksfield = new TextField();
        numberofbooksfield.setPromptText("Number of books");
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


        //edit category
        GridPane editcategoryGridPane = new GridPane();
        TextField newcategory = new TextField();
        editcategoryGridPane.add(newcategory,2,1);
        Button addcategorypagenavButton = new Button();
        Image iconImage1 = new Image(MainPage.class.getResourceAsStream("resources/arrow_back_FILL0_wght400_GRAD0_opsz24.png"));
        addcategorypagenavButton.setGraphic(new ImageView(iconImage1));
        addcategorypagenavButton.getStyleClass().add("mainpagenavButton");
        editcategoryGridPane.add(addcategorypagenavButton,0,0);
        Button editcategoryButton = new Button("Edit Category");
        editcategoryGridPane.add(editcategoryButton,4,1);
        ComboBox<String> category_combobox = new ComboBox<>();
        ObservableList<String> catgory_items =FXCollections.observableArrayList();
        for(Category cat:categories) {
            catgory_items.add(cat.getTitle());
        }
        category_combobox.setItems(catgory_items);

        category_combobox.getSelectionModel().selectFirst();

        VBox category_Box = new VBox(category_combobox);
        editcategoryGridPane.add(category_Box,3,1);
        Scene editcategoryScene = new Scene(editcategoryGridPane, 1000, 500);
        editcategoryScene.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());

        //delete category
        GridPane deletecategoryGridPane = new GridPane();
        Button addcategorypagenavButton1 = new Button();
        Image iconImage2 = new Image(MainPage.class.getResourceAsStream("resources/arrow_back_FILL0_wght400_GRAD0_opsz24.png"));
        addcategorypagenavButton1.setGraphic(new ImageView(iconImage2));
        addcategorypagenavButton1.getStyleClass().add("mainpagenavButton");
        deletecategoryGridPane.add(addcategorypagenavButton1,0,0);
        Button deletecategoryButton = new Button("Delete Category");
        deletecategoryGridPane.add(deletecategoryButton,4,1);
        ComboBox<String> category_combobox1 = new ComboBox<>();
        ObservableList<String> catgory_items1 =FXCollections.observableArrayList();
        for(Category cat:categories) {
            catgory_items1.add(cat.getTitle());
        }
        category_combobox1.setItems(catgory_items1);

        category_combobox1.getSelectionModel().selectFirst();

        VBox category_Box1 = new VBox(category_combobox1);
        deletecategoryGridPane.add(category_Box1,3,1);
        Scene deletecategoryScene = new Scene(deletecategoryGridPane, 1000, 500);
        deletecategoryScene.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());

        //Category management
        GridPane addcategorygrid = new GridPane();
        final Text actiontarget = new Text();
        addcategorygrid.add(actiontarget, 0, 6);
        addcategorygrid.setColumnSpan(actiontarget, 2);
        addcategorygrid.setHalignment(actiontarget, RIGHT);
        actiontarget.setId("actiontarget");
        Text addnewcat = new Text("Add new Category");
        addcategorygrid.add(addnewcat,1,0);
        Button mainpagenavButton = new Button();
        Image iconImage = new Image(MainPage.class.getResourceAsStream("resources/arrow_back_FILL0_wght400_GRAD0_opsz24.png"));
        mainpagenavButton.setGraphic(new ImageView(iconImage));
        mainpagenavButton.getStyleClass().add("mainpagenavButton");
        addcategorygrid.add(mainpagenavButton,2,0);
        TextField categorytitle = new TextField();
        addcategorygrid.add(categorytitle,2,4);
        Button savecategoryButton = new Button("Add Category");
        addcategorygrid.add(savecategoryButton,3,4);
        Button deletecategorynavButton = new Button("Delete Category");
        addcategorygrid.add(deletecategorynavButton,3,5);
        Button editcategorynavButton = new Button("Edit Category Name");
        addcategorygrid.add(editcategorynavButton,3,6);
        Map<String, Integer> categoryCounts = new HashMap<>();

        for (String cat : groupedBooks.keySet()) {
            int counter=0;
            for (Book book : groupedBooks.get(cat)) {
                ++counter;
            }
            categoryCounts.put(cat, counter);
        }
        

        PieChart pieChart = new PieChart();

        for (String category_1 : categoryCounts.keySet()) {
            int count = categoryCounts.get(category_1);
            pieChart.getData().add(new PieChart.Data(category_1, count));
        }

        // Customize chart appearance (optional)
        pieChart.setTitle("Book Categories");
        addcategorygrid.add(pieChart, 3,7);
        Scene addCategoryScene = new Scene(addcategorygrid, 1000, 500);
        addCategoryScene.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());
        
        //edit return time
        GridPane editreturnGridPane = new GridPane();
        Button mainpagenavButton2 = new Button();
        Image iconImage4= new Image(MainPage.class.getResourceAsStream("resources/arrow_back_FILL0_wght400_GRAD0_opsz24.png"));
        mainpagenavButton2.setGraphic(new ImageView(iconImage4));
        mainpagenavButton2.getStyleClass().add("mainpagenavButton2");
        editreturnGridPane.add(mainpagenavButton2,0,0);
// TextFields for days, hours, and seconds
TextField daysTextField = new TextField("5");
TextField hoursTextField = new TextField("0");
TextField minutesTextField = new TextField("0");

// Labels for each field
Label daysLabel = new Label("Days:");
Label hoursLabel = new Label("Hours:");
Label minutesLabel = new Label("Minutes:");

// Add labels and text fields to the grid pane
editreturnGridPane.add(daysLabel, 1, 0);
editreturnGridPane.add(daysTextField, 2, 0);
editreturnGridPane.add(hoursLabel, 3, 0);
editreturnGridPane.add(hoursTextField, 4, 0);
editreturnGridPane.add(minutesLabel, 5, 0);
editreturnGridPane.add(minutesTextField,6, 0);

Button savereturntimeButton = new Button("Save Return Time");
editreturnGridPane.add(savereturntimeButton, 7, 0);

Scene editreturntimeScene = new Scene(editreturnGridPane, 1000, 500);
editreturntimeScene.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());
//searchpage admin
VBox root = new VBox();
      Scene searchScene = new Scene(root, 1000, 500);
      searchScene.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());

        addButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent a) {
                
                primaryStage.setScene(addbookScene);
            }
        });
        searchbyisbn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent a) {
                List<User> users = Serialize.readAllUsers();
                User currentUser=Serialize.findUserByUsername(users, "Admin");
                SearchPage.loadsearchPage(null,null,null,primaryStage,root,currentUser,admingrid,loginScene,null,searchField,adminScene);
            
                primaryStage.setScene(searchScene);
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
                AdminBorrowPage.loadAdminBorrowPage(primaryStage, adminScene);
            }
        });

        usersButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent e) {
                AdminUsersPage.loadadminuserspage(primaryStage,adminScene,admingrid,loginScene);
            }
        });

        mainpagenavButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                loadadminPage(admingrid, primaryStage, adminScene, loginScene);
                primaryStage.setScene(adminScene);
            }
        });
        mainpagenavButton2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                loadadminPage(admingrid, primaryStage, adminScene, loginScene);
                primaryStage.setScene(adminScene);
            }
        });

        adminpagenavButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                loadadminPage(admingrid, primaryStage, adminScene, loginScene);
                primaryStage.setScene(adminScene);
            }
        });
        addcategorypagenavButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent a) {
                loadadminPage(admingrid, primaryStage, adminScene, loginScene);
                primaryStage.setScene(addCategoryScene);
            }
        });
        addcategorypagenavButton1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent a) {
                loadadminPage(admingrid, primaryStage, adminScene, loginScene);
                primaryStage.setScene(addCategoryScene);
            }
        });
        deletecategorynavButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent a) {
                loadadminPage(admingrid, primaryStage, adminScene, loginScene);
                primaryStage.setScene(deletecategoryScene);
            }
        });
        addcatButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent a) {
                loadadminPage(admingrid, primaryStage, adminScene, loginScene);
                primaryStage.setScene(addCategoryScene);
            }
        });
        editcategorynavButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent a) {
                loadadminPage(admingrid, primaryStage, adminScene, loginScene);
                primaryStage.setScene(editcategoryScene);
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
                loadadminPage(admingrid, primaryStage, adminScene,loginScene);
                primaryStage.setScene(adminScene);
            }
            }
        });

        

        

        savecategoryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent s) {
                
                String Category;
                Category=categorytitle.getText();
                System.out.println(Category);
                Category cat = new Category(Category);
                boolean valid=true;
                for(Category c: categories) {
                    if(c.getTitle().equals(Category)) {
                        valid=false;
                        break;
                    }
                }
                if(!Category.equals("") && valid) {
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
        loadadminPage(admingrid, primaryStage, adminScene,loginScene);
        primaryStage.setScene(adminScene);
            }
            if(!valid) {
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("Category Already Exists");
            }
            else {
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("Please Enter a Valid Category Name");
            }
            }
        });

        deletecategoryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent s) {
                
                String Category;
                Category=category_combobox1.getValue();
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
        loadadminPage(admingrid, primaryStage, adminScene, loginScene);
        primaryStage.setScene(adminScene);

            }
        });

        

        

        editcategoryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent s) {
                
                String Category;
                Category=category_combobox.getValue();
                System.out.println(Category);
                try {
                Serialize.editCategory(Category, newcategory.getText());
                
                }
                catch (IOException e) {
                    System.out.println("Error saving category: " + e.getMessage());
    e.printStackTrace();
                }
                for(Book b: books) {
                    if(b.getCategory().equals(Category)) {
                    b.setCategory(newcategory.getText());
                    }
                }
                try {
                    Serialize.writeAllBooks(books);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                List<User> users = Serialize.readAllUsers();
                for(User u:users) {
                    if(u.number_of_borrowed_books()!=0) {
                        for (int i = 0; i < u.getBorrowedBooks().size(); i++) {
                            Book borrowedBook = u.borrowedbooks.get(i);
                            if (borrowedBook.getCategory().equals(Category)) {
                                System.out.println("removed "+u.borrowedbooks.get(i));
                                u.borrowedbooks.remove(i);
                                Book newbook = new Book(borrowedBook.getTitle(), borrowedBook.getTitle(), borrowedBook.getPublisher(),borrowedBook.getISBN(), borrowedBook.getYear_of_Publish(), newcategory.getText(),borrowedBook.getNumberofBooks());
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
                List<Category> categories = Serialize.readAllCategories();
        
                for (Category cate : categories) {
                 System.out.println(cate);
                }
        loadadminPage(admingrid, primaryStage, adminScene, loginScene);
        primaryStage.setScene(adminScene);

            }
        });
        signoutButton.setOnAction(new EventHandler<ActionEvent>() {


            @Override
            public void handle(ActionEvent ev) {
                
                primaryStage.setScene(loginScene);
            }
        });
    }
}
