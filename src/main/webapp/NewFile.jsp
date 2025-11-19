<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ABC Modern Kitchen - Forgot Password</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@400;600;700&family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/login.css">
</head>

<body>
    <div class="login-container">
        <div class="image-section">
            <img src="https://media.istockphoto.com/id/1003743868/photo/business-owner-at-the-door-of-a-cafe-welcoming-customers.jpg?s=612x612&w=0&k=20&c=AzL2MViddyXm7hs4WOY8z_AdLjxGbhiVLu-U8bPSobE=" alt="Forgot Password">
            <div class="image-overlay">
                <div class="image-text">
                    <h1>Reset Your Password</h1>
                    <p>Enter your email to receive a reset link</p>
                </div>
            </div>
        </div>
        
        <div class="form-section">
            <div class="form-header">
                <h1>Forgot Password</h1>
                <p>We'll send you a link to reset your password</p>
            </div>

            <c:if test="${not empty success}">
                <div class="success-message">
                    <i class="fas fa-check-circle"></i> ${success}
                </div>
            </c:if>

            <c:if test="${not empty error}">
                <div class="error-message">
                    <i class="fas fa-exclamation-circle"></i> ${error}
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/LoginServlet" method="POST" class="login-form">
                <input type="hidden" name="action" value="requestPasswordReset">
                
                <div class="input-group">
                    <label for="email">Email Address</label>
                    <input type="email" id="email" name="email" placeholder="john.doe@example.com" required>
                </div>
               
               <button type="submit" class="btn-submit">Send Reset Link</button>
               
               <div class="form-links">
                   <p>Remember your password? 
                     <a href="${pageContext.request.contextPath}/LoginServlet">Back to Login</a>
                   </p>
               </div>
            </form>
        </div>
    </div>
    
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/js/all.min.js"></script>
</body>
</html>