<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <!DOCTYPE html>
 <html lang="en">

 <head>
     <meta charset="UTF-8">
     <meta name="viewport" content="width=device-width, initial-scale=1.0">
     <title>Order Confirmation - ABC Modern Kitchen</title>
     <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
     <style>
         * {
             margin: 0;
             padding: 0;
             box-sizing: border-box;
         }

         body {
             font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
             background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
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

         .order-code {
             background: #f8f9fa;
             padding: 20px;
             border-radius: 10px;
             margin: 20px 0;
             border: 2px dashed #667eea;
         }

         .order-code-label {
             color: #7f8c8d;
             font-size: 0.9rem;
             margin-bottom: 5px;
         }

         .order-code-value {
             color: #667eea;
             font-size: 1.8rem;
             font-weight: bold;
             letter-spacing: 2px;
         }

         .message {
             color: #7f8c8d;
             font-size: 1.1rem;
             line-height: 1.6;
             margin: 20px 0;
         }

         .actions {
             margin-top: 30px;
             display: flex;
             gap: 15px;
             justify-content: center;
             flex-wrap: wrap;
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
         }

         .btn-primary {
             background: #667eea;
             color: white;
         }

         .btn-primary:hover {
             background: #5568d3;
             transform: translateY(-2px);
             box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
         }

         .btn-secondary {
             background: #ecf0f1;
             color: #2c3e50;
         }

         .btn-secondary:hover {
             background: #d5dbdb;
         }

         .info-box {
             background: #e8f4fd;
             border-left: 4px solid #3498db;
             padding: 15px;
             border-radius: 5px;
             margin: 20px 0;
             text-align: left;
         }

         .info-box p {
             margin: 5px 0;
             color: #2c3e50;
         }
     </style>
 </head>

 <body>
     <div class="confirmation-container">
         <div class="success-icon">
             <i class="fas fa-check"></i>
         </div>

         <h1>Order Placed Successfully!</h1>

         <c:if test="${not empty orderCode}">
             <div class="order-code">
                 <div class="order-code-label">Your Order Code</div>
                 <div class="order-code-value">${orderCode}</div>
             </div>
         </c:if>

         <div class="message">
             <p>Thank you for your order! Your takeaway order has been placed successfully.</p>
             <p>A staff member has been assigned to prepare your order. You will receive an email
                 notification once your order is ready for pickup.</p>
         </div>

         <div class="info-box">
             <p><strong><i class="fas fa-info-circle"></i> What's Next?</strong></p>
             <p>• A staff member will prepare your order</p>
             <p>• You'll receive an email when your order is ready</p>
             <p>• Please bring your order code when picking up</p>
         </div>

         <div class="actions">
             <a href="<%= request.getContextPath() %>/CustomerServlet?action=takeAway"
                 class="btn btn-primary">
                 <i class="fas fa-utensils"></i> Order More
             </a>
             <a href="<%= request.getContextPath() %>/DashboardServlet" class="btn btn-secondary">
                 <i class="fas fa-home"></i> Back to Dashboard
             </a>
         </div>
     </div>
 </body>

 </html>