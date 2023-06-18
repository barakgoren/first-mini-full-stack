// src/main/java/com/example/demo/service/UserService.java

package com.example.demo.service;
import com.example.demo.model.User;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private static final String FILE_PATH = "src/main/resources/users.txt";

    public static ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                String username = parts[1];
                String password = parts[2];
                users.add(new User(name, username, password));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static void addUser(User user) {
        ArrayList<User> users = getUsers();
        if(user.isExist(users)){
            System.out.println("User already exist!");
        } else {
            try (FileWriter fw = new FileWriter(FILE_PATH, true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                out.println(user.getName() + "," + user.getUsername() + "," + user.getPassword());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static User validateUser(String username, String password) {
        System.out.println("Inside validateUser method");
        List<User> users = getUsers();
        System.out.println(getUsers().get(0).getUsername());
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}
