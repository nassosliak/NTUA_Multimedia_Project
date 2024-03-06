package com.example;
import static javafx.geometry.HPos.*;

import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AdminUsersPage {
    public static void loadadminuserspage(Stage primaryStage, Scene adminScene, GridPane admingrid, Scene loginScene) {
        GridPane adminusersgrid = new GridPane();
        Button mainpagenavButton = new Button();
        Image iconImage = new Image(MainPage.class.getResourceAsStream("resources/arrow_back_FILL0_wght400_GRAD0_opsz24.png"));
        mainpagenavButton.setGraphic(new ImageView(iconImage));
        mainpagenavButton.getStyleClass().add("mainpagenavButton");
        adminusersgrid.add(mainpagenavButton,0,0);
        Scene adminusersScene = new Scene(adminusersgrid, 1000, 500);
            adminusersScene.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());
        List<User> users = Serialize.readAllUsers();
        VBox usersContainer = new VBox();
        usersContainer.setSpacing(10);

        
        int rowindex = 1;
        for (User u : users) {
            if (u.getusername().equals("Admin")) {
                continue;
            }
            VBox userVBox = new VBox();
            Text userText = new Text("Username: " + u.getusername() + "\n"+
            "First Name: "+ u.getfirstname()+ "\n"+ "Last Name: "+u.getlastname()+"\n"+ "Email: "+ u.getemail()+ "\n"+"ID: " + u.getID());
            //adminusersgrid.add(userText, 1, rowindex);
            //Text userfirstnameText = new Text("First Name: " + u.getfirstname() + "\n");
            userVBox.getChildren().addAll(userText);
            //adminusersgrid.add(userVBox, 1, rowindex);
            rowindex+=2;
            
            // Edit user page
            GridPane editusergrid = new GridPane();
            Button userspagenavbutton = new Button();
            Image iconImage2 = new Image(MainPage.class.getResourceAsStream("resources/arrow_back_FILL0_wght400_GRAD0_opsz24.png"));
            userspagenavbutton.setGraphic(new ImageView(iconImage2));
            userspagenavbutton.getStyleClass().add("mainpagenavButton");
            editusergrid.add(userspagenavbutton,0,0);
            //editusergrid.setAlignment(Pos.CENTER);
            Text editusertitle = new Text("Edit User Information");
            editusertitle.setFont(Font.font("Roboto", FontWeight.NORMAL, 20));
            editusergrid.add(editusertitle, 1,1, 2, 1);
            TextField editusernamefield = new TextField(u.getusername());
            editusergrid.add(editusernamefield, 1, 1);
            TextField editpasswordfield = new TextField(u.getpassword());
            editusergrid.add(editpasswordfield, 1, 2);
            TextField editfirstnamefield = new TextField(u.getfirstname());
            editusergrid.add(editfirstnamefield, 1, 3);
            TextField editlastnamefield = new TextField(u.getlastname());
            editusergrid.add(editlastnamefield, 1, 4);
            TextField editemailField = new TextField(u.getemail());
            editusergrid.add(editemailField, 1, 5);
            TextField editIDField = new TextField(u.getID());
            editusergrid.add(editIDField, 1, 6);
            Button edituserdataButton = new Button("Edit User");
            editusergrid.add(edituserdataButton, 3, 4);
            final Text usernameempty = new Text();
            editusergrid.add(usernameempty, 4, 2);
            editusergrid.setHalignment(usernameempty, RIGHT);

        usernameempty.setId("usernameexists");

        final Text emptypassword = new Text();
        editusergrid.add(emptypassword, 4, 3);
        editusergrid.setHalignment(emptypassword, RIGHT);
        emptypassword.setId("usernameexists");

        final Text firstnameinvalid = new Text();
        editusergrid.add(firstnameinvalid, 4, 4);
        editusergrid.setHalignment(firstnameinvalid, RIGHT);
        firstnameinvalid.setId("usernameexists");
        final Text lastnameinvalid = new Text();
        editusergrid.add(lastnameinvalid, 4, 5);
        editusergrid.setHalignment(lastnameinvalid, RIGHT);
        lastnameinvalid.setId("usernameexists");

        final Text usernameexists = new Text();
        editusergrid.add(usernameexists, 4, 2);
        editusergrid.setHalignment(usernameexists, RIGHT);
        usernameexists.setId("usernameexists");

        final Text emailexists = new Text();
        editusergrid.add(emailexists, 4, 6);
    
        editusergrid.setHalignment(emailexists, RIGHT);
        emailexists.setId("usernameexists");

        final Text invalidemail = new Text();
        editusergrid.add(invalidemail, 4, 6);

        editusergrid.setHalignment(invalidemail, RIGHT);
        invalidemail.setId("usernameexists");

        final Text idexists = new Text();
        editusergrid.add(idexists, 4, 7);

        editusergrid.setHalignment(idexists, RIGHT);
        idexists.setId("usernameexists");

        final Text idempty = new Text();
        editusergrid.add(idempty, 4, 7);

        editusergrid.setHalignment(idempty, RIGHT);
        idempty.setId("usernameexists");

            Scene edituserscene = new Scene(editusergrid, 1000, 500);
            edituserscene.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());


            mainpagenavButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    primaryStage.setScene(adminScene);
                }
            });
            userspagenavbutton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    primaryStage.setScene(adminusersScene);
                }
            });
            userVBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    System.out.println("Clicked on user: " + u.getusername());
                    GridPane adminusergrid = new GridPane();
                    
                    Button adminuserspageButton = new Button();
                    Image iconImage3 = new Image(MainPage.class.getResourceAsStream("resources/arrow_back_FILL0_wght400_GRAD0_opsz24.png"));
                    adminuserspageButton.setGraphic(new ImageView(iconImage3));
                    adminuserspageButton.getStyleClass().add("mainpagenavButton");
                    adminusergrid.add(adminuserspageButton, 0, 0);
                    Text username = new Text("Username: " + u.getusername() + "\n"+
                    "First Name: "+ u.getfirstname()+ "\n"+ "Last Name: "+u.getlastname()+"\n"+ "Email: "+ u.getemail()+ "\n"+"ID: " + u.getID());
                    adminusergrid.add(username, 0, 2);
                    Button deleteuserButton = new Button("Delete User");
                    adminusergrid.add(deleteuserButton, 1, 5);
                    Button edituserButton = new Button("Edit User");
                    adminusergrid.add(edituserButton, 1, 6);
                    Scene adminuserScene = new Scene(adminusergrid, 1000, 500);
                    adminuserScene.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());
                    primaryStage.setScene(adminuserScene);

                    edituserButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent ev) {
                            primaryStage.setScene(edituserscene);
                        }
                    });

                    deleteuserButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent s) {
                            String username = u.getusername();
                            System.out.println(username);
                            try {
                                Serialize.deleteuser(username);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            AdminPage.loadadminPage(admingrid, primaryStage, adminScene,loginScene);
                            primaryStage.setScene(adminScene);
                        }
                    });
                    adminuserspageButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            primaryStage.setScene(adminusersScene);
                        }
                    });
                }
            });

            edituserdataButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent s) {
                    boolean isvaliddata = true;
                    firstnameinvalid.setText("");
                usernameempty.setText("");
                invalidemail.setText("");
                emailexists.setText("");
                usernameexists.setText("");
                idexists.setText("");
                emptypassword.setText("");
                lastnameinvalid.setText("");
                idempty.setText("");
                    List<User> users = Serialize.readAllUsers();
                    
                    if(editusernamefield.getText().equals("")) {
                        isvaliddata=false;
                        usernameempty.setFill(Color.FIREBRICK);
                        usernameempty.setText("Please Enter a Username");
                    }
    
                    if(editpasswordfield.equals("")) {
                        isvaliddata=false;
                        emptypassword.setFill(Color.FIREBRICK);
                        emptypassword.setText("Please Enter a Passoword");
                    }
    
                    if(!(editemailField).getText().contains("@")) {
                        System.out.println("invalid email");
                        invalidemail.setFill(Color.FIREBRICK);
                        invalidemail.setText("Invalid Email (Must contain @)");
                        isvaliddata=false;
                    }
    
                    if(editfirstnamefield.getText().equals("") || editfirstnamefield.getText().matches(".*\\d.*")) {
                        System.out.println("invalid firstname");
                        firstnameinvalid.setFill(Color.FIREBRICK);
                        firstnameinvalid.setText("Invalid First Name (Make sure it does not contain numbers)");
                        isvaliddata=false;
                    }
    
                    if(editlastnamefield.getText().equals("") || editlastnamefield.getText().matches(".*\\d.*")) {
                        System.out.println("invalid firstname");
                        lastnameinvalid.setFill(Color.FIREBRICK);
                        lastnameinvalid.setText("Invalid Last Name (Make sure it does not contain numbers)");
                        isvaliddata=false;
                    }
                    if(editIDField.getText().equals("")) {
                        System.out.println("invalid firstname");
                        idempty.setFill(Color.FIREBRICK);
                        idempty.setText("Please Enter a Valid Id");
                        isvaliddata=false;
                    }
                    for (User user : users) {
                        if (user.getusername().equals(u.getusername())) {
                            user.setUsername(editusernamefield.getText());
                            user.setPassword(editpasswordfield.getText());
                            user.setFirstname(editfirstnamefield.getText());
                            user.setLastname(editlastnamefield.getText());
                            user.setEmail(editemailField.getText());
                            user.setId(editIDField.getText());
                        }
                    }
                    if(isvaliddata) {
                    for(User user:users) {
                        if(user.getusername().equals(u.getusername())) {continue;}
                        if(user.getID().equals(u.getID())) {continue;}
                        if(user.getusername().equals(editusernamefield.getText())) {
                            System.out.println("Username Exists");
                            usernameexists.setFill(Color.FIREBRICK);
                            usernameexists.setText("Username Exists");
                            isvaliddata=false;
                            break;
                        }
                        else {
                            usernameexists.setText("");
                        }
                        if(user.getemail().equals(editemailField.getText())) {
                            System.out.println("Email Exists");
                            emailexists.setFill(Color.FIREBRICK);
                            emailexists.setText("Email Exists");
                            isvaliddata=false;
                            break;
                        }
                        else{
                            emailexists.setText("");
                        }
                        if(user.getID().equals(editIDField.getText())) {
                            System.out.println("ID Exists");
                            idexists.setFill(Color.FIREBRICK);
                            idexists.setText("ID Exists");
                            isvaliddata=false;
                            break;
                        }
                    }
                }
                    if (isvaliddata) {
                        try {
                            Serialize.writeAllUsers(users);
                            System.out.println("User edited successfully");
                        } catch (IOException e) {
                            System.out.println("Error editing user: " + e.getMessage());
                            e.printStackTrace();
                        }
                        primaryStage.setScene(adminScene);
                    }
                }
            });
            usersContainer.getChildren().add(userVBox);

        }
        ScrollPane scrollpane = new ScrollPane(usersContainer);
        scrollpane.setPrefHeight(300);
        scrollpane.setPrefWidth(400);
        adminusersgrid.add(scrollpane,0,2);
       
        primaryStage.setScene(adminusersScene);
    }
}
