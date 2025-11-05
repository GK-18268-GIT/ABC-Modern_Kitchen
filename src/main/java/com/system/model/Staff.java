package com.system.model;

import java.sql.Timestamp;

public class Staff extends User {
	private String name;
	private String profilePicture;
	private String address;
	private String phoneNumber;
	private String staffId;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	
	public Staff(String name, String profilePicture, String email, String password, String address, 
			String phoneNumber, String staffId, Timestamp createdAt, Timestamp updatedAt) {
		super(email, password);
		this.name = name;
		this.profilePicture = profilePicture;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.staffId = staffId;
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
	
	public String getStaffId() {
		return staffId;
	}
	
	public void setStaffId(String staffId) {
		this.staffId = staffId;
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
