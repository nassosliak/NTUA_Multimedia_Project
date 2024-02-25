package com.example;

import static javafx.geometry.HPos.*;

import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Login {
    
    public static void loadloginpage(GridPane grid, Stage primaryStage, Scene registerscene, GridPane maingrid, Scene adminScene, Scene mainScene, User currentUser) {
        List<Book> books = Serialize.readAllBooks();
        
    //login page
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Roboto", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        TextField userTextFieldlogin = new TextField();
        grid.add(userTextFieldlogin, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwBoxlogin = new PasswordField();
        grid.add(pwBoxlogin, 1, 2);
        Label register = new Label("Dont have an Account?");
        grid.add(register, 2, 4);
        Button registerbtn = new Button("Register");
        grid.add(registerbtn, 3, 4);
        Button signinButton = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(signinButton);
        grid.add(signinButton, 1, 4);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 0, 6);
        grid.setColumnSpan(actiontarget, 2);
        grid.setHalignment(actiontarget, RIGHT);
        actiontarget.setId("actiontarget");

      
        registerbtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent ev) {
                actiontarget.setText("");
                primaryStage.setScene(registerscene);
            }
        });

        signinButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            
            public void handle(ActionEvent e) {
                actiontarget.setText("");
                List<User> users = Serialize.readAllUsers();
                String username = userTextFieldlogin.getText();
                String password = pwBoxlogin.getText();
                if(username.equals("medialab") && password.equals("medialab_2024")) {
                    
                    boolean foundadmin=false;
                    for(User u:users) {
                        if(u.getusername().equals("Admin")) {
                            foundadmin=true;
                            break;
                        }
                        
                    }
                    if(!foundadmin) {
                        User Admin = new User();
                        Admin.setUsername("Admin");
                        Admin.setEmail("0");
                        Admin.setId("0");
                       
                            try {
                                Serialize.saveUser(Admin);
                            } catch (ClassNotFoundException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            } catch (IOException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                       
                    }
                    SessionManager.getInstance().setStoredUsername("Admin");

                    for(User u:users) {
                        if(u.getusername().equals("Admin")) {
                            UserWrapper.setUser(u);
                            break;
                        }
                        
                    }
                    primaryStage.setScene(adminScene);
                    primaryStage.show();
                    
                }

                if (Serialize.isValidUser(username, password)) {
                    // Create a new User object and set its username
                    System.out.println("valid");
                    User newUser = new User();
                    newUser.setUsername(username);
        
                    // Set the current user in the SessionManager (if applicable)
                    SessionManager.getInstance().setStoredUsername(username);
        
                    // Find the user in the list based on the username
                    for (User existingUser : users) {
                        if (existingUser.getusername().equals(username)) {
                            UserWrapper.setUser(existingUser);
                            break;
                        }
                    }
        
                    // Set the username in the currentUser (if applicable)
                    if (currentUser != null) {
                        currentUser.setUsername(username);
                    }
                    if(currentUser!=null && currentUser.getBorrowedBooks()!=null){
                        int rowindex_Borrow=1;
                        for(int i=0; i<currentUser.getBorrowedBooks().size(); i++) {
                            Text bbtText= new Text(currentUser.getBorrowedBooks().get(i).getTitle()+", return time"+currentUser.getBorrowingDates().get(i).plusDays(5));
                            maingrid.add(bbtText, 12, rowindex_Borrow);
                            rowindex_Borrow++;
                        }
                    }
                    else {
                        System.out.println("No username");
                    }
                    //MainPage.updateMainGrid(maingrid,books,primaryStage,loginscene,currentUser,searchbar,searchbar_writer,searchbar_year,mainScene);
                    primaryStage.setScene(mainScene);
                    primaryStage.show();
                } else {
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("Invalid username or password");
                }
                if(currentUser!=null) {
                System.out.println("Current username: "+ currentUser.getusername());
            }
        }
        
        });
        
}
}
