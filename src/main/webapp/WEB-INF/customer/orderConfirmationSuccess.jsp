<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Confirmed - ABC Modern Kitchen</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #27ae60 0%, #2ecc71 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 20px;
        }
        
        .confirmation-container {
            background: white;
            border-radius: 20px;
            box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
            max-width: 600px;
            width: 100%;
            padding: 40px;
            text-align: center;
        }
        
        .success-icon {
            width: 100px;
            height: 100px;
            background: #27ae60;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0 auto 30px;
            animation: scaleIn 0.5s ease-out;
        }
        
        @keyframes scaleIn {
            from {
                transform: scale(0);
            }
            to {
                transform: scale(1);
            }
        }
        
        .success-icon i {
            font-size: 50px;
            color: white;
        }
        
        h1 {
            color: #2c3e50;
            margin-bottom: 15px;
            font-size: 2rem;
        }
        
        .message {
            color: #7f8c8d;
            font-size: 1.1rem;
            line-height: 1.6;
            margin: 20px 0;
        }
        
        .order-code {
            background: #f8f9fa;
            padding: 20px;
            border-radius: 10px;
            margin: 20px 0;
            border: 2px dashed #27ae60;
        }
        
        .order-code-value {
            color: #27ae60;
            font-size: 1.8rem;
            font-weight: bold;
            letter-spacing: 2px;
        }
        
        .btn {
            padding: 12px 30px;
            border: none;
            border-radius: 8px;
            font-size: 1rem;
            font-weight: 600;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            transition: all 0.3s ease;
            background: #27ae60;
            color: white;
            margin-top: 20px;
        }
        
        .btn:hover {
            background: #229954;
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(39, 174, 96, 0.4);
        }
        
        .error-message {
            background: #fee;
            color: #c33;
            padding: 15px;
            border-radius: 5px;
            margin: 20px 0;
        }
    </style>
</head>
<body>
    <div class="confirmation-container">
        <c:choose>
            <c:when test="${not empty success}">
                <div class="success-icon">
                    <i class="fas fa-check-circle"></i>
                </div>
                <h1>Order Confirmed!</h1>
                <div class="message">
                    <p>${success}</p>
                </div>
                <c:if test="${not empty orderCode}">
                    <div class="order-code">
                        <div class="order-code-value">${orderCode}</div>
                    </div>
                </c:if>
                <div class="message">
                    <p>The customer has been notified that their order is ready for pickup.</p>
                </div>
            </c:when>
            <c:when test="${not empty message}">
                <div class="success-icon" style="background: #f39c12;">
                    <i class="fas fa-info-circle"></i>
                </div>
                <h1>Order Status</h1>
                <div class="message">
                    <p>${message}</p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="error-message">
                    <p>An error occurred. Please try again.</p>
                </div>
            </c:otherwise>
        </c:choose>
        
        <a href="<%= request.getContextPath() %>/DashboardServlet" class="btn">
            <i class="fas fa-home"></i> Back to Dashboard
        </a>
    </div>
</body>
</html>


