package com.nui.nuibookstore.model;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class OrderInformation implements Serializable {
    private String name;
    private String phone;
    private String homeAddress;
    private String city;
    private String state;
    private List<BookCart> bookCarts;
    private Double totalPrice;
    private String date;

    public OrderInformation() {
    }

    public OrderInformation(String name, String phone, String homeAddress, String city, String state, List<BookCart> bookCarts, Double totalPrice, String date) {
        this.name = name;
        this.phone = phone;
        this.homeAddress = homeAddress;
        this.city = city;
        this.state = state;
        this.bookCarts = bookCarts;
        this.totalPrice = totalPrice;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<BookCart> getBookCarts() {
        return bookCarts;
    }

    public void setBookCarts(List<BookCart> bookCarts) {
        this.bookCarts = bookCarts;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
