package com.example.demo;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.Book;
import com.example.demo.service.BookService;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;



import java.util.List;
import java.util.Map;

@SpringBootApplication
@RestController
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}

	@PostMapping("/api/login")
	public User login(@RequestBody Map<String, Object> payload) {
		String username = (String) payload.get("username");
		String password = (String) payload.get("password");
		System.out.println("Got the parameters");
		// Here, you should check the username and password, for example:
		if (UserService.validateUser(username, password) != null) {
			System.out.println(UserService.validateUser(username, password).getName());
			return UserService.validateUser(username, password);
		} else {
			return new User("Null", "Null", "Null");
		}
	}

	public static class LoginResponse {
		private final boolean success;

		public LoginResponse(boolean success) {
			this.success = success;
		}

		public boolean isSuccess() {
			return success;
		}
	}
	@PostMapping("/api/register")
	public void register(@RequestBody Map<String, Object> payload) {
		String username = (String) payload.get("username");
		String password = (String) payload.get("password");
		String fullName = (String) payload.get("fullName");
		//UserService.addUser(new User(username, password));
		User newOne = new User(fullName, username, password);
		UserService.addUser(newOne);
	}

	@GetMapping("/api/users")
	public List<User> getUsers() {
		return UserService.getUsers();
	}

	@GetMapping("/api/books")
	public List<Book> getBooks() {
		System.out.println("inside getBooks method");
		for(Book book : BookService.getBooks()){
			System.out.println(book.getTitle());
		}
		return BookService.getBooks();

	}
	@PostMapping("/api/search")
	public List<Book> searchBooks(@RequestBody Map<String, Object> payload) {
		String searchTerm = (String) payload.get("searchTerm");
		// Implement your search logic here, e.g., search for books matching the searchTerm

		// Return the search results as a List<Book>
		// Make sure to modify this based on your search implementation
		return BookService.searchBooks(searchTerm);
	}
	@PostMapping("/api/purchase")
	public void purchaseBook(@RequestBody Map<String, Object> payload) {
		//String username = "barak.goren"; // Replace with the actual implementation to get the username
		String isbn = (String) payload.get("isbn");
		String username = (String) payload.get("username");
		System.out.println(username);
		String filename = username + "_purchases.txt";
		String filePath = "/Users/barakgoren/Downloads/demo/src/main/resources/Purchases/" + filename;

		try {
			File file = new File(filePath);
			FileWriter fileWriter = new FileWriter(file, true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			// Check if the ISBN already exists in the file
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			boolean isbnExists = bufferedReader.lines().anyMatch(line -> line.equals(isbn));
			bufferedReader.close();

			if (!isbnExists) {
				bufferedWriter.write(isbn);
				bufferedWriter.newLine();
				bufferedWriter.close();
			} else {
				// Handle the case where the ISBN already exists in the file
				System.out.println("The book with ISBN " + isbn + " has already been purchased.");
			}
		} catch (IOException e) {
			// Handle file write error
			e.printStackTrace();
		}
	}

	@GetMapping("/api/user-books")
	public List<Book> getUserBooks(@RequestParam String username) {
		// Assuming you have a method in BookService to retrieve books by username
		List<Book> userBooks = BookService.getUserBooks(username);
		System.out.println(username);
		for(Book book : userBooks){
			System.out.println(book.getTitle());
		}
		return userBooks;
	}



}
