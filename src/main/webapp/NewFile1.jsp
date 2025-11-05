<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ABC Modern Kitchen - Customer Dashboard</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

</head>
<body>
    <div class="customer-dashboard-container">
        <!-- Left Column - Image Section -->
        <div class="image-section">
            <img src="https://thumbs.dreamstime.com/b/lunch-home-fast-food-takeaway-restaurant-covid-lunch-home-fast-food-takeaway-restaurant-covid-397569759.jpg" alt="Customer Dashboard">
            <div class="image-overlay">
                <h1>ABC Modern Kitchen</h1>
                <p>Your Culinary Journey Starts Here</p>
                <div class="welcome-text">Welcome ${customer.Name}!</div>
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
                    <a href="CustomerServlet?action=takeAway" class="navigate-button">
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
                    <a href="CustomerServlet?action=dineIn" class="navigate-button">
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
                
                <!-- My Profile Card -->
                <div class="navigate-card profile">
                    <a href="CustomerServlet?action=myProfile" class="navigate-button">
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
                        // Generate initials from customer name
                        String customerName = (String) session.getAttribute("customerName");
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
                    <h3>${customer.Name}</h3>
                    <p>ABC Modern Kitchen Customer</p>
                </div>
                <form action="LogoutServlet" method="POST">
                    <button type="submit" class="logout-btn">
                        <i class="fas fa-sign-out-alt"></i> Logout
                    </button>
                </form>
            </div>
        </div>
    </div>

    <script>
        // Add some interactive effects
        document.querySelectorAll('.navigate-card').forEach(card => {
            card.addEventListener('mouseenter', function() {
                this.style.transform = 'translateY(-5px)';
            });
            
            card.addEventListener('mouseleave', function() {
                this.style.transform = 'translateY(0)';
            });
        });

        // Add loading animation
        window.addEventListener('load', function() {
            document.body.style.opacity = '0';
            document.body.style.transition = 'opacity 0.5s ease';
            
            setTimeout(function() {
                document.body.style.opacity = '1';
            }, 100);
        });
    </script>
</body>
</html>