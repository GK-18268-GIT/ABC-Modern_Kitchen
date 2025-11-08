<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ABC Modern Kitchen - Staff Management</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@400;600;700&family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"/>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/staffManagement.css">

</head>
<body>

<div class="staff-management-container">
    <!-- Left Column - Image Section -->
    <div class="img-section">
        <img src="https://img.freepik.com/free-photo/chef-holding-spatula-with-white-background_23-2147983662.jpg?semt=ais_hybrid&w=740&q=80" alt="Staff Management">
        <div class="image-overlay">
            <h1>ABC Modern Kitchen</h1>
            <p>Staff Management Portal</p>
            <div class="welcome-text">
                <%
                    String adminName = (String) session.getAttribute("adminName");
                    if (adminName != null) {
                        out.print("Welcome " + adminName + "!");
                    }
                %>
            </div>
        </div>
    </div>
    
    <!-- Right Column - Content Section -->
    <div class="content-section">
        <div class="content-header">
            <h1>Staff Management</h1>
            <p>Add new staff members and administrators</p>
        </div>
        
        <div class="navigation-cards">
            <div class="navigation-card staff-register">
                <!-- Register Staff Member -->
                <a href="<%= request.getContextPath() %>/RegisterServlet?action=staffRegister" class="navigate-button">
                    <div class="button-icon">
                        <i class="fas fa-user-plus"></i>
                    </div>
                    <div class="button-content">
                        <h3>Register New Staff Member</h3>
                        <p>Add new restaurant staff with appropriate permissions</p>
                    </div>
                    <div class="button-arrow">
                        <i class="fas fa-chevron-right"></i>
                    </div>
                </a>
            </div>
            
            <div class="navigation-card staff-register">
                <!-- Register Administrator -->
                <a href="<%= request.getContextPath() %>/RegisterServlet?action=adminRegister" class="navigate-button">
                    <div class="button-icon">
                        <i class="fas fa-user-shield"></i>
                    </div>
                    <div class="button-content">
                        <h3>Register New Administrator</h3>
                        <p>Add new administrative users with full system access</p>
                    </div>
                    <div class="button-arrow">
                        <i class="fas fa-chevron-right"></i>
                    </div>
                </a>
            </div>
        </div>
        
        <!-- Back to Dashboard -->
        <div class="back-section">
            <a href="<%= request.getContextPath() %>/DashboardServlet" class="back-btn">
                <i class="fas fa-arrow-left"></i>
                Back to Dashboard
            </a>
        </div>
    </div>
</div>

<script>
    // Add interactive effects
    document.querySelectorAll('.navigation-card').forEach(card => {
        card.addEventListener('mouseenter', function() {
            this.style.transform = 'translateY(-5px)';
        });
        
        card.addEventListener('mouseleave', function() {
            this.style.transform = 'translateY(0)';
        });
    });
</script>


</body>
</html>