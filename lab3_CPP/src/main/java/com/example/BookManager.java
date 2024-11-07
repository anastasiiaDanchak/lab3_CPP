package com.example;

import java.io.*;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

public class BookManager {
    private List<Book> books;

    public BookManager(List<Book> books) {
        this.books = books;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void displayBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
        } else {
            for (Book book : books) {
                System.out.println(book);
            }
        }
    }

    public void writeBooksToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Book book : books) {
                writer.write(book.getTitle() + "," + book.getAuthor() + "," + book.getYear());
                writer.newLine();
            }
            System.out.println("Books have been written to " + filename);
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }

    public void readBooksFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            books.clear(); 
            while ((line = reader.readLine()) != null) {
                String[] bookDetails = line.split(",");
                if (bookDetails.length == 3) {
                    String title = bookDetails[0];
                    String author = bookDetails[1];
                    int year = Integer.parseInt(bookDetails[2]);
                    books.add(new Book(title, author, year));
                }
            }
            System.out.println("Books have been read from " + filename);
        } catch (IOException e) {
            System.out.println("An error occurred while reading from the file: " + e.getMessage());
        }
    }

    public void serializeBooksToNative() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("books.ser"))) {
            for (Book book : books) {
                oos.writeObject(book);
            }
            System.out.println("Books have been serialized to books.ser.");
        } catch (IOException e) {
            System.out.println("An error occurred while serializing books: " + e.getMessage());
        }
    }

    public void deserializeBooksFromNative() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("books.ser"))) {
            books.clear();
            while (true) {
                try {
                    Book book = (Book) ois.readObject();
                    books.add(book);
                } catch (EOFException e) {
                    break; 
                }
            }
            System.out.println("Books have been deserialized.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("An error occurred while deserializing books: " + e.getMessage());
        }
    }

    public void serializeBooksToJson() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File("books.json"), books);
            System.out.println("Books have been serialized to books.json.");
        } catch (IOException e) {
            System.out.println("An error occurred while serializing books to JSON: " + e.getMessage());
        }
    }

    public void deserializeBooksFromJson() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            books = objectMapper.readValue(new File("books.json"), objectMapper.getTypeFactory().constructCollectionType(List.class, Book.class));
            System.out.println("Books have been deserialized from books.json.");
        } catch (IOException e) {
            System.out.println("An error occurred while deserializing books from JSON: " + e.getMessage());
        }
    }

    public void serializeBooksToYaml() {
        try {
            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            Yaml yaml = new Yaml(options);
            FileWriter writer = new FileWriter("books.yaml");
            yaml.dump(books, writer);
            System.out.println("Books have been serialized to books.yaml.");
        } catch (IOException e) {
            System.out.println("An error occurred while serializing books to YAML: " + e.getMessage());
        }
    }

    public void deserializeBooksFromYaml() {
        try {
            Yaml yaml = new Yaml();
            FileReader reader = new FileReader("books.yaml");
            books = yaml.load(reader);
            System.out.println("Books have been deserialized from books.yaml.");
        } catch (IOException e) {
            System.out.println("An error occurred while deserializing books from YAML: " + e.getMessage());
        }
    }
}
