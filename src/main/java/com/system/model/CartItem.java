package com.system.model;

public class CartItem {
    public int id;
    public String name;
    public String size;
    public int quantity;
    public int unitPrice;
    public int totalPrice;

    public CartItem(int id, String name, String size, int quantity, int unitPrice) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = quantity * unitPrice;
    }
}