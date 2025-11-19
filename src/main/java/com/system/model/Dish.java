package com.system.model;

import java.sql.Timestamp;

public class Dish {
    private int id;
    private String dishCode;
    private String category;
    private String name;
    private String size;
    private Double priceN; // Normal price
    private Double priceL; // Large price
    private String imagePath;
    private boolean isAvailable;
    private Timestamp created_at;
    private Timestamp updated_at;
    
    //constructors
    public Dish() {
    	
    }
    
    //Single size dishes(for adding dishes)
    public Dish(String category, String name, String size, Double price, String imagePath, boolean isAvailable) {
        this.category = category;
        this.name = name;
        this.size = size;
        if("Normal".equals(size)) {
            this.priceN = price;
            this.priceL = null;
        } else if("Large".equals(size)) {
            this.priceL = price;
            this.priceN = null;
        }
        this.imagePath = imagePath;
        this.isAvailable = isAvailable;
    }
    
    //For both size(for adding dishes)
    public Dish(String category, String name, String size, Double priceN, Double priceL, String imagePath, boolean isAvailable) {
        this.category = category;
        this.name = name;
        this.size = "Both";
        this.priceN = priceN;
        this.priceL = priceL;
        this.imagePath = imagePath;
        this.isAvailable = isAvailable;
    }
    
    //For single size(For updating dishes)
    public Dish(int id, String category, String name, String size, Double price, String imagePath, boolean isAvailable) {
    	this.id = id;
    	this.category = category;
    	this.name= name;
    	this.size = size;
    	if("Normal".equals(size)) {
    		this.priceN = price;
    		this.priceL = null;
    	} else if("Large".equals(size)){
    		this.priceL = price;
    		this.priceN = null;
    	}
    	this.imagePath = imagePath;
    	this.isAvailable = isAvailable;
    }
    
    //For both size(For updating dishes)
    public Dish(int id, String category, String name, String size, Double priceN, Double priceL, String imagePath, boolean isAvailable) {
    	this.id = id;
    	this.category = category;
    	this.name = name;
    	this.size = size;
    	this.priceN = priceN;
    	this.priceL = priceL;
    	this.imagePath = imagePath;
    	this.isAvailable = isAvailable;
    }
    
    //Full constructors
    public Dish(int id, String dishCode, String category, String name, String size, Double priceN, Double priceL, String imagePath, boolean isAvailable) {
    	this.id = id;
    	this.dishCode = dishCode;
    	this.category = category;
    	this.name = name;
    	this.size = size;
    	this.priceN = priceN;
    	this.priceL = priceL;
    	this.imagePath= imagePath;
    	this.isAvailable = isAvailable;
    }

	public int getId() { return id; }
	public void setId(int id) { this.id = id; }

	public String getDishCode() { return dishCode; }
	public void setDishCode(String dishCode) { this.dishCode = dishCode; }

	public String getCategory() { return category; }
	public void setCategory(String category) { this.category = category; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public String getSize() { return size; }
	public void setSize(String size) { this.size = size; }

	public Double getPriceN() { return priceN; }
	public void setPriceN(Double priceN) { this.priceN = priceN; }

	public Double getPriceL() { return priceL; }
	public void setPriceL(Double priceL) { this.priceL = priceL; }
	
	public String getDisplayPrice() {
		if("Both".equals(size)) {
			return String.format("Normal: Rs. %.2f, Large: Rs. %.2f", priceN, priceL);
		} else if ("Normal".equals(size)) {
			return String.format("Rs. %.2f", priceN);
		} else if("Large".equals(size)) {
			return String.format("Rs. %.2f", priceL);
		}
		
		return "Price not set";
	}
	
	public Double getMinPrice() {
		if("Both".equals(size)) {
			return Math.min(priceN, priceL);
		} else if("Normal".equals(size)) {
			return priceN;
		} else if("Large".equals(size)) {
			return priceL;
		}
		
		return 0.0;
	}

	public String getImagePath() { return imagePath; }
	public void setImagePath(String imagePath) { this.imagePath = imagePath; }

	public boolean getAvailable() { return isAvailable; }
	public void setAvailable(boolean isAvailable) { this.isAvailable = isAvailable; }

	public Timestamp getCreatedAt() { return created_at; }
	public void setCreatedAt(Timestamp created_at) { this.created_at = created_at; }

	public Timestamp getUpdatedAt() { return updated_at; }
	public void setUpdatedAt(Timestamp updated_at) { this.updated_at = updated_at; }
}