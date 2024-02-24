package com.example;
import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AdminBorrowPage {
    
    public static void loadAdminBorrowPage(Stage primaryStage) {
        GridPane adminBorrowGrid = new GridPane();
        adminBorrowGrid.getChildren().clear();
        List<User> users = Serialize.readAllUsers();
        int rowIndex = 2;
        
        for (User user : users) {
            if (!user.getBorrowedBooks().isEmpty()) {
                Text userText = new Text("User " + user.getusername() + " has Borrowed:");
                adminBorrowGrid.add(userText, 1, rowIndex++);
                VBox userVBox = new VBox(); // Create a VBox for each user
                for (int i = 0; i < user.getBorrowedBooks().size(); i++) {
                    Text bookText = new Text(
                            Integer.toString(user.getBorrowedBooks().get(i).getISBN()) +
                                    " and has to be returned at " +
                                    user.getBorrowingDates().get(i)
                    );
                    userVBox.getChildren().add(bookText);
                }
                adminBorrowGrid.add(userVBox, 1, rowIndex++);
                
                userVBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        handleUserClick(user, primaryStage);
                    }
                });
            }
        }
        
        Scene adminBorrowScene = new Scene(adminBorrowGrid, 1000, 500);
        adminBorrowScene.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());
        primaryStage.setScene(adminBorrowScene);
    }
    
    private static void handleUserClick(User user, Stage primaryStage) {
        System.out.println("Clicked on user: " + user.getusername());
        
        GridPane handleUserGrid = new GridPane();
        Button endBorrowButton = new Button("End borrow");
        handleUserGrid.add(endBorrowButton, 0, 3);

        endBorrowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                List<User> users = Serialize.readAllUsers();
                for(User u: users) {
                    if(u.getusername().equals(user.getusername())) {
                System.out.println("removed book "+user.getBorrowedBooks().get(0).getISBN());
                u.getBorrowedBooks().remove(0);
                u.getBorrowingDates().remove(0);
                try {
                    Serialize.writeAllUsers(users);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
                
            }
        }
        loadAdminBorrowPage(primaryStage);
            }
        });

        Scene handleUserScene = new Scene(handleUserGrid, 1000, 500);
        handleUserScene.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());
        primaryStage.setScene(handleUserScene);
    }
}
