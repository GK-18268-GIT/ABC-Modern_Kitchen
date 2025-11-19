<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ABC Modern Kitchen - Edit Your Reservation</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/editReservation.css">
</head>
    <style>
    
    /* Add these styles to your existing /WEB-INF/css/style.css */

.container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 20px;
}

header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 30px;
    padding-bottom: 20px;
    border-bottom: 2px solid #eee;
}

header h1 {
    margin: 0;
    color: #2c3e50;
}

.alert {
    padding: 15px;
    margin-bottom: 20px;
    border-radius: 4px;
}

.alert-success {
    background-color: #d4edda;
    color: #155724;
    border: 1px solid #c3e6cb;
}

.alert-error {
    background-color: #f8d7da;
    color: #721c24;
    border: 1px solid #f5c6cb;
}

.btn {
    padding: 10px 20px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    text-decoration: none;
    display: inline-block;
    text-align: center;
    font-size: 14px;
    transition: background-color 0.3s;
}

.btn:hover {
    opacity: 0.9;
}
        .form-container {
            max-width: 600px;
            margin: 0 auto;
            background-color: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        .form-group {
            margin-bottom: 20px;
        }
        .form-label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #333;
        }
        .form-control {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
            box-sizing: border-box;
        }
        .form-control:focus {
            border-color: #3498db;
            outline: none;
            box-shadow: 0 0 5px rgba(52, 152, 219, 0.3);
        }
        .btn-group {
            display: flex;
            gap: 10px;
            margin-top: 30px;
        }
        .btn {
            padding: 12px 24px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            text-align: center;
            font-size: 14px;
            flex: 1;
        }
        .btn-primary {
            background-color: #3498db;
            color: white;
        }
        .btn-secondary {
            background-color: #95a5a6;
            color: white;
        }
        .btn-danger {
            background-color: #e74c3c;
            color: white;
        }
        .reservation-info {
            background-color: #f8f9fa;
            padding: 15px;
            border-radius: 4px;
            margin-bottom: 20px;
            border-left: 4px solid #3498db;
        }
        .info-item {
            margin-bottom: 5px;
        }
        .info-label {
            font-weight: bold;
            color: #555;
        }
        .required {
            color: #e74c3c;
        }
    </style>
