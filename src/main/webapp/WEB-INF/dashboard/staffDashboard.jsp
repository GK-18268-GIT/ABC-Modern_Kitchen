<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ABC Modern Kitchen - Staff Dashboard</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/staffDashboard.css">

</head>
<body>
    <%
        // Check if staff is logged in
        String staffName = (String) session.getAttribute("staffName");
        if (staffName == null) {
            // Try to get from staff object
            Object staffObj = session.getAttribute("staff");
            if (staffObj != null) {
                // Assuming Staff class has getName() method
                try {
                    java.lang.reflect.Method getName = staffObj.getClass().getMethod("getName");
                    staffName = (String) getName.invoke(staffObj);
                    session.setAttribute("customerName", staffName);
                } catch (Exception e) {
                	staffName = "Customer";
                }
            } else {
                // Redirect to login if not logged in
                response.sendRedirect(request.getContextPath() + "/LoginServlet?action=login");
                return;
            }
        }
    %>

    <div class="staff-dashboard-container">
        <!-- Left Column - Image Section -->
        <div class="image-section">
            <img src="https://img.freepik.com/free-photo/we-serve-best-cakes_637285-7884.jpg?semt=ais_hybrid&w=740&q=80" alt="Staff Dashboard">
            <div class="image-overlay">
                <h1>ABC Modern Kitchen</h1>
                <p></p>
                <div class="welcome-text">Welcome <%= staffName %>!</div>
            </div>
        </div>
        
        <!-- Right Column - Content Section -->
        <div class="content-section">
            <div class="content-header">
                <h1>Staff Dashboard</h1>
                <p></p>
            </div>
            
            <div class="navigation-cards">
                <!-- Take Away Card -->
                <div class="navigate-card takeaway">
                    <a href="<%= request.getContextPath() %>/StaffServlet?action=manageDishes" class="navigate-button">
                        <div class="button-icon">
                            <i class="fas fa-utensils"></i>
                        </div>
                        <div class="button-content">
                            <h3>Take Away</h3>
                            <p>Manage and process customer takeaway orders efficiently</p>
                        </div>
                        <div class="button-arrow">
                            <i class="fas fa-chevron-right"></i>
                        </div>
                    </a>
                </div>
                
                <!-- Dine In Reservation Card -->
                <div class="navigate-card dinein">
                    <a href="<%= request.getContextPath() %>/StaffServlet?action=confirmReservation" class="navigate-button">
                        <div class="button-icon">
                            <i class="fas fa-calendar-check"></i>
                        </div>
                        <div class="button-content">
                            <h3>Dine In Reservation</h3>
                            <p>View and update table reservations made by customers</p>
                        </div>
                        <div class="button-arrow">
                            <i class="fas fa-chevron-right"></i>
                        </div>
                    </a>
                </div>
                
                <!-- My Profile Card -->
                <div class="navigate-card profile">
                    <a href="<%= request.getContextPath() %>/StaffServlet?action=myStaffProfile" class="navigate-button">
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
                        if (staffName != null) {
                            String[] nameParts = staffName.split(" ");
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
                    <h3><%= staffName %></h3>
                    <p>ABC Modern Kitchen Staff</p>
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
    </script>
</body>
</html>