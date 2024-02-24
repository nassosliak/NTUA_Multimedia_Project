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
import javafx.scene.control.DatePicker;
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
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Roboto", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        TextField userTextFieldlogin = new TextField();
        grid.add(userTextFieldlogin, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
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

        Scene loginscene = new Scene(grid, 1000, 500);
        loginscene.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());

        //register page
        GridPane registergrid = new GridPane();
        registergrid.setAlignment(Pos.CENTER);
        Text registertitle = new Text("Sign Up");
        registertitle.setFont(Font.font("Roboto", FontWeight.NORMAL, 20));
        registergrid.add(registertitle, 0, 0, 2, 1);
        registergrid.add(userTextField, 1, 1);
        registergrid.add(userName, 0, 1);
        registergrid.add(pw, 0, 2);
        registergrid.add(pwBox, 1, 2);
        TextField firstnamefield = new TextField("First name");
        registergrid.add(firstnamefield,1,3);
        TextField lastnamefield = new TextField("Last name");
        registergrid.add(lastnamefield,1,4);
        TextField emailfield = new TextField("Email");
        registergrid.add(emailfield,1,5);
        TextField IDfield = new TextField("ID");
        registergrid.add(IDfield,1,6);
        Button signupButton = new Button("Sign Up");
        registergrid.add(signupButton, 3, 4);
        DatePicker birthDatePicker = new DatePicker();
        registergrid.add(birthDatePicker, 1 ,7);
        Scene registerscene = new Scene(registergrid, 1000, 500);
        registerscene.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());
        

        //adminpage
        GridPane admingrid = new GridPane();
        
        Scene adminScene = new Scene(admingrid, 1000, 500);
        adminScene.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());
        AdminPage.loadadminPage(admingrid,primaryStage,adminScene);

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
        MainPage.updateMainGrid(maingrid, books, primaryStage, loginscene, currentUser, searchbar,searchbar_writer,searchbar_year, mainScene);


        //various functions
        
        signinButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            
            public void handle(ActionEvent e) {
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

                if (Serialize.isValidUser(username, password)) {
                    // Create a new User object and set its username
                    User newUser = new User();
                    newUser.setUsername(username);
        
                    // Set the current user in the SessionManager (if applicable)
                    SessionManager.getInstance().setStoredUsername(username);
        
                    // Find the user in the list based on the username
                    for (User existingUser : users) {
                        if (existingUser.getusername().equals(username)) {
                            currentUser = existingUser;
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
                    MainPage.updateMainGrid(maingrid,books,primaryStage,loginscene,currentUser,searchbar,searchbar_writer,searchbar_year,mainScene);
                    primaryStage.setScene(mainScene);
                    primaryStage.show();
                } else {
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("Invalid username or password");
                }
                System.out.println("Current username: "+ currentUser.getusername());
            }

        });
        


        registerbtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent ev) {
                
                Navigator(primaryStage,registerscene);
            }
        });

        
        
        signupButton.setOnAction(new EventHandler<ActionEvent>() {
            
            
            @Override
            public void handle(ActionEvent s) {
                List<User> users = Serialize.readAllUsers();
                String username;
                username=userTextFieldlogin.getText();
                System.out.println(username);
                String password;
                password=pwBoxlogin.getText();
                System.out.println(password);
                String firstname;
                firstname=firstnamefield.getText();
                System.out.println(firstname);
                String lastname;
                lastname=lastnamefield.getText();
                System.out.println(lastname);
                String email;
                email=emailfield.getText();
                System.out.println(email);
                String ID;
                ID=IDfield.getText();
                System.out.println(ID);
                boolean isvaliddata=true;
                for(User u:users) {
                    if(u.getusername().equals(username)) {
                        System.out.println("Username Exists");
                        isvaliddata=false;
                    }
                    if(u.getemail().equals(email)) {
                        System.out.println("Email Exists");
                        isvaliddata=false;
                    }
                    if(u.getemail().equals(ID)) {
                        System.out.println("ID Exists");
                        isvaliddata=false;
                    }
                }
                if(isvaliddata) {
                User user = new User(firstname, lastname, username, password, email, ID,null);
                
                System.out.println("Number of Users: "+user.getNumberofUsers());
                
                try {
                Serialize.saveUser(user);
                System.out.println("User added succesfully");
                }
                catch (IOException e) {
                    System.out.println("Error saving user: " + e.getMessage());
    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

        grid.getChildren().remove(actiontarget);
        Navigator(primaryStage, loginscene);
            }
            else {
                ;
            }
            }
        });
            //primary stage
        Navigator(primaryStage, loginscene);
        ReturnTime thread = new ReturnTime(users, books, maingrid, primaryStage, loginscene,mainScene, searchbar_year, searchbar_year, searchbar_year);
        thread.start();
        primaryStage.show();
        
    }

    //method to switch pages
    private void Navigator(Stage Stage,Scene scene) {
        
        Stage.setScene(scene);
    }
    



public static void main(String[] args) {
    launch(args);
}
}



