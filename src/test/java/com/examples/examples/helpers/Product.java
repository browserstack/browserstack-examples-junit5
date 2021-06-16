package com.examples.examples.helpers;

public class Product {

    private String id;
    private String brand;
    private String device;
    private int price;

    public Product(String id, String brand, String device, int price) {
        this.id = id;
        this.brand = brand;
        this.device = device;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getDevice() {
        return device;
    }

    public int getPrice() {
        return price;
    }
}