</head>
<body>
    <div class="container">
        <header>
            <h1>Edit Reservation</h1>
            <nav>
                <a href="${pageContext.request.contextPath}/DashboardServlet" class="btn">Dashboard</a>
                <a href="${pageContext.request.contextPath}/CustomerServlet?action=viewReservations" class="btn">My Reservations</a>
            </nav>
        </header>

        <div class="form-container">
            <!-- Reservation Information -->
            <div class="reservation-info">
                <div class="info-item">
                    <span class="info-label">Reservation Code:</span> ${reservation.reservationCode}
                </div>
                <div class="info-item">
                    <span class="info-label">Current Status:</span> 
                    <span style="color: 
                        <c:choose>
                            <c:when test="${reservation.status == 'Pending'}">#856404</c:when>
                            <c:when test="${reservation.status == 'Confirmed'}">#0c5460</c:when>
                            <c:when test="${reservation.status == 'Cancelled'}">#721c24</c:when>
                            <c:otherwise>#333</c:otherwise>
                        </c:choose>">
                        ${reservation.status}
                    </span>
                </div>
            </div>

            <!-- Success/Error Messages -->
            <c:if test="${not empty success}">
                <div class="alert alert-success">
                    ${success}
                </div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="alert alert-error">
                    ${error}
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/CustomerServlet?action=updateReservation" method="post">
                <input type="hidden" name="id" value="${reservation.id}">
                
                <div class="form-group">
                    <label for="customerName" class="form-label">Customer Name <span class="required">*</span></label>
                    <input type="text" id="customerName" name="customerName" class="form-control" 
                           value="${reservation.customerName}" required>
                </div>

                <div class="form-group">
                    <label for="phoneNumber" class="form-label">Phone Number <span class="required">*</span></label>
                    <input type="tel" id="phoneNumber" name="phoneNumber" class="form-control" 
                           value="${reservation.phoneNumber}" required>
                </div>

                <div class="form-group">
                    <label for="email" class="form-label">Email Address <span class="required">*</span></label>
                    <input type="email" id="email" name="email" class="form-control" 
                           value="${reservation.email}" required>
                </div>

                <div class="form-group">
                    <label for="reservationDateTime" class="form-label">Reservation Date & Time <span class="required">*</span></label>
                    <input type="datetime-local" id="reservationDateTime" name="reservationDateTime" class="form-control" 
                           value="<fmt:formatDate value="${reservation.reservationDateTime}" pattern="yyyy-MM-dd'T'HH:mm"/>" 
                           required>
                </div>

                <div class="form-group">
                    <label for="numberOfGuests" class="form-label">Number of Guests <span class="required">*</span></label>
                    <select id="numberOfGuests" name="numberOfGuests" class="form-control" required>
                        <option value="">Select number of guests</option>
                        <c:forEach begin="1" end="20" var="i">
                            <option value="${i}" <c:if test="${reservation.numberOfGuests == i}">selected</c:if>>${i} ${i == 1 ? 'person' : 'people'}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="seatingPreference" class="form-label">Seating Preference <span class="required">*</span></label>
                    <select id="seatingPreference" name="seatingPreference" class="form-control" required>
                        <option value="">Select seating preference</option>
                        <option value="inDoor" <c:if test="${reservation.seatingPreference == 'inDoor'}">selected</c:if>>Indoor</option>
                        <option value="outDoor" <c:if test="${reservation.seatingPreference == 'outDoor'}">selected</c:if>>Outdoor</option>
                        <option value="any" <c:if test="${reservation.seatingPreference == 'any'}">selected</c:if>>Any</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="specialOccasion" class="form-label">Special Occasion</label>
                    <select id="specialOccasion" name="specialOccasion" class="form-control">
                        <option value="">None</option>
                        <option value="Birthday" <c:if test="${reservation.specialOccasion == 'Birthday'}">selected</c:if>>Birthday</option>
                        <option value="Anniversary" <c:if test="${reservation.specialOccasion == 'Anniversary'}">selected</c:if>>Anniversary</option>
                        <option value="Business Dinner" <c:if test="${reservation.specialOccasion == 'Business Dinner'}">selected</c:if>>Business Dinner</option>
                        <option value="Date Night" <c:if test="${reservation.specialOccasion == 'Date Night'}">selected</c:if>>Date Night</option>
                        <option value="Family Gathering" <c:if test="${reservation.specialOccasion == 'Family Gathering'}">selected</c:if>>Family Gathering</option>
                        <option value="Other" <c:if test="${reservation.specialOccasion == 'Other'}">selected</c:if>>Other</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="dietaryRestrictions" class="form-label">Dietary Restrictions</label>
                    <textarea id="dietaryRestrictions" name="dietaryRestrictions" class="form-control" 
                              rows="3" placeholder="Any allergies, dietary restrictions, or special requests...">${reservation.dietaryRestrictions}</textarea>
                </div>

                <input type="hidden" name="status" value="${reservation.status}">

                <div class="btn-group">
                    <button type="submit" class="btn btn-primary">Update Reservation</button>
                    <a href="${pageContext.request.contextPath}/CustomerServlet?action=viewReservations" class="btn btn-secondary">Cancel</a>
                </div>
            </form>

            <c:if test="${reservation.status != 'Cancelled'}">
                <div style="margin-top: 20px; padding-top: 20px; border-top: 1px solid #ddd;">
                    <form action="${pageContext.request.contextPath}/CustomerServlet?action=deleteReservation" method="post" 
                          onsubmit="return confirm('Are you sure you want to cancel this reservation? This action cannot be undone.')">
                        <input type="hidden" name="id" value="${reservation.id}">
                        <button type="submit" class="btn btn-danger" style="width: 100%;">Cancel Reservation</button>
                    </form>
                </div>
            </c:if>
        </div>
    </div>

    <script>
        // Set minimum datetime for reservation to current time
        document.addEventListener('DOMContentLoaded', function() {
            const now = new Date();
            const localDateTime = now.toISOString().slice(0, 16);
            const datetimeInput = document.getElementById('reservationDateTime');
            
            // Set min attribute to current datetime
            datetimeInput.min = localDateTime;
            
            // Auto-hide alerts after 5 seconds
            setTimeout(function() {
                const alerts = document.querySelectorAll('.alert');
                alerts.forEach(alert => {
                    alert.style.display = 'none';
                });
            }, 5000);
        });
    </script>
</body>
</html>