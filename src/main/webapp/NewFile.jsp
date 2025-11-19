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
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Poppins', sans-serif;
        }

        body {
            background: #ffffff;
            color: #2c3e50;
            line-height: 1.6;
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 20px;
        }

        .myProfile-container {
            display: flex;
            width: 100%;
            max-width: 1400px;
            background: white;
            border-radius: 20px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
            overflow: hidden;
            min-height: 800px;
        }

        /* Image Section */
        .img-section {
            flex: 1;
            position: relative;
            overflow: hidden;
            display: flex;
            align-items: center;
            justify-content: center;
            background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
        }

        .img-section img {
            width: 100%;
            height: 100%;
            object-fit: cover;
            opacity: 0.9;
        }

        .img-overlay {
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background: linear-gradient(135deg, rgba(44, 62, 80, 0.85) 0%, rgba(52, 73, 94, 0.85) 100%);
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            color: white;
            text-align: center;
            padding: 40px;
        }

        .img-overlay h1 {
            font-size: 2.5rem;
            margin-bottom: 15px;
            font-weight: 700;
        }

        .img-overlay p {
            font-size: 1.2rem;
            opacity: 0.9;
            margin-bottom: 10px;
        }

        .welcome-text {
            font-size: 1.4rem;
            margin-top: 20px;
            font-weight: 600;
            color: #3498db;
        }

        /* Content Section */
        .content-section {
            flex: 1.5;
            padding: 40px;
            display: flex;
            flex-direction: column;
            background: white;
            overflow-y: auto;
            max-height: 800px;
        }

        .content-header {
            margin-bottom: 30px;
            text-align: center;
        }

        .content-header h1 {
            font-size: 2.2rem;
            color: #2c3e50;
            margin-bottom: 10px;
            font-weight: 600;
        }

        .content-header p {
            color: #7f8c8d;
            font-size: 1.1rem;
        }

        /* Form Styles */
        .update-profile-data {
            display: flex;
            flex-direction: column;
            gap: 20px;
        }

        .input-group {
            display: flex;
            flex-direction: column;
            margin-bottom: 15px;
        }

        .input-group label {
            font-weight: 600;
            margin-bottom: 8px;
            color: #2c3e50;
            font-size: 0.95rem;
        }

        .input-group input {
            padding: 12px 15px;
            border: 2px solid #e9ecef;
            border-radius: 8px;
            font-size: 1rem;
            transition: all 0.3s ease;
            background: #f8f9fa;
        }

        .input-group input:focus {
            outline: none;
            border-color: #3498db;
            background: white;
            box-shadow: 0 0 0 3px rgba(52, 152, 219, 0.1);
        }

        .input-group input[readonly] {
            background: #e9ecef;
            color: #6c757d;
            cursor: not-allowed;
        }

        .input-group input[type="file"] {
            padding: 10px;
            background: white;
        }

        /* Change Password Section */
        .Change-password {
            background: #f8f9fa;
            padding: 25px;
            border-radius: 12px;
            border-left: 4px solid #3498db;
            margin: 20px 0;
        }

        .Change-password h3 {
            color: #2c3e50;
            margin-bottom: 20px;
            font-size: 1.3rem;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .Change-password h3 i {
            color: #3498db;
        }

        /* Buttons */
        .btn-submit, .btn-change-password {
            padding: 14px 25px;
            border: none;
            border-radius: 8px;
            font-size: 1rem;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        .btn-submit {
            background: #2ecc71;
            color: white;
            margin-top: 10px;
        }

        .btn-submit:hover {
            background: #27ae60;
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(46, 204, 113, 0.3);
        }

        .btn-change-password {
            background: #3498db;
            color: white;
            width: 100%;
            margin-top: 15px;
        }

        .btn-change-password:hover {
            background: #2980b9;
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(52, 152, 219, 0.3);
        }

        /* Back Button */
        .back-section {
            margin-top: 30px;
            text-align: center;
        }

        .back-btn {
            display: inline-flex;
            align-items: center;
            gap: 10px;
            padding: 12px 25px;
            background: #95a5a6;
            color: white;
            text-decoration: none;
            border-radius: 8px;
            transition: all 0.3s ease;
            font-weight: 500;
        }

        .back-btn:hover {
            background: #7f8c8d;
            transform: translateY(-2px);
        }

        /* Profile Picture Preview */
        .profile-picture-preview {
            text-align: center;
            margin-bottom: 20px;
        }

        .profile-picture-preview img {
            width: 120px;
            height: 120px;
            border-radius: 50%;
            object-fit: cover;
            border: 4px solid #3498db;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
        }

        /* Responsive Design */
        @media (max-width: 968px) {
            .myProfile-container {
                flex-direction: column;
                max-width: 600px;
            }
            
            .img-section {
                min-height: 300px;
            }
            
            .content-section {
                padding: 30px;
            }
        }

        @media (max-width: 480px) {
            body {
                padding: 10px;
            }
            
            .content-section {
                padding: 20px;
            }
            
            .Change-password {
                padding: 20px;
            }
            
            .content-header h1 {
                font-size: 1.8rem;
            }
            
            .img-overlay h1 {
                font-size: 2rem;
            }
        }

        /* Success/Error Messages */
        .message {
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 20px;
            font-weight: 500;
        }

        .success-message {
            background: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }

        .error-message {
            background: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
    </style>
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
                    <input type="tel" id="phoneNumber" name="phoneNumber" placeholder="+94 123456789" required
                           value="<%= request.getAttribute("adminPhone") != null ? request.getAttribute("adminPhone") : "" %>">
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
                        <label for="current-password">Current Password</label>
                        <input type="password" id="current-password" name="current-password" placeholder="Enter your current password" required>
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

    // Password validation
    document.getElementById('passwordForm').addEventListener('submit', function(e) {
        const newPassword = document.getElementById('new-password').value;
        const confirmPassword = document.getElementById('confirm-password').value;
        const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/;
        
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
    document.querySelectorAll('.input-group input').forEach(input => {
        input.addEventListener('focus', function() {
            this.parentElement.style.transform = 'translateX(5px)';
        });
        
        input.addEventListener('blur', function() {
            this.parentElement.style.transform = 'translateX(0)';
        });
    });
</script>

</body>
</html>