<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ABC Modern Kitchen - Login</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@400;600;700&family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/login.css">

</head>

<body>
    <div class="login-container">
        <div class="image-section">
            <img src="https://media.istockphoto.com/id/1003743868/photo/business-owner-at-the-door-of-a-cafe-welcoming-customers.jpg?s=612x612&w=0&k=20&c=AzL2MViddyXm7hs4WOY8z_AdLjxGbhiVLu-U8bPSobE=" alt="Login">
            <div class="image-overlay">
                <div class="image-text">
                    <h1>Welcome Back</h1>
                    <p>Sign in to your ABC Modern Kitchen account</p>
                </div>
            </div>
        </div>
        
        <div class="form-section">
            <div class="form-header">
                <h1>Login</h1>
                <p>Access your account</p>
            </div>

            <% if (session.getAttribute("errorMessage") != null) { %>
                <div class="error-message">
                    <%= session.getAttribute("errorMessage") %>
                </div>
                <% session.removeAttribute("errorMessage"); %>
            <% } %>

            <form action="${pageContext.request.contextPath}/LoginServlet" method="POST" class="login-form">
                <input type="hidden" name="action" value="loginUser">
                
                <div class="input-group">
                    <label for="email">Email Address</label>
                    <input type="email" id="email" name="email" placeholder="john.doe@example.com" required>
                </div>
                
                <div class="input-group">
                   <label for="password">Password</label>
                   <input type="password" id="password" name="password" placeholder="Enter your password" required>
               </div>
               
               <button type="submit" class="btn-submit">Login</button>
               
                <c:if test="${not empty success}">
                    <div class="success-message">${success}</div>
                </c:if>
               
               <div class="form-links">
				    <p class="forgot-password">
				        <a href="<%= request.getContextPath() %>/LoginServlet?action=showForgotPasswordForm">Forgot Password?</a>
				    </p>
				    <p class="register-link">Don't have an account? 
				        <a href="<%= request.getContextPath() %>/RegisterServlet?action=customerRegister">Sign Up</a> 
				    </p>
			   </div>
            </form>
        </div>
    </div>
</body>
</html>