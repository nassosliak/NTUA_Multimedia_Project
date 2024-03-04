package com.example;
import static javafx.geometry.HPos.*;

import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
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
        int rowindex = 1;
        for (User u : users) {
            if (u.getusername().equals("Admin")) {
                continue;
            }
            VBox userVBox = new VBox();
            Text userText = new Text("User: " + u.getusername() + "\n");
            adminusersgrid.add(userText, 1, rowindex);
            Text userfirstnameText = new Text("First Name: " + u.getfirstname() + "\n");
            adminusersgrid.add(userfirstnameText, 1, 1 + rowindex);
            adminusersgrid.add(userVBox, 1, rowindex);
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
            TextField editusernamefield = new TextField("username");
            editusergrid.add(editusernamefield, 1, 1);
            PasswordField editpasswordfield = new PasswordField();
            editusergrid.add(editpasswordfield, 1, 2);
            TextField editfirstnamefield = new TextField("firstname");
            editusergrid.add(editfirstnamefield, 1, 3);
            TextField editlastnamefield = new TextField("lastname");
            editusergrid.add(editlastnamefield, 1, 4);
            TextField editemailField = new TextField("email");
            editusergrid.add(editemailField, 1, 5);
            TextField editIDField = new TextField("ID");
            editusergrid.add(editIDField, 1, 6);
            Button edituserdataButton = new Button("Edit User");
            editusergrid.add(edituserdataButton, 3, 4);
            final Text usernameexists = new Text();
            editusergrid.add(usernameexists, 4, 1);
            editusergrid.setColumnSpan(usernameexists, 2);
            editusergrid.setHalignment(usernameexists, RIGHT);
            usernameexists.setId("usernameexists");

            final Text emailexists = new Text();
            editusergrid.add(emailexists, 4, 5);
            editusergrid.setColumnSpan(emailexists, 2);
            editusergrid.setHalignment(emailexists, RIGHT);
            emailexists.setId("emailexists");

            final Text idexists = new Text();
            editusergrid.add(idexists, 4, 6);
            editusergrid.setColumnSpan(idexists, 2);
            editusergrid.setHalignment(idexists, RIGHT);
            idexists.setId("idexists");

            final Text invalidemail = new Text();
            editusergrid.add(invalidemail, 4, 5);
            editusergrid.setColumnSpan(invalidemail, 2);
            editusergrid.setHalignment(invalidemail, RIGHT);
            invalidemail.setId("invalidemail");

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
                    Text username = new Text(u.getusername());
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
                    boolean isvalid = true;
                    invalidemail.setText("");
                    emailexists.setText("");
                    usernameexists.setText("");
                    idexists.setText("");
                    List<User> users = Serialize.readAllUsers();
                    if (!(editemailField.getText()).contains("@")) {
                        System.out.println("invalid email");
                        invalidemail.setFill(Color.FIREBRICK);
                        invalidemail.setText("Invalid Email");
                        isvalid = false;
                    }

                    for (User us : users) {
                        if(us.getusername().equals(u.getusername())) {continue;}
                        if (us.getusername().equals(editusernamefield.getText())) {
                            System.out.println("Username Exists");
                            usernameexists.setFill(Color.FIREBRICK);
                            usernameexists.setText("Username Exists");
                            isvalid = false;
                            break;
                        } else {
                            usernameexists.setText("");
                        }
                        if (us.getemail().equals(editemailField.getText())) {
                            System.out.println("Email Exists");
                            emailexists.setFill(Color.FIREBRICK);
                            emailexists.setText("Email Exists");
                            isvalid = false;
                            break;
                        } else {
                            emailexists.setText("");
                        }
                        if (us.getID().equals(editIDField.getText())) {
                            System.out.println("ID Exists");
                            idexists.setFill(Color.FIREBRICK);
                            idexists.setText("ID Exists");
                            isvalid = false;
                            break;
                        }
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
                    if (isvalid) {
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
        }

       
        primaryStage.setScene(adminusersScene);
    }
}
