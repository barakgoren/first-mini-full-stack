// src/main/java/com/example/demo/model/User.java

package com.example.demo.model;
import java.util.ArrayList;

public class User {
    private String name;
    private String username;
    private String password;
    private ArrayList<Book> books;
    // other fields...

    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {return this.username;}
    public String getPassword() {return this.password;}
    public String getName() {return this.name;}
    public boolean isExist(ArrayList<User> users){
        for(User user : users){
            if(user.getUsername().equals(this.username)){
                return true;
            }
        }
        return false;
    }
    public void setBooks(ArrayList<Book> userBooks){
        this.books = userBooks;
    }
}
