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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Register {
    public static void loadregisterpage(GridPane registergrid, Stage primaryStage, Scene loginscene) {
        //register page
        Label userName = new Label("User Name:");
        Label pw = new Label("Password:");
        Label firstname = new Label("First Name: ");
        Label lastname = new Label("Last Name: ");
        Label email = new Label("Email: ");
        Label id = new Label("ID: ");
        registergrid.setAlignment(Pos.CENTER);
        Text registertitle = new Text("Sign Up");
        registertitle.setFont(Font.font("Roboto", FontWeight.NORMAL, 30));
        registergrid.add(registertitle, 0, 1, 40, 1);
        GridPane.setMargin(registertitle, new Insets(0, 0, 10, 0));
        registergrid.add(userName, 0, 2);
        GridPane.setMargin(userName, new Insets(0, 0, 20, 0));
        registergrid.add(pw, 0, 3);
        GridPane.setMargin(pw, new Insets(0, 0, 20, 0));
        registergrid.add(firstname, 0, 4);
        GridPane.setMargin(firstname, new Insets(0, 0, 20, 0));
        registergrid.add(lastname, 0, 5);
        GridPane.setMargin(lastname, new Insets(0, 0, 20, 0));
        registergrid.add(email, 0,6);
        GridPane.setMargin(email, new Insets(0, 0, 20, 0));
        registergrid.add(id, 0, 7);
        GridPane.setMargin(id, new Insets(0, 0, 20, 0));
        Button loginnpagenavbutton = new Button();
        Image iconImage = new Image(MainPage.class.getResourceAsStream("resources/arrow_back_FILL0_wght400_GRAD0_opsz24.png"));
        loginnpagenavbutton.setGraphic(new ImageView(iconImage));
        loginnpagenavbutton.getStyleClass().add("loginnpagenavbutton");
        registergrid.add(loginnpagenavbutton,0,0);
        TextField usernamefield = new TextField();
        registergrid.add(usernamefield,1,2);
        GridPane.setMargin(usernamefield, new Insets(0, 0, 20, 0));
        PasswordField passworField = new PasswordField();
        registergrid.add(passworField,1,3);
        GridPane.setMargin(passworField, new Insets(0, 0, 20, 0));
        TextField firstnamefield = new TextField();
        registergrid.add(firstnamefield,1,4);
        GridPane.setMargin(firstnamefield, new Insets(0, 0, 20, 0));
        TextField lastnamefield = new TextField();
        registergrid.add(lastnamefield,1,5);
        GridPane.setMargin(lastnamefield, new Insets(0, 0, 20, 0));
        TextField emailfield = new TextField();
        registergrid.add(emailfield,1,6);
        GridPane.setMargin(emailfield, new Insets(0, 0, 20, 0));
        TextField IDfield = new TextField();
        registergrid.add(IDfield,1,7);
        GridPane.setMargin(IDfield, new Insets(0, 0, 20, 0));
        Button signupButton = new Button("Sign up");
        registergrid.add(signupButton, 3, 8);
        // DatePicker birthDatePicker = new DatePicker();
        //registergrid.add(birthDatePicker, 1 ,7);
        final Text usernameempty = new Text();
        registergrid.add(usernameempty, 4, 2);
        registergrid.setColumnSpan(usernameempty, 2);
        registergrid.setHalignment(usernameempty, RIGHT);
        usernameempty.setId("usernameempty");

        final Text usernameexists = new Text();
        registergrid.add(usernameexists, 4, 2);
        registergrid.setColumnSpan(usernameexists, 2);
        registergrid.setHalignment(usernameexists, RIGHT);
        usernameexists.setId("usernameexists");

        final Text emailexists = new Text();
        registergrid.add(emailexists, 4, 5);
        registergrid.setColumnSpan(emailexists, 2);
        registergrid.setHalignment(emailexists, RIGHT);
        emailexists.setId("emailexists");

        final Text invalidemail = new Text();
        registergrid.add(invalidemail, 4, 5);
        registergrid.setColumnSpan(invalidemail, 2);
        registergrid.setHalignment(invalidemail, RIGHT);
        invalidemail.setId("emailexists");

        
        final Text idexists = new Text();
        registergrid.add(idexists, 4, 6);
        registergrid.setColumnSpan(idexists, 2);
        registergrid.setHalignment(idexists, RIGHT);
        idexists.setId("idexists");



        signupButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent s) {
                usernameempty.setText("");
                invalidemail.setText("");
                emailexists.setText("");
                usernameexists.setText("");
                idexists.setText("");
                List<User> users = Serialize.readAllUsers();
                String username;
                username=usernamefield.getText();
                System.out.println(username);
                String password;
                password=passworField.getText();
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
                if(username.equals("")) {
                    isvaliddata=false;
                    usernameempty.setFill(Color.FIREBRICK);
                    usernameempty.setText("Please Input a Username");
                }
                if(!(email).contains("@")) {
                    System.out.println("invalid email");
                    invalidemail.setFill(Color.FIREBRICK);
                    invalidemail.setText("Invalid Email");
                    isvaliddata=false;
                }
                
                for(User u:users) {
                    if(u.getusername().equals(username)) {
                        System.out.println("Username Exists");
                        usernameexists.setFill(Color.FIREBRICK);
                        usernameexists.setText("Username Exists");
                        isvaliddata=false;
                        break;
                    }
                    else {
                        usernameexists.setText("");
                    }
                    if(u.getemail().equals(email)) {
                        System.out.println("Email Exists");
                        emailexists.setFill(Color.FIREBRICK);
                        emailexists.setText("Email Exists");
                        isvaliddata=false;
                        break;
                    }
                    else{
                        emailexists.setText("");
                    }
                    if(u.getID().equals(ID)) {
                        System.out.println("ID Exists");
                        idexists.setFill(Color.FIREBRICK);
                        idexists.setText("ID Exists");
                        isvaliddata=false;
                        break;
                    }
                }
                if(isvaliddata) {
                User user = new User(firstname, lastname, username, password, email, ID,null);
                System.out.println("Number of Users: "+user.getNumberofUsers());
                invalidemail.setText("");

                try {
                    Serialize.saveUser(user);
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println("User added succesfully");

        
        primaryStage.setScene(loginscene);
            }
            else {
                ;
            }
            }
        });
            //primary stage
        primaryStage.setScene(loginscene);
        ReturnTime thread = new ReturnTime(primaryStage, loginscene);
        thread.start();
        primaryStage.show();

        loginnpagenavbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                
                    primaryStage.setScene(loginscene);
              
            }
        });
        
    }
    

    //method to switch pages
    private void Navigator(Stage Stage,Scene scene) {
        
        Stage.setScene(scene);
    }
    }

