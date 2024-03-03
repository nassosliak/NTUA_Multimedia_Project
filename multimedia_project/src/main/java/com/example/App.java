package com.example;
import static javafx.geometry.HPos.*;

import java.io.IOException;
import java.util.List;

import javafx.application.Application;
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


public class App extends Application {

    User currentUser;
    
    @Override
    public void start(Stage primaryStage) {
        
        primaryStage.setTitle("Library Management System");
        //deserialize
        List<Book> books = Serialize.readAllBooks();
        
        System.out.println(books);
        List<User> users = Serialize.readAllUsers();
        System.out.println(users);
        String storedUsername = SessionManager.getInstance().getStoredUsername();
        currentUser = Serialize.findUserByUsername(users, storedUsername);

        //login page
        GridPane grid = new GridPane();
        grid.setId("grid");
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
        Label register = new Label("Don't have an Account?");
        grid.add(register, 2, 4);
        GridPane.setMargin(register, new Insets(0, 0, 0, -50));
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

      
        Scene loginscene = new Scene(grid, 1000, 500);
        loginscene.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());

        //register page
        GridPane registergrid = new GridPane();
        Scene registerscene = new Scene(registergrid, 1000, 500);
        registerscene.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());
  
        //adminpage
        GridPane admingrid = new GridPane();
        Scene adminScene = new Scene(admingrid, 1000, 500);
        adminScene.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());
        AdminPage.loadadminPage(admingrid,primaryStage,adminScene,loginscene);

        //mainpage
        GridPane maingrid = new GridPane();
        TextField searchbar =new TextField();
        maingrid.add(searchbar,1,2);
        TextField searchbar_writer =new TextField();
        maingrid.add(searchbar_writer,1,3);
        TextField searchbar_year =new TextField();
        maingrid.add(searchbar_year,1,4);
        Scene mainScene = new Scene(maingrid, 1000, 500);
        mainScene.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());
        MainPage.updateMainGrid(maingrid, books, primaryStage, loginscene, currentUser, searchbar,searchbar_writer,searchbar_year, mainScene,adminScene);
        Register.loadregisterpage(registergrid, primaryStage, loginscene);

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
                boolean adminmode=false;
                if(username.equals("medialab") && password.equals("medialab_2024")) {
                    adminmode=true;
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
                            currentUser=u;
                            break;
                        }
                        
                    }
                    primaryStage.setScene(adminScene);
                    primaryStage.show();
                    
                }

                if (Serialize.isValidUser(username, password) && !adminmode) {
                    // Create a new User object and set its username
                    System.out.println("valid");
                    User newUser = new User();
                    newUser.setUsername(username);
        
                    // Set the current user in the SessionManager (if applicable)
                    SessionManager.getInstance().setStoredUsername(username);
        
                    // Find the user in the list based on the username
                    for (User existingUser : users) {
                        if (existingUser.getusername().equals(username)) {
                            currentUser=existingUser;
                            System.out.println("Current user set to "+ existingUser.getusername()+ currentUser);
                            break;
                        }
                    }
        
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
                    MainPage.updateMainGrid(maingrid,books,primaryStage,loginscene,currentUser,searchbar,searchbar_writer,searchbar_year,mainScene,adminScene);
                    primaryStage.setScene(mainScene);
                    primaryStage.show();
                } else {
                    if(!adminmode) {
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("Invalid username or password");
                    }
                }
                if(currentUser!=null) {
                System.out.println("Current username: "+ currentUser.getusername());
            }
        }
        
        });
    }
     
public static void main(String[] args) {
    launch(args);
}
}



