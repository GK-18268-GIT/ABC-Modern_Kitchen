<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Shopping Cart - ABC Modern Kitchen</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <style>
        <%@ include file="/css/cart.css" %>
    </style>
</head>
<body>
    <div class="cart-container">
        <!-- Header -->
        <div class="header">
            <h1><i class="fas fa-shopping-cart"></i> Shopping Cart</h1>
            <p>Review and manage your takeaway order</p>
        </div>
        
        <!-- Navigation -->
        <div class="navigation">
            <a href="<%= request.getContextPath() %>/CustomerServlet?action=takeAway" class="back-btn">
                <i class="fas fa-arrow-left"></i> Continue Shopping
            </a>
            <a href="<%= request.getContextPath() %>/DashboardServlet" class="back-btn">
                <i class="fas fa-home"></i> Dashboard
            </a>
        </div>
        
        <!-- Messages -->
        <c:if test="${not empty error}">
            <div class="alert error">
                <i class="fas fa-exclamation-circle"></i> ${error}
            </div>
        </c:if>
        
        <c:if test="${not empty success}">
            <div class="alert success">
                <i class="fas fa-check-circle"></i> ${success}
            </div>
        </c:if>
        
        <!-- Cart Content -->
        <c:choose>
            <c:when test="${not empty cart && !empty cart}">
                <div class="cart-content">
                    <!-- Cart Items -->
                    <div class="cart-items">
                        <c:forEach var="item" items="${cart}" varStatus="status">
                            <div class="cart-item">
                                <div class="item-image">
                                    <c:choose>
                                        <c:when test="${not empty item.imagePath}">
                                            <img src="<%= request.getContextPath() %>/${item.imagePath}" 
                                                 alt="${item.dishName}" class="dish-image">
                                        </c:when>
                                        <c:otherwise>
                                            <div class="no-image">
                                                <i class="fas fa-utensils fa-2x"></i>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                
                                <div class="item-details">
                                    <h3 class="item-name">${item.dishName}</h3>
                                    <p class="item-size">Size: ${item.size}</p>
                                    <p class="item-code">Code: ${item.dishCode}</p>
                                    <p class="item-price">Rs. <fmt:formatNumber value="${item.price}" pattern="#,##0.00"/></p>
                                </div>
                                
                                <div class="item-controls">
                                    <form action="<%= request.getContextPath() %>/CustomerServlet" method="post" class="quantity-form">
                                        <input type="hidden" name="action" value="updateCart">
                                        <input type="hidden" name="dishId" value="${item.dishId}">
                                        <input type="hidden" name="size" value="${item.size}">
                                        
                                        <div class="quantity-controls">
                                            <button type="button" class="qty-btn minus" onclick="updateQuantity(${item.dishId}, '${item.size}', ${item.quantity - 1})">
                                                <i class="fas fa-minus"></i>
                                            </button>
                                            <span class="quantity">${item.quantity}</span>
                                            <button type="button" class="qty-btn plus" onclick="updateQuantity(${item.dishId}, '${item.size}', ${item.quantity + 1})">
                                                <i class="fas fa-plus"></i>
                                            </button>
                                        </div>
                                    </form>
                                    
                                    <form action="<%= request.getContextPath() %>/CustomerServlet" method="post" class="remove-form">
                                        <input type="hidden" name="action" value="removeFromCart">
                                        <input type="hidden" name="dishId" value="${item.dishId}">
                                        <input type="hidden" name="size" value="${item.size}">
                                        <button type="submit" class="remove-btn">
                                            <i class="fas fa-trash"></i> Remove
                                        </button>
                                    </form>
                                </div>
                                
                                <div class="item-total">
                                    Rs. <fmt:formatNumber value="${item.totalPrice}" pattern="#,##0.00"/>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    
                    <!-- Order Summary -->
                    <div class="order-summary">
                        <h3>Order Summary</h3>
                        
                        <div class="summary-details">
                            <c:set var="subtotal" value="0" />
                            <c:forEach var="item" items="${cart}">
                                <c:set var="subtotal" value="${subtotal + item.totalPrice}" />
                            </c:forEach>
                            
                            <div class="summary-row">
                                <span>Subtotal:</span>
                                <span>Rs. <fmt:formatNumber value="${subtotal}" pattern="#,##0.00"/></span>
                            </div>
                            
                            <div class="summary-row">
                                <span>Tax (5%):</span>
                                <span>Rs. <fmt:formatNumber value="${subtotal * 0.05}" pattern="#,##0.00"/></span>
                            </div>
                            
                            <div class="summary-row total">
                                <span>Total:</span>
                                <span>Rs. <fmt:formatNumber value="${subtotal * 1.05}" pattern="#,##0.00"/></span>
                            </div>
                        </div>
                        
                        <!-- Customer Information Form -->
                        <form action="<%= request.getContextPath() %>/CustomerServlet" method="post" class="order-form">
                            <input type="hidden" name="action" value="confirmTakeawayOrder">
                            
                            <div class="form-group">
                                <label for="phoneNumber">Phone Number *</label>
                                <input type="tel" id="phoneNumber" name="phoneNumber" 
                                       placeholder="+94 771234567" required
                                       pattern="^\+94\s?\d{9}$">
                                <small>Format: +94 followed by 9 digits (e.g., +94771234567)</small>
                            </div>
                            
                            <button type="submit" class="confirm-order-btn">
                                <i class="fas fa-check-circle"></i> Confirm Order
                            </button>
                        </form>
                    </div>
                </div>
            </c:when>
            
            <c:otherwise>
                <div class="empty-cart">
                    <i class="fas fa-shopping-cart fa-4x"></i>
                    <h3>Your cart is empty</h3>
                    <p>Add some delicious dishes from our takeaway menu!</p>
                    <a href="<%= request.getContextPath() %>/CustomerServlet?action=takeAway" class="shop-btn">
                        <i class="fas fa-utensils"></i> Browse Menu
                    </a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <script>
        function updateQuantity(dishId, size, newQuantity) {
            if (newQuantity < 1) return;
            
            const form = document.createElement('form');
            form.method = 'post';
            form.action = '<%= request.getContextPath() %>/CustomerServlet';
            
            const actionInput = document.createElement('input');
            actionInput.type = 'hidden';
            actionInput.name = 'action';
            actionInput.value = 'updateCart';
            form.appendChild(actionInput);
            
            const dishIdInput = document.createElement('input');
            dishIdInput.type = 'hidden';
            dishIdInput.name = 'dishId';
            dishIdInput.value = dishId;
            form.appendChild(dishIdInput);
            
            const sizeInput = document.createElement('input');
            sizeInput.type = 'hidden';
            sizeInput.name = 'size';
            sizeInput.value = size;
            form.appendChild(sizeInput);
            
            const quantityInput = document.createElement('input');
            quantityInput.type = 'hidden';
            quantityInput.name = 'quantity';
            quantityInput.value = newQuantity;
            form.appendChild(quantityInput);
            
            document.body.appendChild(form);
            form.submit();
        }
    </script>
</body>
</html>