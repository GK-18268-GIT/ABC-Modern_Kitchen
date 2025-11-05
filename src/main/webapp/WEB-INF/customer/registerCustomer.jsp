<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ABC Modern Kitchen - Customer Registration</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@400;600;700&family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/registerCustomer.css">

</head>

<body>
    <div class="register-container">
        <div class="image-section">
            <img src="https://tableo.com/wp-content/uploads/Restaurant-Food-Stock-Imagery-1.webp" alt="Customer Registration">
            <div class="image-overlay">
                <div class="image-text">
                    <h1>Welcome Food Lover</h1>
                    <p>Join ABC Modern Kitchen's family</p>
                </div>
            </div>
        </div>
        
        <div class="form-section">
            <div class="form-header">
                <h1>Customer Registration</h1>
                <p>Create your customer account</p>
            </div>

            <% if (session.getAttribute("errorMessage") != null) { %>
                <div class="error-message">
                    <%= session.getAttribute("errorMessage") %>
                </div>
                <% session.removeAttribute("errorMessage"); %>
            <% } %>

            <form action="<%= request.getContextPath() %>/RegisterServlet" method="POST" class="register-form" enctype="multipart/form-data">
                <input type="hidden" name="action" value="customerRegister">

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
                    <input type="tel" id="phone-number" name="phone-number" placeholder="+94 123456789" required>
                </div>

                <div class="input-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" placeholder="Choose your password" required>
                    <div class="password-requirements">Must contain 8+ characters with uppercase, lowercase, and number</div>
                </div>

                <div class="input-group">
                    <label for="confirmpassword">Confirm Password</label>
                    <input type="password" id="confirmpassword" name="confirmpassword" placeholder="Confirm your password" required>
                </div>

                <button type="submit" class="btn-submit">Register</button>

                <c:if test="${not empty success}">
                    <div class="success-message">${success}</div>
                </c:if>

                <p class="login-link">Already have an account? <a href="RegisterServlet?action=login">Sign In</a></p>
            </form>
        </div>
    </div>

    <script>
        function validateCredentials() {
            var password = document.getElementById("password").value;
            var confirmPassword = document.getElementById("confirmpassword").value;
            var passwordPattern = /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$/;

            var email = document.getElementById("email").value;
            var emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

            if (!passwordPattern.test(password)) {
                alert("Password must contain at least 8 characters, including a number, lowercase and uppercase letter!");
                return false;
            }

            if (password !== confirmPassword) {
                alert("Passwords do not match!");
                return false;
            }

            if (!emailPattern.test(email)) {
                alert("Invalid email format");
                return false;
            }

            return true;
        }

        // Attach validation to form submit
        document.querySelector('.register-form').addEventListener('submit', function(e) {
            if (!validateCredentials()) {
                e.preventDefault();
            }
        });
    </script>
</body>
</html>
