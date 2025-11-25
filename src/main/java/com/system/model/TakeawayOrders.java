package com.system.model;

import java.time.LocalDateTime;
import java.util.List;

public class TakeawayOrders {
    private int orderId;
    private String orderCode;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String status;
    private double totalAmount;
    private LocalDateTime orderDate;
    private LocalDateTime readyTime;
    private String assignedStaffId;
    private List<OrderItems> items;

    public TakeawayOrders() {}

    public TakeawayOrders(String customerName, String customerEmail, String customerPhone, 
                        double totalAmount, String assignedStaffId) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
        this.totalAmount = totalAmount;
        this.assignedStaffId = assignedStaffId;
        this.status = "Pending";
        this.orderDate = LocalDateTime.now();
    }

    // Getters and Setters
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public String getOrderCode() { return orderCode; }
    public void setOrderCode(String orderCode) { this.orderCode = orderCode; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }

    public String getCustomerPhone() { return customerPhone; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public LocalDateTime getReadyTime() { return readyTime; }
    public void setReadyTime(LocalDateTime readyTime) { this.readyTime = readyTime; }

    public String getAssignedStaffId() { return assignedStaffId; }
    public void setAssignedStaffId(String assignedStaffId) { this.assignedStaffId = assignedStaffId; }

    public List<OrderItems> getItems() { return items; }
    public void setItems(List<OrderItems> items) { this.items = items; }
}