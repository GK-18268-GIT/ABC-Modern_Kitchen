<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ABC Modern Kitchen - My Reservations</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/viewReservation.css">

</head>

<body>
    <div class="container">
        <div class="header">
            <div class="header-content">
                <h1><i class="fas fa-calendar-alt"></i> My Reservations</h1>
                <div class="header-actions">
                    <a href="${pageContext.request.contextPath}/DashboardServlet" class="btn btn-outline">
                        <i class="fas fa-arrow-left"></i> Dashboard
                    </a>
                    <a href="${pageContext.request.contextPath}/CustomerServlet?action=dineIn" class="btn btn-primary">
                        <i class="fas fa-plus"></i> New Reservation
                    </a>
                </div>
            </div>
        </div>

        <!-- Success/Error Messages -->
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

        <!-- Stats Section -->
        <c:if test="${not empty reservations && reservations.size() > 0}">
            <div class="stats-container">
                <div class="stat-card">
                    <div class="stat-number">${reservations.size()}</div>
                    <div class="stat-label">Total Reservations</div>
                </div>
                <div class="stat-card">
                    <div class="stat-number">
                        <c:set var="upcomingCount" value="0" />
                        <c:forEach var="reservation" items="${reservations}">
                            <c:if test="${reservation.upcoming}">
                                <c:set var="upcomingCount" value="${upcomingCount + 1}" />
                            </c:if>
                        </c:forEach>
                        ${upcomingCount}
                    </div>
                    <div class="stat-label">Upcoming</div>
                </div>
                <div class="stat-card">
                    <div class="stat-number">
                        <c:set var="pendingCount" value="0" />
                        <c:forEach var="reservation" items="${reservations}">
                            <c:if test="${reservation.status == 'Pending'}">
                                <c:set var="pendingCount" value="${pendingCount + 1}" />
                            </c:if>
                        </c:forEach>
                        ${pendingCount}
                    </div>
                    <div class="stat-label">Pending</div>
                </div>
            </div>
        </c:if>

        <c:choose>
            <c:when test="${not empty reservations && reservations.size() > 0}">
                <div class="reservations-grid">
                    <c:forEach var="reservation" items="${reservations}">
                        <div class="reservation-card">
                            <div class="reservation-header">
                                <div>
                                    <div class="reservation-code">
                                        <i class="fas fa-receipt"></i> ${reservation.reservationCode}
                                        <c:if test="${reservation.upcoming}">
                                            <span class="upcoming-badge">Upcoming</span>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="status-badge">
                                    <span class="reservation-status status-${reservation.status.toLowerCase()}">
                                        <c:choose>
                                            <c:when test="${reservation.status == 'Pending'}">
                                                <i class="fas fa-clock"></i>
                                            </c:when>
                                            <c:when test="${reservation.status == 'Confirmed'}">
                                                <i class="fas fa-check"></i>
                                            </c:when>
                                            <c:when test="${reservation.status == 'Cancelled'}">
                                                <i class="fas fa-times"></i>
                                            </c:when>
                                        </c:choose>
                                        ${reservation.status}
                                    </span>
                                </div>
                            </div>
                            
                            <div class="reservation-details">
                                <div class="detail-item">
								    <span class="detail-label"><i class="far fa-calendar"></i> Date & Time</span>
								    <span class="detail-value">
								        ${reservation.formattedReservationDateTime}
								    </span>
								</div>
                                <div class="detail-item">
                                    <span class="detail-label"><i class="fas fa-users"></i> Guests</span>
                                    <span class="detail-value">${reservation.numberOfGuests} people</span>
                                </div>
                                <div class="detail-item">
                                    <span class="detail-label"><i class="fas fa-chair"></i> Seating</span>
                                    <span class="detail-value">${reservation.displaySeatingPreference}</span>
                                </div>
                                <div class="detail-item">
                                    <span class="detail-label"><i class="fas fa-phone"></i> Contact</span>
                                    <span class="detail-value">${reservation.phoneNumber}</span>
                                </div>
                            </div>

                            <c:if test="${not empty reservation.specialOccasion || not empty reservation.dietaryRestrictions}">
                                <div class="special-info">
                                    <c:if test="${not empty reservation.specialOccasion}">
                                        <div class="detail-item">
                                            <span class="detail-label"><i class="fas fa-birthday-cake"></i> Special Occasion</span>
                                            <span class="detail-value">${reservation.specialOccasion}</span>
                                        </div>
                                    </c:if>
                                    <c:if test="${not empty reservation.dietaryRestrictions}">
                                        <div class="detail-item">
                                            <span class="detail-label"><i class="fas fa-utensils"></i> Dietary Restrictions</span>
                                            <span class="detail-value">${reservation.dietaryRestrictions}</span>
                                        </div>
                                    </c:if>
                                </div>
                            </c:if>

                            <div class="action-buttons">
                                <c:if test="${reservation.status != 'Cancelled' && reservation.upcoming}">
                                    <a href="${pageContext.request.contextPath}/CustomerServlet?action=editReservation&id=${reservation.id}" 
                                       class="btn btn-edit">
                                        <i class="fas fa-edit"></i> Edit
                                    </a>
                                    <a href="${pageContext.request.contextPath}/CustomerServlet?action=deleteReservation&id=${reservation.id}" 
                                       class="btn btn-cancel" 
                                       onclick="return confirm('Are you sure you want to cancel this reservation?')">
                                        <i class="fas fa-times"></i> Cancel
                                    </a>
                                </c:if>
                                <c:if test="${reservation.status == 'Cancelled'}">
                                    <span class="btn btn-disabled">
                                        <i class="fas fa-ban"></i> Cancelled
                                    </span>
                                </c:if>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <div class="empty-state">
                    <i class="far fa-calendar-times"></i>
                    <h3>No Reservations Found</h3>
                    <p>You haven't made any dine-in reservations yet. Start by creating your first reservation!</p>
                    <a href="${pageContext.request.contextPath}/CustomerServlet?action=dineIn" class="btn btn-primary">
                        <i class="fas fa-plus"></i> Make Your First Reservation
                    </a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <script>
        // Auto-hide alerts after 5 seconds
        setTimeout(function() {
            const alerts = document.querySelectorAll('.alert');
            alerts.forEach(alert => {
                alert.style.opacity = '0';
                alert.style.transform = 'translateY(-20px)';
                setTimeout(() => alert.style.display = 'none', 300);
            });
        }, 5000);

        // Add hover effects
        document.addEventListener('DOMContentLoaded', function() {
            const cards = document.querySelectorAll('.reservation-card');
            cards.forEach(card => {
                card.addEventListener('mouseenter', function() {
                    this.style.transform = 'translateY(-5px)';
                });
                card.addEventListener('mouseleave', function() {
                    this.style.transform = 'translateY(0)';
                });
            });
        });
    </script>
</body>
</html>