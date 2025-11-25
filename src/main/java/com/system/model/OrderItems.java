// CartItem.java
package com.system.model;

public class OrderItems {
//    private int dishId;
    private String dishName;
    private String size;
    private double price;
    private int quantity;
    private String imagePath;
    private String dishCode;

    public OrderItems() {}

    public OrderItems( String dishName, String size, double price, int quantity, String imagePath, String dishCode) {
//        this.dishId = dishId;
        this.dishName = dishName;
        this.size = size;
        this.price = price;
        this.quantity = quantity;
        this.imagePath = imagePath;
        this.dishCode = dishCode;
    }

    // Getters and Setters
//    public int getDishId() { return dishId; }
//    public void setDishId(int dishId) { this.dishId = dishId; }

    public String getDishName() { return dishName; }
    public void setDishName(String dishName) { this.dishName = dishName; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public String getDishCode() { return dishCode; }
    public void setDishCode(String dishCode) { this.dishCode = dishCode; }

    public double getTotalPrice() {
        return price * quantity;
    }
}