package com.example;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
//see open borrows
public class AdminBorrowPage {
    
    public static void loadAdminBorrowPage(Stage primaryStage, Scene adminScene) {
        GridPane adminBorrowGrid = new GridPane();
        adminBorrowGrid.setAlignment(Pos.CENTER);
        Button mainpagenavButton = new Button();
        Image iconImage = new Image(MainPage.class.getResourceAsStream("resources/arrow_back_FILL0_wght400_GRAD0_opsz24.png"));
        mainpagenavButton.setGraphic(new ImageView(iconImage));
        mainpagenavButton.getStyleClass().add("mainpagenavButton");
        adminBorrowGrid.add(mainpagenavButton,0,0);
        Text title = new Text("Open Book Borrows");
        title.setStyle("-fx-font-size:20px;");
        adminBorrowGrid.add(title,1,1);
        List<User> users = Serialize.readAllUsers();
        int rowIndex = 2;
        VBox borrowContainer = new VBox();
        borrowContainer.setSpacing(10);
        Scene adminBorrowScene = new Scene(adminBorrowGrid, 1400, 700);
        adminBorrowScene.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());
        for (User user : users) {
            if (!user.getBorrowedBooks().isEmpty()) {
                Text userText = new Text("User " + user.getusername() + " has Borrowed:");
                VBox userVBox = new VBox();
                 double x1 = 50;
        double y1 = 100;
        double x2 = 250;
        double y2 = 100;
        Line line = new Line(x1, y1, x2, y2);

        // Set the line color
        line.setStroke(Color.BLUE);
        userVBox.getChildren().add(line);
                userVBox.getChildren().add(userText);
                //adminBorrowGrid.add(userText, 1, rowIndex++);

                for (int i = 0; i < user.getBorrowedBooks().size(); i++) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    String returnTime = user.getBorrowingDates().get(i).plusDays(5).format(formatter);
                    Text bookText = new Text(
                        user.getBorrowedBooks().get(i).getTitle()+" (ISBN): "+
                            user.getBorrowedBooks().get(i).getISBN() +
                                    " and has to be returned at " +
                                    returnTime
                    );
                    userVBox.getChildren().add(bookText);
                }
                borrowContainer.getChildren().add(userVBox);
                
                userVBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        handleUserClick(user, primaryStage, adminScene, adminBorrowScene);
                    }
                });
                
            }

        }
        ScrollPane scrollpane = new ScrollPane(borrowContainer);
        scrollpane.setPrefHeight(300);
        scrollpane.setPrefWidth(400);
        adminBorrowGrid.add(scrollpane, 1, rowIndex++);
        primaryStage.setScene(adminBorrowScene);

        mainpagenavButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                primaryStage.setScene(adminScene);
            }
        });
    }
    
    private static void handleUserClick(User user, Stage primaryStage, Scene adminScene, Scene adminBorrowScene) {
        System.out.println("Clicked on user: " + user.getusername());
        
        GridPane handleUserGrid = new GridPane();
        Text usernameText= new Text("User "+user.getusername()+ " Open Borrows: ");
        handleUserGrid.add(usernameText,1,1);
        usernameText.setId("booktexttitle");
        handleUserGrid.setAlignment(Pos.CENTER);
        for(int i=0; i<user.number_of_borrowed_books(); i++) {
            final int key = i;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String returnTime = user.getBorrowingDates().get(i).plusDays(5).format(formatter);
            Text c = new Text(
                user.getBorrowedBooks().get(i).getTitle()+" (ISBN): "+
                user.getBorrowedBooks().get(i).getISBN() +
                        " and has to be returned at " +
                        returnTime
            );
            Button adminpagenavButton = new Button();
            Image iconImage3 = new Image(MainPage.class.getResourceAsStream("resources/arrow_back_FILL0_wght400_GRAD0_opsz24.png"));
            adminpagenavButton.setGraphic(new ImageView(iconImage3));
            adminpagenavButton.getStyleClass().add("bookpagenavButton");
            handleUserGrid.add(adminpagenavButton,0,0);
        Button endBorrowButton = new Button("End borrow");
        handleUserGrid.add(endBorrowButton, 2, i+2);
        handleUserGrid.add(c, 1, i+2);
        endBorrowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                List<User> users = Serialize.readAllUsers();
                List<Book> books = Serialize.readAllBooks();
                Book b = user.getBorrowedBooks().get(key);
                for(User u: users) {
                    if(u.getusername().equals(user.getusername())) {
    
                        
                        for(Book book:books) {
                            if(book.getISBN().equals(b.getISBN())) {
                                book.setNumberofBooks(b.getNumberofBooks()+1);
                                b=book;
                            }
                        }
                        
                System.out.println("removed book "+user.getBorrowedBooks().get(key).getISBN());
                u.getBorrowedBooks().remove(key);
                u.getBorrowingDates().remove(key);
                try {
                    Serialize.writeAllUsers(users);
                    Serialize.writeAllBooks(books);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Returned Book "+ b.getTitle()+" (ISBN: )"+b.getISBN()+" from user: "+ u.getusername());
        alert.showAndWait();
        GridPane admingrid = new GridPane();
        AdminPage.loadadminPage(admingrid, primaryStage, adminScene, adminScene);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
                
            }
        }
        for(User us:users) {
            if(us.getBorrowedBooks()!=null&&us.getBorrowedBooks().size()==1 && (us.borrowedbooks.get(0).getISBN().equals(b.getISBN()))) {
                us.borrowedbooks.get(0).setNumberofBooks(us.borrowedbooks.get(0).getNumberofBooks()+1);
        }
        if(us.getBorrowedBooks()!=null&&us.getBorrowedBooks().size()==2 && (us.borrowedbooks.get(0).getISBN().equals(b.getISBN()))) {
            us.borrowedbooks.get(0).setNumberofBooks(us.borrowedbooks.get(0).getNumberofBooks()+1);
    }
    if(us.getBorrowedBooks()!=null&&us.getBorrowedBooks().size()==2 && (us.borrowedbooks.get(1).getISBN().equals(b.getISBN()))) {
        us.borrowedbooks.get(1).setNumberofBooks(us.borrowedbooks.get(1).getNumberofBooks()+1);
}
    }
    try {
        Serialize.writeAllUsers(users);
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
        GridPane admingrid = new GridPane();
        AdminPage.loadadminPage(admingrid, primaryStage, adminScene, adminScene);
        loadAdminBorrowPage(primaryStage, adminScene);
            }
        });
        adminpagenavButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                loadAdminBorrowPage(primaryStage, adminScene);
                primaryStage.setScene(adminBorrowScene);
            }
        });
        }
        

        

        Scene handleUserScene = new Scene(handleUserGrid, 1400, 700);
        handleUserScene.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());
        primaryStage.setScene(handleUserScene);
    }
}
