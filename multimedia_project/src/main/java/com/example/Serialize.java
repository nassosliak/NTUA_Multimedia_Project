package com.example;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Serialize extends App{
    //books object serializing
    public static void saveBook(Book b) throws IOException,ClassNotFoundException  {
        List<Book> books = readAllBooks();
        books.add(b);
        String usersFilePath = getFilePath("medialab/books.ser");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(usersFilePath))) {
            oos.writeObject(books);
        }
    }

    final static boolean isValidUser(String username, String password) {
        List<User> users = readAllUsers();
    
        for (User user : users) {
            if (user.getusername().equals(username) && user.getpassword().equals(password)) {
                return true;
            }
        }
    
        return false;
    }
    public static void readBook() throws IOException, ClassNotFoundException {
        String usersFilePath = getFilePath("medialab/books.ser");
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(usersFilePath))) {
            Book b = (Book) ois.readObject();
        }
    }
    public static List<Book> readAllBooks() {
    List<Book> books;
    String usersFilePath = getFilePath("medialab/books.ser");
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(usersFilePath))) {
        Object obj = ois.readObject();

        if (obj instanceof List) {
            books = (List<Book>) obj;
        } else {
            // Handle the case where the file doesn't contain a list of books
            books = new ArrayList<>();
        }
    } catch (FileNotFoundException e) {
        // If the file doesn't exist, return an empty list
        books = new ArrayList<>();
    } catch (IOException | ClassNotFoundException e) {

        e.printStackTrace();
        books = new ArrayList<>();
    }
    return books;
}
public static void writeAllBooks(List<Book> books) throws IOException {
    createMedialabFolderIfNotExists();

    String usersFilePath = getFilePath("medialab/books.ser");
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(usersFilePath))) {
        oos.writeObject(books);
    }
}

public static void deleteBook(String ISBN) throws IOException {
    // Read all categories from the file
    List<Book> books = Serialize.readAllBooks();

    // Iterate through the categories to find the one to delete
    Iterator<Book> iterator = books.iterator();
    List<User> users = Serialize.readAllUsers();
    while (iterator.hasNext()) {
        Book b = iterator.next();
        for(User u:users) {
                    
            if(u.getBorrowedBooks()!=null) {
                List<Book> borrowedBooks = u.getBorrowedBooks();
                List<LocalDateTime> borrowingDates = u.getBorrowingDates();
            if(u.getBorrowedBooks().size()==1 && u.borrowedbooks.get(0).getISBN()==(b.getISBN())) {
                borrowedBooks.remove(0);
                System.out.println("Book "+ b.getISBN()  + " removed");
                borrowingDates.remove(0);
            }
            if(u.getBorrowedBooks().size()==2 && (u.getBorrowedBooks().get(0).getISBN()==(b.getISBN()))) {
                borrowedBooks.remove(0);
                System.out.println("Book "+ b.getISBN()  + " removed");
                borrowingDates.remove(0);
            }
            if(u.getBorrowedBooks().size()==2 && u.getBorrowedBooks().get(1).getISBN()==(b.getISBN())) {
                borrowedBooks.remove(1);
                System.out.println("Book "+ b.getISBN()  + " removed");
                borrowingDates.remove(1);
            }
            try {
                Serialize.updateUser(u);
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        }
        if (b.getISBN().equals(ISBN)) {
            
            iterator.remove();
            System.out.println("Book deleted successfully: " + ISBN);
        }
    }

    // Rewrite the updated list of categories back to the file
    Serialize.writeAllBooks(books);
}

public static void editBook(String ISBN, String title, String Publisher, String Writer,int year_of_publish, String Category,int numberofbooks) throws IOException {
    // Read all categories from the file
    List<Book> books = Serialize.readAllBooks();

    // Iterate through the categories to find the one to edit
    for (Book b : books) {
        if (b.getISBN().equals(ISBN)) {
            b.setTitle(title);
            b.setCategory(Category);
            b.setWriter(Writer);
            b.setyearofpublish(year_of_publish);
            b.setPublisher(Publisher);
            b.setNumberofBooks(numberofbooks);
            System.out.println("Book updated successfully");
            break;
        }
    }

    // Rewrite the updated list of categories back to the file
    Serialize.writeAllBooks(books);
}

//users object serializing
public static void saveUser(User user) throws IOException, ClassNotFoundException {
    List<User> users = readAllUsers();
    users.add(user);
    writeAllUsers(users);
}

public static void updateUser(User updatedUser) throws IOException, ClassNotFoundException {
    List<User> users = readAllUsers();

    // Find the user to update based on some criteria (e.g., username or ID)
    for (int i = 0; i < users.size(); i++) {
        User existingUser = users.get(i);
        if (existingUser.getID().equals(updatedUser.getID())) {
            // Update the user information
            users.set(i, updatedUser);
            break;
        }
    }

    writeAllUsers(users);
}
public static void deleteuser(String username) throws IOException {
    
    List<User> users = Serialize.readAllUsers();

    
    Iterator<User> iterator = users.iterator();
    List<Book> books = readAllBooks();
    while (iterator.hasNext()) {
        User user = iterator.next();
        if (user.getusername().equals(username)) {
            if(user.getBorrowedBooks()!=null) {
            for (Book b:user.getBorrowedBooks()) {
            
                for (Book book : books) {
                    if (book.getISBN()==(b.getISBN())) {
                        // Increment the number of available books
                        book.setNumberofBooks(book.getNumberofBooks() + 1);
                        break; // Once found, break the loop
                    }
                }
            System.out.println("Book Returned: " + b);
            }
            
        }
            iterator.remove();
            System.out.println("User deleted successfully: " + username);
            break;
            }
        
            
        
    }
    try {
        Serialize.writeAllBooks(books);
        } catch (IOException e) {
        e.printStackTrace();
        }
    Serialize.writeAllUsers(users);
}
public static void writeAllUsers(List<User> users) throws IOException {
    createMedialabFolderIfNotExists();

    String usersFilePath = getFilePath("medialab/users.ser");
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(usersFilePath))) {
        oos.writeObject(users);
    }
}
public static User findUserByUsername(List<User> userList, String username) {
    for (User user : userList) {
        if (user.getusername().equals(username)) {
            return user;
        }
    }
    return null; // User not found
}

