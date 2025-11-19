<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Take Away Menu - ABC Modern Kitchen</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/editDishes.css">
</head>

<body>
<div class="header">
        <h1><i class="fas fa-edit"></i> Edit Dish</h1>
        <p>Update dish information</p>
    </div>
    
    <a href="<%= request.getContextPath() %>/StaffServlet?action=manageDishes" class="back-btn">
        <i class="fas fa-arrow-left"></i> Back to Manage Dishes
    </a>
    
    <c:if test="${not empty success}">
        <div class="alert alert-success">
            <i class="fas fa-check-circle"></i> ${success}
        </div>
    </c:if>
    
    <c:if test="${not empty error}">
        <div class="alert alert-error">
            <i class="fas fa-exclamation-circle"></i> ${error}
        </div>
    </c:if>

    <div class="form-container">
        <h2>Edit Dish: ${dish.name}</h2>
        <form action="<%= request.getContextPath() %>/StaffServlet" method="POST" enctype="multipart/form-data" id="editDishForm">
            <input type="hidden" name="action" value="updateDishes">
            <input type="hidden" name="id" value="${dish.id}">
            
            <div class="form-group">
                <label for="category">Category *</label>
                <input type="text" id="category" name="category" class="form-control" required 
                       value="${dish.category}" placeholder="e.g., Appetizers, Main Course, Desserts">
            </div>
            
            <div class="form-group">
                <label for="name">Dish Name *</label>
                <input type="text" id="name" name="name" class="form-control" required 
                       value="${dish.name}" placeholder="e.g., Chicken Burger, French Fries">
            </div>
            
            <div class="form-group">
                <label>Size Options *</label>
                <div style="display: flex; gap: 20px; margin-top: 10px;">
                    <label style="display: flex; align-items: center; gap: 5px;">
                        <input type="radio" name="sizeType" value="single" 
                               <c:if test="${dish.size != 'Both'}">checked</c:if> onchange="toggleSizeOptions()">
                        Single Size
                    </label>
                    <label style="display: flex; align-items: center; gap: 5px;">
                        <input type="radio" name="sizeType" value="both" 
                               <c:if test="${dish.size == 'Both'}">checked</c:if> onchange="toggleSizeOptions()">
                        Both Sizes (Normal & Large)
                    </label>
                </div>
            </div>
            
            <!-- Single Size Options -->
            <div id="singleSizeSection">
                <div class="form-group">
                    <label for="size">Size</label>
                    <select id="size" name="size" class="form-control">
                        <option value="Normal" <c:if test="${dish.size == 'Normal'}">selected</c:if>>Normal</option>
                        <option value="Large" <c:if test="${dish.size == 'Large'}">selected</c:if>>Large</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <label for="price">Price (Rs.) *</label>
                    <input type="number" id="price" name="price" class="form-control" 
                           step="0.01" min="0" placeholder="0.00"
                           value="<c:choose>
                               <c:when test="${dish.size == 'Normal'}">${dish.priceN}</c:when>
                               <c:when test="${dish.size == 'Large'}">${dish.priceL}</c:when>
                               <c:otherwise>0.00</c:otherwise>
                           </c:choose>">
                </div>
            </div>
            
            <!-- Both Sizes Options -->
            <div id="bothSizesSection" style="display: none;">
                <div class="form-group">
                    <label for="priceN">Normal Size Price (Rs.) *</label>
                    <input type="number" id="priceN" name="priceN" class="form-control" 
                           step="0.01" min="0" placeholder="0.00" value="${dish.priceN}">
                </div>
                
                <div class="form-group">
                    <label for="priceL">Large Size Price (Rs.) *</label>
                    <input type="number" id="priceL" name="priceL" class="form-control" 
                           step="0.01" min="0" placeholder="0.00" value="${dish.priceL}">
                </div>
            </div>
            
            <div class="form-group">
                <label for="dishImage">Dish Image</label>
                <input type="file" id="dishImage" name="dishImage" class="form-control" 
                       accept="image/*">
                <c:if test="${not empty dish.imagePath}">
                    <div class="current-image">
                        <p>Current Image:</p>
                        <img src="<%= request.getContextPath() %>/${dish.imagePath}" 
                             alt="${dish.name}">
                    </div>
                </c:if>
            </div>
            
            <div class="form-group">
                <label style="display: flex; align-items: center; gap: 5px;">
                    <input type="checkbox" name="isAvailable" value="true" 
                           <c:if test="${dish.available}">checked</c:if>>
                    Available for ordering
                </label>
            </div>
            
            <div class="button-group">
                <button type="submit" class="btn btn-primary">
                    <i class="fas fa-save"></i> Update Dish
                </button>
                <a href="<%= request.getContextPath() %>/StaffServlet?action=manageDishes" class="btn btn-secondary">
                    <i class="fas fa-times"></i> Cancel
                </a>
            </div>
        </form>
    </div>

    <script>
        function toggleSizeOptions() {
            const sizeType = document.querySelector('input[name="sizeType"]:checked').value;
            const singleSizeSection = document.getElementById('singleSizeSection');
            const bothSizesSection = document.getElementById('bothSizesSection');
            
            if (sizeType === 'single') {
                singleSizeSection.style.display = 'block';
                bothSizesSection.style.display = 'none';
                document.getElementById('price').required = true;
                document.getElementById('priceN').required = false;
                document.getElementById('priceL').required = false;
            } else {
                singleSizeSection.style.display = 'none';
                bothSizesSection.style.display = 'block';
                document.getElementById('price').required = false;
                document.getElementById('priceN').required = true;
                document.getElementById('priceL').required = true;
            }
        }
        
        // Initialize on page load
        document.addEventListener('DOMContentLoaded', function() {
            toggleSizeOptions();
        });
    </script>

</body>
</html>