<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ABC Modern Kitchen - Dine In Reservation Form</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/dineInReservation.css">
</head>
<body>

<div class="reservation-container">
    <!-- Image Section -->
    <div class="image-section">
        <img src="https://img.freepik.com/premium-photo/elegant-dining-reservation-setup_53876-1191077.jpg?semt=ais_hybrid&w=740&q=80" alt="ABC Modern Kitchen Restaurant Interior">
        <div class="image-overlay">
            <div class="image-text">
                <h1>Make Your Reservation</h1>
                <p>Experience fine dining at its best. Book your table now and create unforgettable memories with us.</p>
            </div>
        </div>
    </div>
    
    <!-- Form Section -->
    <div class="form-section">
        <div class="form-header">
            <h1>Dine In Reservation</h1>
            <p>Fill in your details to reserve your table</p>
        </div>
        
        <form action="CustomerServlet" method="POST" class="reservation-form" onsubmit="return validateCredentials()">
            <input type="hidden" name="action" value="dineIn">
            
            <!-- Combined Form Fields -->
            <div class="form-grid">
                <!-- Personal Information -->
                <div class="input-group">
                    <label for="name">Full Name *</label>
                    <input type="text" id="customerName" name="customerName" value = "${customerName}" placeholder="John Doe" readonly>
                </div>
                
                <div class="input-group">
                    <label for="phone-number">Phone Number *</label>
                    <div class="phone-input-container">
                        <span class="phone-prefix">+94 </span>
                        <input type="tel" id="phone-number" name="phone-number" value = "${phoneNumber}"
                               placeholder="123456789" 
                               pattern="[0-9]{9}" 
                               maxlength="9"
                               title="Please enter exactly 9 digits (123456789)" required>
                    </div>
                </div>
                
                <div class="input-group">
                    <label for="email">Email Address *</label>
                    <input type="email" id="email" name="email" value = "${email}" placeholder="john.doe@example.com" readonly>
                </div>
                
                <!-- Reservation Details -->
                <div class="input-group">
                    <label for="date">Reservation Date & Time *</label>
                    <input type="datetime-local" id="reservationDateTime" name="reservationDateTime" required>
                </div>
                
                <div class="input-group">
                    <label for="guest">Number of Guests *</label>
                    <input type="number" id="numberOfGuests" name="numberOfGuests" placeholder="e.g., 2" min="1" max="20" required>
                </div>
                
                <div class="input-group">
                    <label for="seatPreference">Seating Preference *</label>
                    <select id="seatingPreference" name="seatingPreference" required>
                        <option value="">Select preference</option>
                        <option value="inDoor">Indoor Seating</option>
                        <option value="outDoor">Outdoor Seating</option>
                    </select>
                </div>
                
                <!-- Additional Information -->
                <div class="input-group full-width">
                    <label for="specialOccasion">Special Occasion</label>
                    <input type="text" id="specialOccasion" name="specialOccasion" placeholder="e.g., Birthday, Anniversary, Date Night">
                </div>
                
                <div class="input-group full-width">
                    <label for="allergies">Dietary Restrictions & Allergies</label>
                    <input type="text" id="dietaryRestrictions" name="dietaryRestrictions" placeholder="e.g., Nut allergy, Vegetarian, Gluten-free">
                </div>
            </div>
            
            <button type="submit" class="btn-submit">
                <i class="fas fa-calendar-check"></i> Reserve Table
            </button>
            
            <!-- Messages -->
            <c:if test="${not empty success}">
                <div class="alert alert-success">
                    <i class="fas fa-check-circle"></i> ${success}
                </div>
            </c:if>
            
            <c:if test="${not empty error}">
                <div class="alert alert-error">
                    <i class="fas fa-exclamation-circle"></i> ${error}
                </div>
            </c:if>
            
            <div class="back-section">
                <a href="<%= request.getContextPath() %>/DashboardServlet" class="back-btn">
                    <i class="fas fa-arrow-left"></i>
                    Back to Dashboard
                </a>
            </div>
        </form>
    </div>
</div>

<script>
    function validateCredentials() {
        var phoneNumber = document.getElementById("phone-number").value;
        var phonePattern = /^[0-9]{9}$/;
        
        if (!phonePattern.test(phoneNumber)) {
            alert("Invalid phone number. Please enter exactly 9 digits.");
            return false;
        }
        
        // Validate future date
        var selectedDate = new Date(document.getElementById("date").value);
        var currentDate = new Date();
        
        if (selectedDate <= currentDate) {
            alert("Please select a future date and time for your reservation.");
            return false;
        }
        
        // Validate number of guests
        var numberOfGuests = document.getElementById("numberOfGuests").value;
        if (numberOfGuests < 1 || guests > 20) {
            alert("Number of guests must be between 1 and 20.");
            return false;
        }
        
        return true;
    }
    
    // Phone number input restriction - only numbers
    document.getElementById('phone-number').addEventListener('input', function(e) {
        this.value = this.value.replace(/[^0-9]/g, '');
    });
    
    // Set minimum datetime to current time
    document.addEventListener('DOMContentLoaded', function() {
        var now = new Date();
        // Format to YYYY-MM-DDTHH:MM
        var year = now.getFullYear();
        var month = String(now.getMonth() + 1).padStart(2, '0');
        var day = String(now.getDate()).padStart(2, '0');
        var hours = String(now.getHours()).padStart(2, '0');
        var minutes = String(now.getMinutes()).padStart(2, '0');
        
        var minDateTime = year + '-' + month + '-' + day + 'T' + hours + ':' + minutes;
        document.getElementById('date').min = minDateTime;
        
        // Set default number of guests to 2
        document.getElementById('numberOfGuests').value = 2;
    });
</script>

</body>
</html>