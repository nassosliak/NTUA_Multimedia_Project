package com.example;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ReturnTime extends Thread {
    private volatile boolean running = true;
    private List<User> users;
    private List<Book> books;
    private GridPane maingrid;
    private Stage primaryStage;
    private Scene loginScene;
    private User currentUser;
    private TextField searchbar;
    private TextField searchbar_writer;
    private TextField searchbar_year;
    private Scene mainScene;
    private final ReentrantLock lock = new ReentrantLock();

    public ReturnTime(List<User> users, List<Book> books, GridPane maingrid, Stage primaryStage, Scene loginScene,Scene mainScene,
                      TextField searchbar, TextField searchbar_writer, TextField searchbar_year
) {
        super("ReturnTimeThread");
        this.maingrid = maingrid;
        this.primaryStage = primaryStage;
        this.searchbar = searchbar;
        this.searchbar_writer = searchbar_writer;
        this.searchbar_year = searchbar_year;
        this.mainScene = mainScene;

        this.loginScene=loginScene;
    }
    AdminReturnTime returnTime;
    @Override
public void run() {
    while (running) {
        try {
            Thread.sleep(60000);//run check every minute
            
            
    try {
        returnTime = Serialize.readreturntime();
    } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
        returnTime = new AdminReturnTime(5 * 86400);
    }
            List<User> users = Serialize.readAllUsers();
            System.out.println("Read from serialized and got "+ returnTime.getdate());
            if (users != null) {
                LocalDateTime currentDate = LocalDateTime.now();
                
                
                for (User u : users) {
                    List<LocalDateTime> borrowingDates = u.getBorrowingDates();
                    List<Book> borrowedBooks = u.getBorrowedBooks();
                    List<Book> books = Serialize.readAllBooks();
                    
                    for (int i = 0; i < borrowingDates.size(); i++) {
                        LocalDateTime borrowingDate = borrowingDates.get(i);
                        
                        if (currentDate.minusSeconds(returnTime.getdate()).isAfter(borrowingDate)) {
                            

                            Book returnedBook = borrowedBooks.remove(i);
                            returnedBook.setNumberofBooks(returnedBook.getNumberofBooks() + 1);
                            borrowingDates.remove(i);
                            
                            
                            try {
                                Platform.runLater(() -> {
                                    String storedUsername = SessionManager.getInstance().getStoredUsername();
                                    currentUser = Serialize.findUserByUsername(users, storedUsername);

                                    if(currentUser.getusername().equals(u.getusername())) {
                                    if(!currentUser.getusername().equals("Admin")) {
                                    primaryStage.setScene(loginScene);
                                    }
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Information Dialog");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Book: "+returnedBook.getTitle()+" will be returned shortly");
                                    alert.showAndWait();
                                    
                                    //MainPage.updateMainGrid(maingrid, books, primaryStage, loginScene, currentUser, searchbar,searchbar_writer,searchbar_year, mainScene);
                                    
                                    }
                                    System.out.println("Book returned due to time limit exceeded: " + returnedBook);
                                });
                            } finally {
                                
                            }
                            
                            try {
                                Serialize.writeAllBooks(books);
                                Serialize.writeAllUsers(users);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
               
                System.out.println("Checking borrowing dates...");
                System.out.println("Current return date set to "+ returnTime.getdate()+" seconds");
 
                
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


    public void shutdown() {
        running = false; // Set the running flag to false to stop the thread
    }
}