package com.example;

import java.io.Serializable;

public class Book implements Serializable {
    private static final long serialVersionUID = 1L; 
    private String title;
    private String author;
    private transient int year; 
    public Book() {}

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = (author != null && !author.isEmpty()) ? author : null; 
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = (author != null && !author.isEmpty()) ? author : null; 
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        String authorDisplay = (author != null) ? author : "No Author";
        return "Title: " + title + ", Author: " + authorDisplay + ", Year: " + year;
    }
}
