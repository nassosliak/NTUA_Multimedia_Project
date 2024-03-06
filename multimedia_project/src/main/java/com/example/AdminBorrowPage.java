package com.example;
import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
//see open borrows
public class AdminBorrowPage {
    
    public static void loadAdminBorrowPage(Stage primaryStage, Scene adminScene) {
        GridPane adminBorrowGrid = new GridPane();
        Button mainpagenavButton = new Button();
        Image iconImage = new Image(MainPage.class.getResourceAsStream("resources/arrow_back_FILL0_wght400_GRAD0_opsz24.png"));
        mainpagenavButton.setGraphic(new ImageView(iconImage));
        mainpagenavButton.getStyleClass().add("mainpagenavButton");
        adminBorrowGrid.add(mainpagenavButton,0,0);
        
        List<User> users = Serialize.readAllUsers();
        int rowIndex = 2;
        VBox borrowContainer = new VBox();
        borrowContainer.setSpacing(10);
        Scene adminBorrowScene = new Scene(adminBorrowGrid, 1000, 500);
        adminBorrowScene.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());
        for (User user : users) {
            if (!user.getBorrowedBooks().isEmpty()) {
                Text userText = new Text("User " + user.getusername() + " has Borrowed:");
                //adminBorrowGrid.add(userText, 1, rowIndex++);
                VBox userVBox = new VBox(); // Create a VBox for each user
                for (int i = 0; i < user.getBorrowedBooks().size(); i++) {
                    Text bookText = new Text(
                            user.getBorrowedBooks().get(i).getISBN() +
                                    " and has to be returned at " +
                                    user.getBorrowingDates().get(i)
                    );
                    userVBox.getChildren().addAll(userText,bookText);
                }
                borrowContainer.getChildren().addAll(userVBox);
                
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
        for(int i=0; i<user.number_of_borrowed_books(); i++) {
            final int key = i;
            Text c = new Text((user.getBorrowedBooks().get(i).getISBN()) +
            " Return date: " +
            user.getBorrowingDates().get(i));
            Button adminpagenavButton = new Button();
            Image iconImage3 = new Image(MainPage.class.getResourceAsStream("resources/arrow_back_FILL0_wght400_GRAD0_opsz24.png"));
            adminpagenavButton.setGraphic(new ImageView(iconImage3));
            adminpagenavButton.getStyleClass().add("bookpagenavButton");
            handleUserGrid.add(adminpagenavButton,0,0);
        Button endBorrowButton = new Button("End borrow");
        handleUserGrid.add(endBorrowButton, 2, i+1);
        handleUserGrid.add(c, 1, i+1);
        endBorrowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                List<User> users = Serialize.readAllUsers();
                for(User u: users) {
                    if(u.getusername().equals(user.getusername())) {
                System.out.println("removed book "+user.getBorrowedBooks().get(key).getISBN());
                u.getBorrowedBooks().remove(key);
                u.getBorrowingDates().remove(key);
                try {
                    Serialize.writeAllUsers(users);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
                
            }
        }
        loadAdminBorrowPage(primaryStage, adminScene);
            }
        });
        adminpagenavButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                primaryStage.setScene(adminBorrowScene);
            }
        });
        }
        

        

        Scene handleUserScene = new Scene(handleUserGrid, 1000, 500);
        handleUserScene.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());
        primaryStage.setScene(handleUserScene);
    }
}
