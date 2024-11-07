package com.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.DumperOptions;

import java.io.*;
import java.util.List;

public class BookSerializer {
    private static final String JSON_FILE = "books.json";
    private static final String YAML_FILE = "books.yaml";

    // Серіалізація книг у формат JSON, пропускаючи поле year
    public void serializeBooksToJson(List<Book> books) {
 

        Gson gsonCustom = new GsonBuilder()
                .setPrettyPrinting()
                .addSerializationExclusionStrategy(new com.google.gson.ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(com.google.gson.FieldAttributes f) {
                        return f.getName().equals("year");
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();

        try (Writer writer = new FileWriter(JSON_FILE)) {
            gsonCustom.toJson(books, writer);
            System.out.println("Книги серіалізовані у формат JSON у файл " + JSON_FILE + ".");
        } catch (IOException e) {
            System.out.println("Помилка при серіалізації у JSON: " + e.getMessage());
        }
    }

    // Десеріалізація книг з формату JSON
    public List<Book> deserializeBooksFromJson() {
        Gson gson = new Gson();
        try (Reader reader = new FileReader(JSON_FILE)) {
            Book[] booksArray = gson.fromJson(reader, Book[].class);
            System.out.println("Книги десеріалізовані з файлу " + JSON_FILE + ".");
            return List.of(booksArray);
        } catch (IOException e) {
            System.out.println("Помилка при десеріалізації з JSON: " + e.getMessage());
            return List.of();
        }
    }

    public void serializeBooksToYaml(List<Book> books) {
        DumperOptions options = new DumperOptions();
        options.setIndent(2);
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);

        try (Writer writer = new FileWriter(YAML_FILE)) {
            writer.write("---\n");
            for (Book book : books) {
                writer.write("- title: " + book.getTitle() + "\n");
                writer.write("  author: " + book.getAuthor() + "\n");
                writer.write("  year: " + book.getYear() + "\n");
            }
            System.out.println("Книги серіалізовані у формат YAML у файл " + YAML_FILE + ".");
        } catch (IOException e) {
            System.out.println("Помилка при серіалізації у YAML: " + e.getMessage());
        }
    }


    // Десеріалізація книг з формату YAML
    public List<Book> deserializeBooksFromYaml() {
        Yaml yaml = new Yaml(new Constructor(Book.class));
        List<Book> deserializedBooks = new java.util.ArrayList<>();
        try (Reader reader = new FileReader(YAML_FILE)) {
            Iterable<Object> objects = yaml.loadAll(reader);
            for (Object obj : objects) {
                if (obj instanceof Book) {
                    deserializedBooks.add((Book) obj);
                }
            }
            System.out.println("Книги десеріалізовані з файлу " + YAML_FILE + ".");
        } catch (IOException e) {
            System.out.println("Помилка при десеріалізації з YAML: " + e.getMessage());
        }
        return deserializedBooks;
    }

}
