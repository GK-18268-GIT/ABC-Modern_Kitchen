<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ABC Modern Kitchen - My Profile</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@400;600;700&family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"/>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/myAdminProfile.css">

</head>
<body>

<div class="myProfile-container">
    <!-- Left Column - Image Section -->
    <div class="img-section">
        <img src="https://img.freepik.com/premium-photo/small-business-owner-working-his-cafe_484651-11545.jpg?semt=ais_hybrid&w=740&q=80" alt="Admin Profile">
        <div class="img-overlay">
            <h1>ABC Modern Kitchen</h1>
            <p>MY PROFILE PORTAL</p>
            <div class="welcome-text">
                <%
                    String adminName = (String) session.getAttribute("adminName");
                    if(adminName != null) {
                        out.print("Welcome " + adminName + "!");
                    }
                %>
            </div>
        </div>
    </div>
    
    <!-- Right Column - Content Section -->
    <div class="content-section">
        <div class="content-header">
            <h1>My Profile</h1>
            <p>Manage your account details and security</p>
        </div>
        
        <!-- Display Messages -->
        <% if (request.getAttribute("success") != null) { %>
            <div class="message success-message">
                <i class="fas fa-check-circle"></i> <%= request.getAttribute("success") %>
            </div>
        <% } %>
        
        <% if (request.getAttribute("error") != null) { %>
            <div class="message error-message">
                <i class="fas fa-exclamation-circle"></i> <%= request.getAttribute("error") %>
            </div>
        <% } %>
        
        <div class="input-fields">
            <form action="<%= request.getContextPath() %>/AdminServlet" class="update-profile-data" method="POST" enctype="multipart/form-data">
                <input type="hidden" name="action" value="updateAdminProfile">
                
                <!-- Profile Picture Preview -->
                <div class="profile-picture-preview">
                    <%
                        String profilePic = (String) request.getAttribute("adminProfilePic");
                        String profilePicPath = profilePic != null && !profilePic.isEmpty() ? 
                            request.getContextPath() + "/" + profilePic : 
                            request.getContextPath() + "/assets/profiles/default-admin.jpg";
                    %>
                    <img id="profilePreview" src="<%= profilePicPath %>" alt="Profile Preview">
                </div>

                <div class="input-group">
                    <label for="name">Full Name</label>
                    <input type="text" id="name" name="name" placeholder="John Doe" required 
                           value="<%= request.getAttribute("adminName") != null ? request.getAttribute("adminName") : "" %>">
                </div>

                <div class="input-group">
                    <label for="profile-picture">Profile Picture</label>
                    <input type="file" id="profile-picture" name="profile-picture" accept="image/*" onchange="previewImage(this)">
                </div>

                <div class="input-group">
                    <label for="email">Email Address</label>
                    <input type="email" id="email" name="email" placeholder="john.doe@example.com" required readonly
                           value="<%= request.getAttribute("adminEmail") != null ? request.getAttribute("adminEmail") : "" %>">
                </div>

                <div class="input-group">
                    <label for="address">Address</label>
                    <input type="text" id="address" name="address" placeholder="No.123, Main Street" required
                           value="<%= request.getAttribute("adminAddress") != null ? request.getAttribute("adminAddress") : "" %>">
                </div>

                <div class="input-group">
                    <label for="phoneNumber">Phone Number</label>
                    <input type="tel" id="phoneNumber" name="phoneNumber" 
                           placeholder="+94 77 123 4567" 
                           pattern="\+94\s[0-9]{9}" 
                           title="Phone number must start with +94 followed by a space and 9 digits (e.g., +94 771234567)"
                           required
                           value="<%= request.getAttribute("adminPhone") != null ? request.getAttribute("adminPhone") : "" %>">
                    <small>Format: +94 followed by a space and 9 digits (e.g., +94 771234567)</small>
                </div>

                <button type="submit" class="btn-submit">
                    <i class="fas fa-save"></i> Update Profile
                </button>
            </form>
            
            <!-- Separate Form for Password Change -->
            <div class="Change-password">
                <h3><i class="fas fa-lock"></i> Change Password</h3>
                <form action="<%= request.getContextPath() %>/AdminServlet" method="POST" id="passwordForm">
                    <input type="hidden" name="action" value="changeMyPassword">
                    
                    <div class="input-group">
                        <label for="current-password-display">Current Password (System Generated)</label>
                        <input type="text" id="current-password-display" name="current-password-display" 
                               value="********" readonly style="background-color: #f8f9fa; color: #6c757d;">
                        <small>This is your system-generated temporary password. You cannot change this field.</small>
                    </div>
                    
                    <div class="input-group">
                        <label for="current-password">Verify Current Password</label>
                        <input type="password" id="current-password" name="current-password" placeholder="Enter your current password to verify" required>
                    </div>
                    
                    <div class="input-group">
                        <label for="new-password">New Password</label>
                        <input type="password" id="new-password" name="new-password" placeholder="Enter your new password" required>
                        <small>Password must be at least 8 characters with uppercase, lowercase, and number</small>
                    </div>

                    <div class="input-group">
                        <label for="confirm-password">Confirm New Password</label>
                        <input type="password" id="confirm-password" name="confirm-password" placeholder="Confirm your new password" required>
                    </div>
                    
                    <button type="submit" class="btn-change-password">
                        <i class="fas fa-key"></i> Change Password
                    </button>
                </form>
            </div>
        </div>
        
        <!-- Back to Dashboard -->
        <div class="back-section">
            <a href="<%= request.getContextPath() %>/DashboardServlet" class="back-btn">
                <i class="fas fa-arrow-left"></i>
                Back to Dashboard
            </a>
        </div>
    </div>
