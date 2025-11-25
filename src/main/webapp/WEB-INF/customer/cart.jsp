<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
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
 /* cart.css */
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

body {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    min-height: 100vh;
    padding: 20px;
}

.cart-container {
    max-width: 1200px;
    margin: 0 auto;
    background: white;
    border-radius: 15px;
    box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
    overflow: hidden;
}

/* Header Styles */
.header {
    background: linear-gradient(135deg, #2c3e50 0%, #34495e 100%);
    color: white;
    padding: 30px;
    text-align: center;
}

.header h1 {
    font-size: 2.5rem;
    margin-bottom: 10px;
    font-weight: 600;
}

.header p {
    font-size: 1.1rem;
    opacity: 0.9;
}

.header i {
    margin-right: 15px;
    color: #3498db;
}

/* Navigation Styles */
.navigation {
    display: flex;
    gap: 15px;
    padding: 20px 30px;
    background: #f8f9fa;
    border-bottom: 1px solid #e9ecef;
}

.back-btn {
    display: inline-flex;
    align-items: center;
    gap: 8px;
    padding: 10px 20px;
    background: #6c757d;
    color: white;
    text-decoration: none;
    border-radius: 8px;
    font-weight: 500;
    transition: all 0.3s ease;
}

.back-btn:hover {
    background: #5a6268;
    transform: translateY(-2px);
    box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
}

/* Alert Styles */
.alert {
    padding: 15px 20px;
    margin: 20px 30px;
    border-radius: 8px;
    font-weight: 500;
    display: flex;
    align-items: center;
    gap: 10px;
}

.alert.error {
    background: #f8d7da;
    color: #721c24;
    border: 1px solid #f5c6cb;
}

.alert.success {
    background: #d1edff;
    color: #155724;
    border: 1px solid #c3e6cb;
}

/* Cart Content */
.cart-content {
    display: grid;
    grid-template-columns: 1fr 400px;
    gap: 30px;
    padding: 30px;
}

/* Cart Items */
.cart-items {
    display: flex;
    flex-direction: column;
    gap: 20px;
}

.cart-item {
    display: grid;
    grid-template-columns: 120px 1fr auto auto;
    gap: 20px;
    padding: 20px;
    background: #f8f9fa;
    border-radius: 12px;
    border: 1px solid #e9ecef;
    transition: all 0.3s ease;
}

.cart-item:hover {
    transform: translateY(-2px);
    box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
}

.item-image {
    width: 120px;
    height: 120px;
    border-radius: 10px;
    overflow: hidden;
    background: #e9ecef;
    display: flex;
    align-items: center;
    justify-content: center;
}

.item-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.no-image {
    color: #6c757d;
    text-align: center;
}

.item-details h3 {
    font-size: 1.3rem;
    color: #2c3e50;
    margin-bottom: 8px;
    font-weight: 600;
}

.item-details p {
    color: #6c757d;
    margin-bottom: 5px;
    font-size: 0.95rem;
}

.item-price {
    font-weight: 600;
    color: #27ae60 !important;
    font-size: 1.1rem !important;
}

/* Item Controls */
.item-controls {
    display: flex;
    flex-direction: column;
    gap: 15px;
    align-items: center;
}

.quantity-controls {
    display: flex;
    align-items: center;
    gap: 12px;
    background: white;
    padding: 8px;
    border-radius: 8px;
    border: 1px solid #dee2e6;
}

.qty-btn {
    width: 35px;
    height: 35px;
    border: none;
    background: #3498db;
    color: white;
    border-radius: 6px;
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: center;
    transition: all 0.3s ease;
}

.qty-btn:hover {
    background: #2980b9;
    transform: scale(1.1);
}

.quantity {
    font-weight: 600;
    font-size: 1.1rem;
    min-width: 30px;
    text-align: center;
}

.remove-btn {
    padding: 8px 16px;
    background: #e74c3c;
    color: white;
    border: none;
    border-radius: 6px;
    cursor: pointer;
    font-size: 0.9rem;
    display: flex;
    align-items: center;
    gap: 5px;
    transition: all 0.3s ease;
}

.remove-btn:hover {
    background: #c0392b;
    transform: translateY(-1px);
}

.item-total {
    font-size: 1.3rem;
    font-weight: 700;
    color: #2c3e50;
    display: flex;
    align-items: center;
}

/* Order Summary */
.order-summary {
    background: #f8f9fa;
    padding: 25px;
    border-radius: 12px;
    border: 1px solid #e9ecef;
    height: fit-content;
    position: sticky;
    top: 20px;
}

.order-summary h3 {
    font-size: 1.5rem;
    color: #2c3e50;
    margin-bottom: 20px;
    text-align: center;
    font-weight: 600;
}

.summary-details {
    background: white;
    padding: 20px;
    border-radius: 8px;
    margin-bottom: 25px;
    border: 1px solid #dee2e6;
}

.summary-row {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px 0;
    border-bottom: 1px solid #f1f3f4;
}

.summary-row:last-child {
    border-bottom: none;
}

.summary-row.total {
    font-size: 1.3rem;
    font-weight: 700;
    color: #2c3e50;
    padding-top: 15px;
    border-top: 2px solid #3498db;
}

/* Form Styles */
.order-form {
    display: flex;
    flex-direction: column;
    gap: 20px;
}

.form-group {
    display: flex;
    flex-direction: column;
    gap: 8px;
}

.form-group label {
    font-weight: 600;
    color: #2c3e50;
    font-size: 1rem;
}

.form-group input {
    padding: 12px 15px;
    border: 2px solid #e9ecef;
    border-radius: 8px;
    font-size: 1rem;
    transition: all 0.3s ease;
}

.form-group input:focus {
    outline: none;
    border-color: #3498db;
    box-shadow: 0 0 0 3px rgba(52, 152, 219, 0.1);
}

.form-group small {
    color: #6c757d;
    font-size: 0.85rem;
}

.confirm-order-btn {
    padding: 15px 30px;
    background: linear-gradient(135deg, #27ae60 0%, #2ecc71 100%);
    color: white;
    border: none;
    border-radius: 8px;
    font-size: 1.1rem;
    font-weight: 600;
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 10px;
    transition: all 0.3s ease;
    margin-top: 10px;
}

.confirm-order-btn:hover {
    transform: translateY(-2px);
    box-shadow: 0 10px 25px rgba(39, 174, 96, 0.3);
}

.confirm-order-btn:active {
    transform: translateY(0);
}

/* Empty Cart */
.empty-cart {
    text-align: center;
    padding: 60px 30px;
    color: #6c757d;
}

.empty-cart i {
    margin-bottom: 20px;
    color: #bdc3c7;
}

.empty-cart h3 {
    font-size: 1.8rem;
    margin-bottom: 15px;
    color: #2c3e50;
}

.empty-cart p {
    font-size: 1.1rem;
    margin-bottom: 30px;
}

.shop-btn {
    display: inline-flex;
    align-items: center;
    gap: 10px;
    padding: 12px 30px;
    background: linear-gradient(135deg, #3498db 0%, #2980b9 100%);
    color: white;
    text-decoration: none;
    border-radius: 8px;
    font-weight: 600;
    font-size: 1.1rem;
    transition: all 0.3s ease;
}

.shop-btn:hover {
    transform: translateY(-2px);
    box-shadow: 0 10px 25px rgba(52, 152, 219, 0.3);
}

/* Responsive Design */
@media (max-width: 968px) {
    .cart-content {
        grid-template-columns: 1fr;
        gap: 20px;
    }
    
    .order-summary {
        position: static;
    }
}

@media (max-width: 768px) {
    .cart-item {
        grid-template-columns: 100px 1fr;
        grid-template-rows: auto auto;
        gap: 15px;
    }
    
    .item-controls {
        grid-column: 1 / -1;
        flex-direction: row;
        justify-content: space-between;
        border-top: 1px solid #dee2e6;
        padding-top: 15px;
    }
    
    .item-total {
        grid-column: 2;
        grid-row: 1;
        justify-self: end;
    }
    
    .header h1 {
        font-size: 2rem;
    }
    
    .navigation {
        flex-direction: column;
    }
}

@media (max-width: 480px) {
    body {
        padding: 10px;
    }
    
    .cart-container {
        border-radius: 10px;
    }
    
    .header {
        padding: 20px;
    }
    
    .header h1 {
        font-size: 1.6rem;
    }
    
    .cart-content {
        padding: 20px 15px;
    }
    
    .cart-item {
        padding: 15px;
    }
    
    .item-image {
        width: 80px;
        height: 80px;
    }
    
    .item-details h3 {
        font-size: 1.1rem;
    }
    
    .empty-cart {
        padding: 40px 20px;
    }
}

/* Animation for cart items */
@keyframes slideIn {
    from {
        opacity: 0;
        transform: translateY(20px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

.cart-item {
    animation: slideIn 0.3s ease-out;
}

/* Loading state for buttons */
.confirm-order-btn:disabled {
    opacity: 0.6;
    cursor: not-allowed;
    transform: none !important;
}

/* Success state */
.confirm-order-btn.success {
    background: linear-gradient(135deg, #27ae60 0%, #2ecc71 100%);
}
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
                             <p class="item-price">Rs.
                                 <fmt:formatNumber value="${item.price}" pattern="#,##0.00" />
                             </p>
                         </div>

                        <div class="item-controls">
                            <form action="<%= request.getContextPath() %>/CustomerServlet"
                                method="post" class="quantity-form">
                                <input type="hidden" name="action" value="updateCart">
                                <input type="hidden" name="dishCode" value="${item.dishCode}">
                                <input type="hidden" name="size" value="${item.size}">
                                <input type="hidden" name="quantity" value="${item.quantity}">

                                <div class="quantity-controls">
                                    <button type="button" class="qty-btn minus"
                                        onclick="updateQuantity('${item.dishCode}', '${item.size}', ${item.quantity - 1})">
                                        <i class="fas fa-minus"></i>
                                    </button>
                                    <span class="quantity">${item.quantity}</span>
                                    <button type="button" class="qty-btn plus"
                                        onclick="updateQuantity('${item.dishCode}', '${item.size}', ${item.quantity + 1})">
                                        <i class="fas fa-plus"></i>
                                    </button>
                                </div>
                            </form>

                             <form action="<%= request.getContextPath() %>/CustomerServlet"
                                 method="post" class="remove-form">
                                 <input type="hidden" name="action" value="removeFromCart">
                                 <input type="hidden" name="dishCode" value="${item.dishCode}">
                                 <input type="hidden" name="size" value="${item.size}">
                                 <button type="submit" class="remove-btn">
                                     <i class="fas fa-trash"></i> Remove
                                 </button>
                             </form>
                         </div>

                         <div class="item-total">
                             Rs.
                             <fmt:formatNumber value="${item.totalPrice}" pattern="#,##0.00" />
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
                         <span>Rs.
                             <fmt:formatNumber value="${subtotal}" pattern="#,##0.00" />
                         </span>
                     </div>

                     <div class="summary-row">
                         <span>Tax (5%):</span>
                         <span>Rs.
                             <fmt:formatNumber value="${subtotal * 0.05}" pattern="#,##0.00" />
                         </span>
                     </div>

                     <div class="summary-row total">
                         <span>Total:</span>
                         <span>Rs.
                             <fmt:formatNumber value="${subtotal * 1.05}" pattern="#,##0.00" />
                         </span>
                     </div>
                 </div>

                 <!-- Customer Information Form -->
                 <form action="<%= request.getContextPath() %>/CustomerServlet" method="post"
                     class="order-form">
                     <input type="hidden" name="action" value="confirmTakeawayOrder">

                     <div class="form-group">
                         <label for="phoneNumber">Phone Number *</label>
                         <input type="tel" id="phoneNumber" name="phoneNumber"
                             placeholder="+94 771234567" required pattern="^\+94\s?\d{9}$">
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
             <a href="<%= request.getContextPath() %>/CustomerServlet?action=takeAway"
                 class="shop-btn">
                 <i class="fas fa-utensils"></i> Browse Menu
             </a>
         </div>
     </c:otherwise>
 </c:choose>
</div>

<script>
function updateQuantity(dishCode, size, newQuantity) {
    if (newQuantity < 1) {
        // If quantity is 0 or less, remove the item
        if (confirm('Remove this item from cart?')) {
            newQuantity = 0;
        } else {
            return;
        }
    }
    
    // Validate dishCode is not empty
    if (!dishCode || dishCode.trim() === '') {
        console.error('Invalid dishCode:', dishCode);
        alert('Error: Cannot update item. Please try again.');
        return;
    }

    console.log('Updating quantity - dishCode:', dishCode, 'size:', size, 'newQuantity:', newQuantity);

    // Find the form for this item by matching dishCode and size
    const forms = document.querySelectorAll('form.quantity-form');
    let targetForm = null;
    
    for (let form of forms) {
        const dishCodeInput = form.querySelector('input[name="dishCode"]');
        const sizeInput = form.querySelector('input[name="size"]');
        if (dishCodeInput && dishCodeInput.value === dishCode && 
            sizeInput && sizeInput.value === size) {
            targetForm = form;
            break;
        }
    }
    
    if (targetForm) {
        // Update the quantity input value
        const quantityInput = targetForm.querySelector('input[name="quantity"]');
        if (quantityInput) {
            quantityInput.value = newQuantity;
            targetForm.submit();
        } else {
            console.error('Quantity input not found in form');
            alert('Error: Cannot update quantity. Please refresh the page and try again.');
        }
    } else {
        console.error('Form not found for dishCode:', dishCode, 'size:', size);
        alert('Error: Cannot find item form. Please refresh the page and try again.');
    }
}

// Enhanced form handling
document.addEventListener('DOMContentLoaded', function() {
    // Add validation for all forms
    const forms = document.querySelectorAll('form');
    forms.forEach(form => {
        form.addEventListener('submit', function(e) {
            const dishCodeInput = this.querySelector('input[name="dishCode"]');
            if (dishCodeInput && (!dishCodeInput.value || dishCodeInput.value.trim() === '')) {
                e.preventDefault();
                console.error('Form submission: Invalid dishCode');
                alert('Error: Cannot process item. Please try again.');
                return;
            }
        });
    });

    // Add click handlers for remove buttons
    const removeButtons = document.querySelectorAll('.remove-btn');
    removeButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            if (!confirm('Are you sure you want to remove this item from your cart?')) {
                e.preventDefault();
            }
        });
    });
});
</script>

</body>

</html>