public static List<User> readAllUsers() {
    List<User> users;
    String usersFilePath = getFilePath("medialab/users.ser");
       
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(usersFilePath))) {
        Object obj = ois.readObject();

        if (obj instanceof List) {
            users = (List<User>) obj;
        } else {
            
            users = new ArrayList<>();
        }
    } catch (FileNotFoundException e) {
        // If the file doesn't exist, return an empty list
        users = new ArrayList<>();
    } catch (IOException | ClassNotFoundException e) {

        e.printStackTrace();
        users = new ArrayList<>();
    }
    return users;
}
private static String getFilePath(String relativePath) {
    String currentDirectory = System.getProperty("user.dir");
    return currentDirectory +   File.separator+"multimedia_project" +File.separator + relativePath;
}
private static void createMedialabFolderIfNotExists() {
        String medialabFolderPath = getFilePath("medialab");
        File medialabFolder = new File(medialabFolderPath);
        if (!medialabFolder.exists()) {
            boolean created = medialabFolder.mkdirs();
            if (!created) {
                System.err.println("Failed to create medialab folder.");
            }
        }
    }

    public static List<Category> readAllCategories() {
        List<Category> categories;
        String usersFilePath = getFilePath("medialab/categories.ser");
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(usersFilePath))) {
            Object obj = ois.readObject();
    
            if (obj instanceof List) {
                categories = (List<Category>) obj;
            } else {
                
                categories = new ArrayList<>();
            }
        } catch (FileNotFoundException e) {
            // If the file doesn't exist, return an empty list
            categories = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
    
            e.printStackTrace();
            categories = new ArrayList<>();
        }
        return categories;
    }
    private static void writeAllCategories(List<Category> categories) throws IOException {
        createMedialabFolderIfNotExists();
        String usersFilePath = getFilePath("medialab/categories.ser");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(usersFilePath))) {
            oos.writeObject(categories);
        }
    }
    public static void saveCategory(Category category) throws IOException, ClassNotFoundException {
        List<Category> categories = readAllCategories();
        categories.add(category);
        writeAllCategories(categories);
    }

    public static void deleteCategory(String categoryName) throws IOException {
        // Read all categories from the file
        List<Category> categories = Serialize.readAllCategories();
        List <Book> books = Serialize.readAllBooks();
        List <User> users = Serialize.readAllUsers();
        // Iterate through the categories to find the one to delete
        Iterator<Category> iterator = categories.iterator();
        Iterator <Book> iterator_book = books.iterator();
        while(iterator_book.hasNext()){
            Book b = iterator_book.next();
            if(b.getCategory().equals(categoryName)) {
                for(User u:users) {
                    
                    if(u.getBorrowedBooks()!=null) {
                        List<Book> borrowedBooks = u.getBorrowedBooks();
                        List<LocalDateTime> borrowingDates = u.getBorrowingDates();
                    if(u.getBorrowedBooks().size()==1 && u.borrowedbooks.get(0).getISBN()==(b.getISBN())) {
                        borrowedBooks.remove(0);
                        System.out.println("Book "+ b.getISBN()  + " removed");
                        borrowingDates.remove(0);
                    }
                    if(u.getBorrowedBooks().size()==2 && (u.getBorrowedBooks().get(0).getISBN()==(b.getISBN()))) {
                        borrowedBooks.remove(0);
                        System.out.println("Book "+ b.getISBN()  + " removed");
                        borrowingDates.remove(0);
                    }
                    if(u.getBorrowedBooks().size()==2 && u.getBorrowedBooks().get(1).getISBN()==(b.getISBN())) {
                        borrowedBooks.remove(1);
                        System.out.println("Book "+ b.getISBN()  + " removed");
                        borrowingDates.remove(1);
                    }
                    try {
                        Serialize.updateUser(u);
                    } catch (ClassNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                }
                System.out.println("Book "+b.getTitle()+ " deleted");
                iterator_book.remove();
                
            }
        }
        while (iterator.hasNext()) {
            Category cat = iterator.next();
            if (cat.getTitle().equals(categoryName)) {
                
                iterator.remove();
                System.out.println("Category deleted successfully: " + categoryName);
            }
        }
    
        // Rewrite the updated list of categories back to the file
        Serialize.writeAllCategories(categories);
        Serialize.writeAllBooks(books);
        
    }

    public static void editCategory(String currentCategoryName, String newCategoryName) throws IOException {
        // Read all categories from the file
        List<Category> categories = Serialize.readAllCategories();
    
        // Iterate through the categories to find the one to edit
        for (Category cat : categories) {
            if (cat.getTitle().equals(currentCategoryName)) {
                cat.setTitle(newCategoryName);
                System.out.println("Category name updated successfully from \"" + currentCategoryName + "\" to \"" + newCategoryName + "\"");
                break;
            }
        }
    

        Serialize.writeAllCategories(categories);
    }
    public static void writereturntime(AdminReturnTime returnTime) throws IOException {
        createMedialabFolderIfNotExists();
        String usersFilePath = getFilePath("medialab/returntime.ser");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(usersFilePath))) {
            oos.writeObject(returnTime);
            //System.out.println("Serialized return time to"+ returnTime.getdate());
        }
    }
    public static AdminReturnTime readreturntime() throws IOException, ClassNotFoundException {
        String filePath = getFilePath("medialab/returntime.ser");
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            Object obj = ois.readObject();
            if (obj instanceof AdminReturnTime) {
                //System.out.println("Succesful read");
                return (AdminReturnTime) obj;
            } else {
                // If the object is not found or is null, return a default value
                //System.out.println("Object not found");
                return new AdminReturnTime(5 * 86400);
            }
        } catch (FileNotFoundException e) {
            // If the file is not found, return a default value
            //System.out.println("File not found");
            return new AdminReturnTime(5 * 86400);
        }
    }
    
}