</div>

<script>
    // Image preview functionality
    function previewImage(input) {
        const preview = document.getElementById('profilePreview');
        if (input.files && input.files[0]) {
            const reader = new FileReader();
            reader.onload = function(e) {
                preview.src = e.target.result;
            }
            reader.readAsDataURL(input.files[0]);
        }
    }

    // Phone number formatting with space after +94
    document.getElementById('phoneNumber').addEventListener('input', function(e) {
        let value = e.target.value.replace(/\s/g, ''); // Remove all spaces first
        
        // If it starts with 94, convert to +94
        if (value.startsWith('94')) {
            value = '+' + value;
        }
        // If it doesn't start with +94 but has numbers, ensure +94
        else if (value.length > 0 && !value.startsWith('+94')) {
            value = '+94' + value.replace(/^\+?94?/, '');
        }
        
        // Limit to 12 characters total (+94 + 9 digits = 12)
        if (value.length > 12) {
            value = value.substring(0, 12);
        }
        
        // Add space after +94 if we have more than 3 characters
        if (value.length > 3) {
            value = value.substring(0, 3) + ' ' + value.substring(3);
        }
        
        e.target.value = value;
    });

    // Phone number validation on form submit
    document.querySelector('.update-profile-data').addEventListener('submit', function(e) {
        const phoneInput = document.getElementById('phoneNumber');
        const phonePattern = /^\+94\s[0-9]{9}$/;
        
        // Remove space for validation check
        const phoneValue = phoneInput.value.replace(/\s/g, '');
        const phonePatternNoSpace = /^\+94[0-9]{9}$/;
        
        if (!phonePatternNoSpace.test(phoneValue)) {
            e.preventDefault();
            alert('Please enter a valid Sri Lankan phone number: +94 followed by a space and 9 digits (e.g., +94 771234567)');
            phoneInput.focus();
            return false;
        }
    });

    // Password validation
    document.getElementById('passwordForm').addEventListener('submit', function(e) {
        const currentPassword = document.getElementById('current-password').value;
        const newPassword = document.getElementById('new-password').value;
        const confirmPassword = document.getElementById('confirm-password').value;
        const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/;
        
        if (!currentPassword) {
            e.preventDefault();
            alert('Please enter your current password for verification.');
            return;
        }
        
        if (!passwordPattern.test(newPassword)) {
            e.preventDefault();
            alert('Password must be at least 8 characters long and contain uppercase, lowercase letters and numbers.');
            return;
        }
        
        if (newPassword !== confirmPassword) {
            e.preventDefault();
            alert('New password and confirmation do not match.');
            return;
        }
    });

    // Add interactive effects
    document.querySelectorAll('.input-group input:not([readonly])').forEach(input => {
        input.addEventListener('focus', function() {
            this.parentElement.style.transform = 'translateX(5px)';
        });
        
        input.addEventListener('blur', function() {
            this.parentElement.style.transform = 'translateX(0)';
        });
    });

    // Auto-focus on verification password field
    document.addEventListener('DOMContentLoaded', function() {
        const verifyField = document.getElementById('current-password');
        if (verifyField) {
            verifyField.focus();
        }
        
        // Format existing phone number if needed
        const phoneInput = document.getElementById('phoneNumber');
        if (phoneInput.value) {
            let currentValue = phoneInput.value.replace(/\s/g, '');
            if (currentValue.startsWith('+94') && currentValue.length === 12) {
                // Format as +94 XXXXXXXX (with space)
                phoneInput.value = '+94 ' + currentValue.substring(3);
            } else if (currentValue.startsWith('94') && currentValue.length === 11) {
                // Format as +94 XXXXXXXX (with space)
                phoneInput.value = '+94 ' + currentValue.substring(2);
            } else if (currentValue.length === 9 && !currentValue.startsWith('+94')) {
                // Assume it's just the 9 digits, add +94 and space
                phoneInput.value = '+94 ' + currentValue;
            }
        }
    });
</script>

</body>
</html>