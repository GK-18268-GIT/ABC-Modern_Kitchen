<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ABC Modern Kitchen - Manage Dishes</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/manageDishes.css">
                    
</head>

<body>
    <div class="header">
        <h1><i class="fas fa-utensils"></i> Manage Dishes</h1>
        <p>Add, edit, or remove dishes from the takeaway menu</p>
    </div>

    <a href="<%= request.getContextPath() %>/DashboardServlet" class="back-btn">
    <i class="fas fa-arrow-left"></i> Back to Dashboard
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

<div class="tabs">
    <div class="tab active" onclick="showTab(event, 'addDish')">
    <i class="fas fa-plus"></i> Add New Dish
</div>
<div class="tab" onclick="showTab(event, 'viewDishes')">
        <i class="fas fa-list"></i> View All Dishes
    </div>
</div>

<!-- Add Dish Tab -->
<div id="addDish" class="tab-content active">
    <div class="form-container">
        <h2>Add New Dish</h2>
        <form action="<%= request.getContextPath() %>/StaffServlet" method="POST"
enctype="multipart/form-data" id="dishForm">
<input type="hidden" name="action" value="addDishes">

<div class="form-group">
    <label for="category">Category *</label>
    <input type="text" id="category" name="category" class="form-control" required
        placeholder="e.g., Appetizers, Main Course, Desserts">
</div>

<div class="form-group">
    <label for="name">Dish Name *</label>
    <input type="text" id="name" name="name" class="form-control" required
        placeholder="e.g., Chicken Burger, French Fries">
</div>

<div class="form-group">
    <label>Size Options *</label>
    <div style="display: flex; gap: 20px; margin-top: 10px;">
     <label style="display: flex; align-items: center; gap: 5px;">
         <input type="radio" name="sizeType" value="single" checked
             onchange="toggleSizeOptions()">
         Single Size
     </label>
     <label style="display: flex; align-items: center; gap: 5px;">
            <input type="radio" name="sizeType" value="both"
                onchange="toggleSizeOptions()">
            Both Sizes (Normal & Large)
        </label>
    </div>
</div>

<!-- Single Size Options -->
<div id="singleSizeSection">
    <div class="form-group">
        <label for="size">Size</label>
        <select id="size" name="size" class="form-control">
            <option value="Normal">Normal</option>
            <option value="Large">Large</option>
        </select>
    </div>

    <div class="form-group">
        <label for="price">Price (Rs.) *</label>
        <input type="number" id="price" name="price" class="form-control" required
            step="0.01" min="0" placeholder="0.00">
    </div>
</div>

<!-- Both Sizes Options -->
<div id="bothSizesSection" style="display: none;">
    <div class="form-group">
        <label for="priceN">Normal Size Price (Rs.) *</label>
        <input type="number" id="priceN" name="priceN" class="form-control" step="0.01"
            min="0" placeholder="0.00">
    </div>

    <div class="form-group">
        <label for="priceL">Large Size Price (Rs.) *</label>
        <input type="number" id="priceL" name="priceL" class="form-control" step="0.01"
            min="0" placeholder="0.00">
    </div>
</div>

<div class="form-group">
    <label for="dishImage">Dish Image</label>
    <input type="file" id="dishImage" name="dishImage" class="form-control"
        accept="image/*">
</div>

<div class="form-group">
    <label style="display: flex; align-items: center; gap: 5px;">
                    <input type="checkbox" name="isAvailable" value="true" checked>
                    Available for ordering
                </label>
            </div>

            <button type="submit" class="btn btn-primary">
                <i class="fas fa-plus"></i> Add Dish
            </button>
        </form>
    </div>
</div>

<div id="viewDishes" class="tab-content">
    <div class="dishes-table">
        <table>
            <thead>
                <tr>
                    <th>Dish Code</th>
                    <th>Image</th>
                    <th>Name</th>
                    <th>Category</th>
                    <th>Size</th>
                    <th>Normal Price (Rs.)</th>
                    <th>Large Price (Rs.)</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
     <c:when test="${not empty dishes}">
         <c:forEach var="dish" items="${dishes}">
             <tr>
                 <td>
                     <strong>${dish.dishCode}</strong>
                 </td>
                 <td>
                     <c:choose>
                         <c:when test="${not empty dish.imagePath}">
                             <img src="<%= request.getContextPath() %>/${dish.imagePath}"
                                 alt="${dish.name}" class="dish-image">
                         </c:when>
                         <c:otherwise>
                             <div
                                 style="width:60px;height:60px;background:#f8f9fa;border-radius:8px;display:flex;align-items:center;justify-content:center;color:#666;">
                                 <i class="fas fa-utensils"></i>
                             </div>
                         </c:otherwise>
                     </c:choose>
                 </td>
                 <td>${dish.name}</td>
                 <td>${dish.category}</td>
                 <td>${dish.size}</td>
                 <td>
                     <c:if test="${not empty dish.priceN}">
                         <fmt:formatNumber value="${dish.priceN}"
                             pattern="#,##0.00" />
                     </c:if>
                 </td>
                 <td>
                     <c:if test="${not empty dish.priceL}">
                         <fmt:formatNumber value="${dish.priceL}"
                             pattern="#,##0.00" />
                     </c:if>
                 </td>
                 <td>
                     <c:choose>
                         <c:when test="${dish.available}">
                             <span class="status-available">Available</span>
                         </c:when>
                         <c:otherwise>
                             <span class="status-unavailable">Unavailable</span>
                         </c:otherwise>
                     </c:choose>
                 </td>
                 <td>
                     <a href="<%= request.getContextPath() %>/StaffServlet?action=editDish&id=${dish.id}"
                         class="btn"
                         style="background:#28a745;color:white;padding:5px 10px;">
                         <i class="fas fa-edit"></i> Edit
                     </a>
                     <form action="<%= request.getContextPath() %>/StaffServlet"
                         method="POST" style="display:inline;">
                         <input type="hidden" name="action" value="deleteDishes">
                         <input type="hidden" name="dishId" value="${dish.id}">
                         <button type="submit" class="btn"
                             style="background:#dc3545;color:white;padding:5px 10px;"
                             onclick="return confirm('Are you sure you want to delete this dish?')">
                             <i class="fas fa-trash"></i> Delete
                         </button>
                     </form>
                 </td>
             </tr>
         </c:forEach>
     </c:when>
     <c:otherwise>
         <tr>
             <td colspan="9" style="text-align: center; padding: 40px;">
                 <i class="fas fa-utensils"
                     style="font-size: 3em; color: #ddd; margin-bottom: 15px;"></i>
                 <h3 style="color: #666; margin: 0;">No Dishes Found</h3>
                 <p style="color: #888;">Add your first dish using the "Add New Dish"
                     tab</p>
             </td>
         </tr>
     </c:otherwise>
 </c:choose>
            </tbody>
        </table>
    </div>
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

function showTab(evt, tabName) {
    document.querySelectorAll('.tab-content').forEach(tab => {
        tab.classList.remove('active');
    });

    document.querySelectorAll('.tab').forEach(tab => {
        tab.classList.remove('active');
    });

    document.getElementById(tabName).classList.add('active');
    if (evt && evt.currentTarget) {
        evt.currentTarget.classList.add('active');
    }
}

document.addEventListener('DOMContentLoaded', function () {
    toggleSizeOptions();
});
</script>
</body>

</html>