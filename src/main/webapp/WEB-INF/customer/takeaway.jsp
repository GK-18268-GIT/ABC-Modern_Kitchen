<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Take Away Menu - ABC Modern Kitchen</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/takeaway.css">

</head>
<body>
   <div class="takeaway-container">
       <!-- Header -->
       <div class="header">
           <h1><i class="fas fa-utensils"></i> Take Away Menu</h1>
           <p>Order your favorite dishes for pickup</p>
       </div>

       <!-- Navigation Buttons -->
       <div style="display: flex; gap: 10px; margin-bottom: 20px; flex-wrap: wrap;">
           <a href="<%= request.getContextPath() %>/DashboardServlet" class="back-btn">
               <i class="fas fa-arrow-left"></i> Back to Dashboard
           </a>
           <a href="<%= request.getContextPath() %>/CustomerServlet?action=viewCart" class="back-btn"
               style="background: #27ae60;">
               <i class="fas fa-shopping-cart"></i> View Cart
               <c:if test="${not empty sessionScope.cart && !empty sessionScope.cart}">
                   <span
                       style="background: white; color: #27ae60; padding: 2px 8px; border-radius: 10px; margin-left: 5px; font-weight: bold;">
                       ${fn:length(sessionScope.cart)}
                   </span>
               </c:if>
           </a>
       </div>

       <!-- Messages -->
       <c:if test="${not empty error}">
           <div
               style="background: #f8d7da; color: #721c24; padding: 15px; border-radius: 5px; margin-bottom: 20px;">
               <i class="fas fa-exclamation-circle"></i> ${error}
           </div>
       </c:if>

       <!-- Menu Content -->
       <c:choose>
           <c:when test="${not empty dishes}">
               <c:forEach var="category" items="${categories}">
                   <div class="category-section">
                       <h2 class="category-title">${category}</h2>
                       <div class="dishes-grid">
                           <c:forEach var="dish" items="${dishes}">
                               <c:if test="${dish.category == category && dish.available}">
                                   <div class="dish-card">
                                       <c:choose>
                                           <c:when test="${not empty dish.imagePath}">
                                               <img src="<%= request.getContextPath() %>/${dish.imagePath}"
                                                   alt="${dish.name}" class="dish-image">
                                           </c:when>
                                           <c:otherwise>
                                               <div class="no-image">
                                                   <i class="fas fa-utensils fa-3x"></i>
                                               </div>
                                           </c:otherwise>
                                       </c:choose>
                                       <div class="dish-details">
                                           <div
                                               style="display: flex; justify-content: space-between; align-items: start; margin-bottom: 10px;">
                                               <h3 class="dish-name">${dish.name}</h3>
                                               <span
                                                   style="background: #667eea; color: white; padding: 4px 8px; border-radius: 4px; font-size: 0.8em; font-weight: 600;">
                                                   ${dish.dishCode}
                                               </span>
                                           </div>

                                           <c:choose>
                                               <c:when test="${dish.size == 'Both'}">
                                                   <div class="price-options">
                                                       <div class="price-option">
                                                           <span class="size-label">Normal</span>
                                                           <span class="price">Rs.
                                                               <fmt:formatNumber value="${dish.priceN}"
                                                                   pattern="#,##0.00" />
                                                           </span>
                                                           <button class="add-to-cart-btn"
                                                               data-dish-id="${dish.id}"
                                                               data-dish-code="${dish.dishCode}"
                                                               data-size="Normal"
                                                               data-price="${dish.priceN}">
                                                               <i class="fas fa-cart-plus"></i> Add
                                                           </button>
                                                       </div>
                                                       <div class="price-option">
                                                           <span class="size-label">Large</span>
                                                           <span class="price">Rs.
                                                               <fmt:formatNumber value="${dish.priceL}"
                                                                   pattern="#,##0.00" />
                                                           </span>
                                                           <button class="add-to-cart-btn"
                                                               data-dish-id="${dish.id}"
                                                               data-dish-code="${dish.dishCode}"
                                                               data-size="Large"
                                                               data-price="${dish.priceL}">
                                                               <i class="fas fa-cart-plus"></i> Add
                                                           </button>
                                                       </div>
                                                   </div>
                                               </c:when>
                                               <c:when test="${dish.size == 'Normal'}">
                                                   <div class="single-price">
                                                       <span class="price">Rs.
                                                           <fmt:formatNumber value="${dish.priceN}"
                                                               pattern="#,##0.00" />
                                                       </span>
                                                       <button class="add-to-cart-btn"
                                                           data-dish-id="${dish.id}"
                                                           data-dish-code="${dish.dishCode}"
                                                           data-size="Normal"
                                                           data-price="${dish.priceN}">
                                                           <i class="fas fa-cart-plus"></i> Add to Cart
                                                       </button>
                                                   </div>
                                               </c:when>
                                               <c:when test="${dish.size == 'Large'}">
                                                   <div class="single-price">
                                                       <span class="price">Rs.
                                                           <fmt:formatNumber value="${dish.priceL}"
                                                               pattern="#,##0.00" />
                                                       </span>
                                                       <button class="add-to-cart-btn"
                                                           data-dish-id="${dish.id}"
                                                           data-dish-code="${dish.dishCode}"
                                                           data-size="Large"
                                                           data-price="${dish.priceL}">
                                                           <i class="fas fa-cart-plus"></i> Add to Cart
                                                       </button>
                                                   </div>
                                               </c:when>
                                           </c:choose>
                                       </div>
                                   </div>
                               </c:if>
                           </c:forEach>
                       </div>
                   </div>
               </c:forEach>
           </c:when>
           <c:otherwise>
               <div class="empty-state">
                   <i class="fas fa-utensils"></i>
                   <h3>Menu Not Available</h3>
                   <p>We're currently updating our menu. Please check back later.</p>
               </div>
           </c:otherwise>
       </c:choose>
   </div>

   <script>
       document.querySelectorAll('.add-to-cart-btn').forEach(btn => {
           btn.addEventListener('click', function () {
               const dishCode = this.getAttribute('data-dish-code');
               const size = this.getAttribute('data-size');
               const price = this.getAttribute('data-price');
               const dishCard = this.closest('.dish-card');
               const dishName = dishCard.querySelector('.dish-name').textContent;

               // Create form and submit to add to cart
               const form = document.createElement('form');
               form.method = 'post';
               form.action = '<%= request.getContextPath() %>/CustomerServlet';

               const actionInput = document.createElement('input');
               actionInput.type = 'hidden';
               actionInput.name = 'action';
               actionInput.value = 'addToCart';
               form.appendChild(actionInput);

               const dishCodeInput = document.createElement('input');
               dishCodeInput.type = 'hidden';
               dishCodeInput.name = 'dishCode';
               dishCodeInput.value = dishCode;
               form.appendChild(dishCodeInput);

               const sizeInput = document.createElement('input');
               sizeInput.type = 'hidden';
               sizeInput.name = 'size';
               sizeInput.value = size;
               form.appendChild(sizeInput);

               document.body.appendChild(form);
               form.submit();
           });
       });
   </script>
</body>

</html>