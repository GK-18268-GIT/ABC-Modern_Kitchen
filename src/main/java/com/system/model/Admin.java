package com.system.model;

import java.sql.Timestamp;

public class Admin extends User {
	private String name;
	private String profilePicture;
	private String address;
	private String phoneNumber;
	private String adminId;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	
	  public Admin() {
	        super();
	    }
	
	
	public Admin(String name, String profilePicture, String email, String password, String address, 
			String phoneNumber, String adminId, Timestamp createdAt, Timestamp updatedAt) {
		super(email, password);
		this.name = name;
		this.profilePicture = profilePicture;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.adminId = adminId;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getProfilePicture() {
		return profilePicture;
	}
	
	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getAdminId() {
		return adminId;
	}
	
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	
	public Timestamp getUpdatedAt() {
		return updatedAt;
	}
	
	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

}
