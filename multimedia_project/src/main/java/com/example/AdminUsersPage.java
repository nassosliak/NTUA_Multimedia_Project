package com.example;
import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AdminUsersPage {
    public static void loadadminuserspage(Stage primaryStage) {
        GridPane adminusersgrid = new GridPane();
        List<User> users = Serialize.readAllUsers();
        int rowindex = 1;
        for (User u : users) {
                VBox userVBox = new VBox();
                Text userText = new Text("User: " + u.getusername() + "\n");
                adminusersgrid.add(userText, 1, rowindex++);
                Text userfirstnameText = new Text("First Name: " + u.getfirstname() + "\n");
                adminusersgrid.add(userfirstnameText, 1, 1+rowindex++);
                adminusersgrid.add(userVBox, 1, rowindex++);
                userVBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        // Handle the click event here
                        System.out.println("Clicked on user: " + u.getusername());
                    }
            });
        
        Scene adminusersScene =new Scene(adminusersgrid,1000,500);
        adminusersScene.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());
        primaryStage.setScene(adminusersScene);
    }
}
}
