// src/main/java/com/example/demo/service/BookService.java

package com.example.demo.service;

import com.example.demo.model.Book;
import java.util.stream.Collectors;
import com.example.demo.model.User;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;

public class BookService {

    private static final String BOOKS_FILE = "src/main/resources/books.txt";
    private static List<Book> books = new ArrayList<>();

    static {
        // Load books from file
        try (BufferedReader reader = new BufferedReader(new FileReader(BOOKS_FILE))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    // skip the header line
                    isFirstLine = false;
                    continue;
                }
                String[] bookData = line.split(", ");
                String title = bookData[0];
                String author = bookData[1];
                double price = Double.parseDouble(bookData[2]);
                String isbn = bookData[3];

                books.add(new Book(title, author, price, isbn));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Book> getBooks() {
        return new ArrayList<>(books);
    }

    public static List<Book> searchBooksByTitle(String title) {
        List<Book> results = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                results.add(book);
            }
        }
        return results;
    }
    public static List<Book> searchBooks(String searchTerm) {
        // Implement your search logic here to search for books based on the searchTerm
        // Return the search results as a List<Book>
        // Modify this code based on your search implementation

        // For example, you can filter the books based on the searchTerm
        return books.stream()
                .filter(book -> book.getTitle().contains(searchTerm) || book.getAuthor().contains(searchTerm))
                .collect(Collectors.toList());
    }
    public static ArrayList<Book> getUserBooks(String username){
        ArrayList<Book> resultList = new ArrayList<Book>();
        File userPurchases = new File("src/main/resources/Purchases/"+username + "_purchases.txt");
        try{
            Scanner reader = new Scanner(userPurchases);
            while(reader.hasNextLine()){
                String isbn = reader.nextLine();
                resultList.add(getBookByIsbn(isbn));
            }
        } catch (FileNotFoundException e){
            System.out.println("File not found");
        }
        return resultList;
    }
    public static Book getBookByIsbn(String isbn){
        for(Book book : books){
            if(book.getIsbn().equals(isbn)){
                return book;
            }
        }
        return null;
    }


    // You can implement more search methods like searchBooksByAuthor, etc.
}
