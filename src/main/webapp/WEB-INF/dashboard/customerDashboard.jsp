<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ABC Modern Kitchen - Customer Dashboard</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/customerDashboard.css">

</head>
<body>
    <%
        // Check if customer is logged in
        String customerName = (String) session.getAttribute("customerName");
        if (customerName == null) {
            // Try to get from customer object
            Object customerObj = session.getAttribute("customer");
            if (customerObj != null) {
                // Assuming Customer class has getName() method
                try {
                    java.lang.reflect.Method getName = customerObj.getClass().getMethod("getName");
                    customerName = (String) getName.invoke(customerObj);
                    session.setAttribute("customerName", customerName);
                } catch (Exception e) {
                    customerName = "Customer";
                }
            } else {
                // Redirect to login if not logged in
                response.sendRedirect(request.getContextPath() + "/LoginServlet?action=login");
                return;
            }
        }
    %>

    <div class="customer-dashboard-container">
        <!-- Left Column - Image Section -->
        <div class="image-section">
            <img src="https://thumbs.dreamstime.com/b/lunch-home-fast-food-takeaway-restaurant-covid-lunch-home-fast-food-takeaway-restaurant-covid-397569759.jpg" alt="Customer Dashboard">
            <div class="image-overlay">
                <h1>ABC Modern Kitchen</h1>
                <p>Your Culinary Journey Starts Here</p>
                <div class="welcome-text">Welcome <%= customerName %>!</div>
            </div>
        </div>
        
        <!-- Right Column - Content Section -->
        <div class="content-section">
            <div class="content-header">
                <h1>Customer Dashboard</h1>
                <p>Manage your dining experience with us</p>
            </div>
            
            <div class="navigation-cards">
                <!-- Take Away Card -->
                <div class="navigate-card takeaway">
                    <a href="<%= request.getContextPath() %>/CustomerServlet?action=takeAway" class="navigate-button">
                        <div class="button-icon">
                            <i class="fas fa-utensils"></i>
                        </div>
                        <div class="button-content">
                            <h3>Take Away</h3>
                            <p>Order your favorite meals for pickup and enjoy at home</p>
                        </div>
                        <div class="button-arrow">
                            <i class="fas fa-chevron-right"></i>
                        </div>
                    </a>
                </div>
                
                <!-- Dine In Reservation Card -->
                <div class="navigate-card dinein">
                    <a href="<%= request.getContextPath() %>/CustomerServlet?action=dineIn" class="navigate-button">
                        <div class="button-icon">
                            <i class="fas fa-calendar-check"></i>
                        </div>
                        <div class="button-content">
                            <h3>Dine In Reservation</h3>
                            <p>Book a table for an exceptional dining experience</p>
                        </div>
                        <div class="button-arrow">
                            <i class="fas fa-chevron-right"></i>
                        </div>
                    </a>
                </div>
                
                <!-- My Reservations Card -->
                <div class="navigate-card reservations">
                    <a href="<%= request.getContextPath() %>/CustomerServlet?action=viewReservations" class="navigate-button">
                        <div class="button-icon">
                            <i class="fas fa-list-alt"></i>
                        </div>
                        <div class="button-content">
                            <h3>My Reservations</h3>
                            <p>View and manage your existing table reservations</p>
                        </div>
                        <div class="button-arrow">
                            <i class="fas fa-chevron-right"></i>
                        </div>
                    </a>
                </div>
                
                <!-- My Profile Card -->
                <div class="navigate-card profile">
                    <a href="<%= request.getContextPath() %>/CustomerServlet?action=myCustomerProfile" class="navigate-button">
                        <div class="button-icon">
                            <i class="fas fa-user"></i>
                        </div>
                        <div class="button-content">
                            <h3>My Profile</h3>
                            <p>Manage your account details and preferences</p>
                        </div>
                        <div class="button-arrow">
                            <i class="fas fa-chevron-right"></i>
                        </div>
                    </a>
                </div>
            </div>
            
            <!-- User Info Section -->
            <div class="user-info">
                <div class="user-avatar">
                    <%
                        if (customerName != null) {
                            String[] nameParts = customerName.split(" ");
                            String initials = "";
                            if (nameParts.length > 0) {
                                initials += nameParts[0].substring(0, 1).toUpperCase();
                                if (nameParts.length > 1) {
                                    initials += nameParts[nameParts.length - 1].substring(0, 1).toUpperCase();
                                }
                            }
                            out.print(initials);
                        } else {
                            out.print("U");
                        }
                    %>
                </div>
                <div class="user-details">
                    <h3><%= customerName %></h3>
                    <p>ABC Modern Kitchen Customer</p>
                </div>
                <form action="<%= request.getContextPath() %>/LoginServlet" method="POST">
                    <input type="hidden" name="action" value="logOutUser">
                    <button type="submit" class="logout-btn">
                        <i class="fas fa-sign-out-alt"></i> Logout
                    </button>
                </form>
            </div>
        </div>
    </div>

    <script>
        // Add interactive effects
        document.querySelectorAll('.navigate-card').forEach(card => {
            card.addEventListener('mouseenter', function() {
                this.style.transform = 'translateY(-5px)';
            });
            
            card.addEventListener('mouseleave', function() {
                this.style.transform = 'translateY(0)';
            });
        });

        // Debug info
        console.log("Customer Dashboard Loaded");
        console.log("Customer Name: <%= customerName %>");
        console.log("Context Path: <%= request.getContextPath() %>");
    </script>
</body>
</html>