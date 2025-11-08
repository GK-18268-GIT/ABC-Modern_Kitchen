<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ABC Modern Kitchen - Admin Registration</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@400;600;700&family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/registerAdmin.css">

</head>

<body>
    <div class="register-container">
        <div class="image-section">
            <img src="https://img.freepik.com/free-photo/waiter-restaurant_1098-14384.jpg?semt=ais_hybrid&w=740&q=80" alt="Admin Registration">
            <div class="image-overlay">
                <div class="image-text">
                    <h1>Administrative Access</h1>
                    <p>Manage ABC Modern Kitchen operations</p>
                </div>
            </div>
        </div>
        
        <div class="form-section">
            <div class="form-header">
                <h1>Admin Registration</h1>
                <p>Create your admin account</p>
            </div>

            <% if (session.getAttribute("errorMessage") != null) { %>
                <div class="error-message">
                    <%= session.getAttribute("errorMessage") %>
                </div>
                <% session.removeAttribute("errorMessage"); %>
            <% } %>

            <form action="RegisterServlet" method="POST" class="register-form" enctype="multipart/form-data">
                <input type="hidden" name="action" value="adminRegister">

                <div class="input-group">
                    <label for="name">Full Name</label>
                    <input type="text" id="name" name="name" placeholder="John Doe" required>
                </div>

                <div class="input-group">
                    <label for="profile-picture">Profile Picture</label>
                    <input type="file" id="profile-picture" name="profile-picture">
                </div>

                <div class="input-group">
                    <label for="email">Email Address</label>
                    <input type="email" id="email" name="email" placeholder="john.doe@example.com" required>
                </div>

                <div class="input-group">
                    <label for="address">Address</label>
                    <input type="text" id="address" name="address" placeholder="No.123, Main Street" required>
                </div>

                <div class="input-group">
                    <label for="phone-number">Phone Number</label>
                    <div class="phone-input-container">
                        <span class="phone-prefix">+94 </span>
                        <input type="tel" id="phone-number" name="phone-number" 
                               placeholder="123456789" 
                               pattern="[0-9]{9}" 
                               maxlength="9"
                               title="Please enter exactly 9 digits (123456789)" required>
                    </div>
                    <div class="phone-hint">Enter 9 digits without the country code</div>
                </div>

                <button type="submit" class="btn-submit">Register</button>

                <c:if test="${not empty success}">
                    <div class="success-message">${success}</div>
                </c:if>

                <p class="login-link">Already have an account? <a href="LoginServlet?action=login">Sign In</a></p>
            </form>
        </div>
    </div>

    <script>
        function validateCredentials() {
            var email = document.getElementById("email").value;
            var emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            
            var phoneNumber = document.getElementById("phone-number").value;
            var phonePattern = /^[0-9]{9}$/;

            if (!emailPattern.test(email)) {
                alert("Invalid email format");
                return false;
            }

            if (!phonePattern.test(phoneNumber)) {
                alert("Invalid phone number. Please enter exactly 9 digits.");
                return false;
            }

            return true;
        }

        // Phone number input restriction - only numbers
        document.getElementById('phone-number').addEventListener('input', function(e) {
            this.value = this.value.replace(/[^0-9]/g, '');
        });

        // Attach validation to form submit
        document.querySelector('.register-form').addEventListener('submit', function(e) {
            if (!validateCredentials()) {
                e.preventDefault();
            }
        });
    </script>
</body>
</html>