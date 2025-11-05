package com.system.model;

public class Dish {
    public int id;
    public String name;
    public String image;
    public int priceN;
    public int priceL;

    public Dish(int id, String name, String image, int priceN, int priceL) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.priceN = priceN;
        this.priceL = priceL;
    }
}