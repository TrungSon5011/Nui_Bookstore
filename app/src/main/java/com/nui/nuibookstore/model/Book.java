package com.nui.nuibookstore.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

@Entity
public class Book implements Comparable<Book>, Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "book_name")
    private String name;
    @ColumnInfo(name = "author")
    private String author;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "genre")
    private String genre;
    @ColumnInfo(name = "price")
    private Double price;
    @ColumnInfo(name = "picture")
    private int pictureResourceId;

    public Book() {
    }

    public Book(String name, String author, String description, String genre, Double price, int pictureResourceId) {
        this.name = name;
        this.author = author;
        this.description = description;
        this.genre = genre;
        this.price = price;
        this.pictureResourceId = pictureResourceId;
    }

    public Book(int id, String name, String author, String description, String genre, Double price, int pictureResourceId) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.description = description;
        this.genre = genre;
        this.price = price;
        this.pictureResourceId = pictureResourceId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getPictureResourceId() {
        return pictureResourceId;
    }

    public void setPictureResourceId(int pictureResourceId) {
        this.pictureResourceId = pictureResourceId;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", genre='" + genre + '\'' +
                ", price=" + price +
                ", pictureResourceId=" + pictureResourceId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id &&
                pictureResourceId == book.pictureResourceId &&
                Objects.equals(name, book.name) &&
                Objects.equals(author, book.author) &&
                Objects.equals(description, book.description) &&
                Objects.equals(genre, book.genre) &&
                Objects.equals(price, book.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, author, description, genre, price, pictureResourceId);
    }

    @Override
    public int compareTo(Book o) {
        if (this.name != o.getName()){
            return -1;
        }
        return 0;
    }
}
