package com.example;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
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
        Login.loadloginpage(grid,primaryStage,registerscene,maingrid,adminScene,mainScene,currentUser);
        Register.loadregisterpage(registergrid, primaryStage, loginscene);
    }
public static void main(String[] args) {
    launch(args);
}
}



