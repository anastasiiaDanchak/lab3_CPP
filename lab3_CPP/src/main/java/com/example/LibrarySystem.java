package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LibrarySystem {
    private List<Book> books;
    private BookManager bookManager;

    public LibrarySystem() {
        books = new ArrayList<>();
        bookManager = new BookManager(books);
    }

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("--- Library System Menu ---");
            System.out.println("1. Add a book");
            System.out.println("2. Display books");
            System.out.println("3. Write books to file");
            System.out.println("4. Read books from file");
            System.out.println("5. Serialize books (Java Native)");
            System.out.println("6. Deserialize books (Java Native)");
            System.out.println("7. Serialize books to JSON");
            System.out.println("8. Deserialize books from JSON");
            System.out.println("9. Serialize books to YAML");
            System.out.println("10. Deserialize books from YAML");
            System.out.println("11. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Clear the buffer

            switch (choice) {
                case 1:
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter book author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter book year: ");
                    int year = scanner.nextInt();
                    scanner.nextLine(); // Clear the buffer
                    bookManager.addBook(new Book(title, author, year));
                    break;
                case 2:
                    bookManager.displayBooks();
                    break;
                case 3:
                    System.out.print("Enter file name to save: ");
                    String filenameWrite = scanner.nextLine();
                    bookManager.writeBooksToFile(filenameWrite);
                    break;
                case 4:
                    System.out.print("Enter file name to read from: ");
                    String filenameRead = scanner.nextLine();
                    bookManager.readBooksFromFile(filenameRead);
                    break;
                case 5:
                    bookManager.serializeBooksToNative();
                    break;
                case 6:
                    bookManager.deserializeBooksFromNative();
                    break;
                case 7:
                    bookManager.serializeBooksToJson();
                    break;
                case 8:
                    bookManager.deserializeBooksFromJson();
                    break;
                case 9:
                    bookManager.serializeBooksToYaml();
                    break;
                case 10:
                    bookManager.deserializeBooksFromYaml();
                    break;
                case 11:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    public static void main(String[] args) {
        LibrarySystem system = new LibrarySystem();
        system.displayMenu();
    }
}
