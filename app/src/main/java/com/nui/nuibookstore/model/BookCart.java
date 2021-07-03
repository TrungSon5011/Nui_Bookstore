package com.nui.nuibookstore.model;

import java.io.Serializable;

public class BookCart implements Serializable {
    private Book book;
    private Integer quantity;

    public BookCart(Book book, Integer quantity) {
        this.book = book;
        this.quantity = quantity;
    }

    public BookCart() {
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "BookCart{" +
                "book=" + book +
                ", quantity=" + quantity +
                '}';
    }
}
