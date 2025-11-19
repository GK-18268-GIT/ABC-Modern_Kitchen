package com.system.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DineInReservation {
    private int id;
    private String reservationCode;
    private String customerName;
    private String phoneNumber;
    private String email;
    private LocalDateTime reservationDateTime;
    private int numberOfGuests;
    private String dietaryRestrictions;
    private String specialOccasion;
    private String seatingPreference;
    private String status;
    private String assignedStaffId;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public DineInReservation() {

    }

    public DineInReservation(String customerName, String phoneNumber, String email, LocalDateTime reservationDateTime,
            int numberOfGuests, String dietaryRestrictions, String specialOccasion, String seatingPreference) {
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.reservationDateTime = reservationDateTime;
        this.numberOfGuests = numberOfGuests;
        this.dietaryRestrictions = dietaryRestrictions;
        this.specialOccasion = specialOccasion;
        this.seatingPreference = seatingPreference;
        this.status = "Pending";
    }

    public DineInReservation(int id, String reservationCode, String customerName, String phoneNumber, String email,
            LocalDateTime reservationDateTime, int numberOfGuests, String dietaryRestrictions, String specialOccasion, String seatingPreference,
            String status, String assignedStaffId, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.reservationCode = reservationCode;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.reservationDateTime = reservationDateTime;
        this.numberOfGuests = numberOfGuests;
        this.dietaryRestrictions = dietaryRestrictions;
        this.specialOccasion = specialOccasion;
        this.seatingPreference = seatingPreference;
        this.status = status;
        this.assignedStaffId = assignedStaffId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReservationCode() {
        return reservationCode;
    }

    public void setReservationCode(String reservationCode) {
        this.reservationCode = reservationCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getReservationDateTime() {
        return reservationDateTime;
    }

    public void setReservationDateTime(LocalDateTime reservationDateTime) {
        this.reservationDateTime = reservationDateTime;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public String getDietaryRestrictions() {
        return dietaryRestrictions;
    }

    public void setDietaryRestrictions(String dietaryRestrictions) {
        this.dietaryRestrictions = dietaryRestrictions;
    }

    public String getSpecialOccasion() {
        return specialOccasion;
    }

    public void setSpecialOccasion(String specialOccasion) {
        this.specialOccasion = specialOccasion;
    }

    public String getSeatingPreference() {
        return seatingPreference;
    }

    public void setSeatingPreference(String seatingPreference) {
        this.seatingPreference = seatingPreference;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getAssignedStaffId() {
    	return assignedStaffId;
    }
    
    public void setAssignedStaffId(String assignedStaffId) {
    	this.assignedStaffId = assignedStaffId;
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

    public String getDisplaySeatingPreference() {
        if ("inDoor".equals(seatingPreference)) {
            return "Indoor";
        } else if ("outDoor".equals(seatingPreference)) {
            return "Outdoor";
        }
        return seatingPreference;
    }

    public boolean isUpcoming() {
        return reservationDateTime != null &&
                reservationDateTime.isAfter(LocalDateTime.now()) &&
                !"Cancelled".equals(status);
    }

    public String getFormattedReservationDateTime() {
        if (reservationDateTime == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, MMM d, yyyy 'at' h:mm a");
        return reservationDateTime.format(formatter);
    }

}
