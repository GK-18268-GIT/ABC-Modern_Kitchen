<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ABC Modern Kitchen - Admin Dashboard</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/adminDashboard.css">

</head>
<body>
    <%
        // Enhanced admin authentication check
        String adminName = (String) session.getAttribute("adminName");
        System.out.println("DEBUG: adminName from session: " + adminName);
        
        if (adminName == null) {
            // Try to get from admin object with better error handling
            Object adminObj = session.getAttribute("admin");
            System.out.println("DEBUG: admin object from session: " + adminObj);
            
            if (adminObj != null) {
                try {
                    // Try common getter methods for admin name
                    java.lang.reflect.Method getName = adminObj.getClass().getMethod("getName");
                    adminName = (String) getName.invoke(adminObj);
                    session.setAttribute("adminName", adminName);
                } catch (NoSuchMethodException e) {
                    try {
                        // Try getAdminName if getName doesn't exist
                        java.lang.reflect.Method getAdminName = adminObj.getClass().getMethod("getAdminName");
                        adminName = (String) getAdminName.invoke(adminObj);
                        session.setAttribute("adminName", adminName);
                    } catch (Exception e2) {
                        adminName = "Administrator";
                        session.setAttribute("adminName", adminName);
                    }
                } catch (Exception e) {
                    adminName = "Administrator";
                    session.setAttribute("adminName", adminName);
                }
            } else {
                // Debug: Check all session attributes
                System.out.println("DEBUG: Session attributes:");
                java.util.Enumeration<String> attrNames = session.getAttributeNames();
                while (attrNames.hasMoreElements()) {
                    String attrName = attrNames.nextElement();
                    System.out.println("  " + attrName + ": " + session.getAttribute(attrName));
                }
                
                // For testing, you can temporarily comment out the redirect:
               // adminName = "Test Admin"; // Remove this in production
               // session.setAttribute("adminName", adminName);
                
                // In production, uncomment this:
                 response.sendRedirect(request.getContextPath() + "/LoginServlet?action=login");
                 return;
            }
        }
    %>

    <div class="admin-dashboard-container">
        <!-- Left Column - Image Section -->
        <div class="image-section">
            <img src="https://img.freepik.com/premium-photo/small-business-owner-working-his-cafe_484651-11545.jpg?semt=ais_hybrid&w=740&q=80" alt="Admin Dashboard">
            <div class="image-overlay">
                <h1>ABC Modern Kitchen</h1>
                <p>Administrative Portal</p>
                <div class="welcome-text">Welcome <%= adminName %>!</div>
            </div>
        </div>
        
        <!-- Right Column - Content Section -->
        <div class="content-section">
            <div class="content-header">
                <h1>Admin Dashboard</h1>
                <p>Manage restaurant operations and staff</p>
            </div>
            
            <div class="navigation-cards">
                <!-- Staff Management Card -->
                <div class="navigate-card staff-management">
                    <a href="<%= request.getContextPath() %>/AdminServlet?action=manageStaff" class="navigate-button">
                        <div class="button-icon">
                            <i class="fas fa-users-cog"></i>
                        </div>
                        <div class="button-content">
                            <h3>Staff Management</h3>
                            <p>Register new staff members and administrators</p>
                        </div>
                        <div class="button-arrow">
                            <i class="fas fa-chevron-right"></i>
                        </div>
                    </a>
                </div>
                
                <!-- User Account Management Card -->
                <div class="navigate-card user-management">
                    <a href="<%= request.getContextPath() %>/AdminServlet?action=manageUsers" class="navigate-button">
                        <div class="button-icon">
                            <i class="fas fa-user-cog"></i>
                        </div>
                        <div class="button-content">
                            <h3>User Management</h3>
                            <p>Manage customer accounts and permissions</p>
                        </div>
                        <div class="button-arrow">
                            <i class="fas fa-chevron-right"></i>
                        </div>
                    </a>
                </div>
                
                <!-- Reports Card -->
                <div class="navigate-card reports">
                    <a href="<%= request.getContextPath() %>/AdminServlet?action=viewReports" class="navigate-button">
                        <div class="button-icon">
                            <i class="fas fa-chart-bar"></i>
                        </div>
                        <div class="button-content">
                            <h3>Reports & Analytics</h3>
                            <p>View sales reports and business analytics</p>
                        </div>
                        <div class="button-arrow">
                            <i class="fas fa-chevron-right"></i>
                        </div>
                    </a>
                </div>
                
                <!-- My Profile Card -->
                <div class="navigate-card profile">
                    <a href="<%= request.getContextPath() %>/AdminServlet?action=myAdminProfile" class="navigate-button">
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
                        if (adminName != null && !adminName.trim().isEmpty()) {
                            String[] nameParts = adminName.split(" ");
                            String initials = "";
                            if (nameParts.length > 0) {
                                initials += nameParts[0].substring(0, 1).toUpperCase();
                                if (nameParts.length > 1) {
                                    initials += nameParts[nameParts.length - 1].substring(0, 1).toUpperCase();
                                }
                            } else {
                                initials = "A";
                            }
                            out.print(initials);
                        } else {
                            out.print("A");
                        }
                    %>
                </div>
                <div class="user-details">
                    <h3><%= adminName != null ? adminName : "Administrator" %></h3>
                    <p>ABC Modern Kitchen Administrator</p>
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
        console.log("Admin Dashboard Loaded");
        console.log("Admin Name: <%= adminName %>");
        console.log("Context Path: <%= request.getContextPath() %>");
    </script>
</body>
</html>