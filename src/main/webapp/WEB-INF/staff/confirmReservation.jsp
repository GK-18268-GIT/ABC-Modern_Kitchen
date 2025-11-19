<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ABC Modern Kitchen - Confirm Reservations</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/confirmReservation.css">

</head>
<body>

<div class= "confirmation-container">
	<div class = "header">
		<div class = "header-content">
			<h1><i class="fas fa-calendar-check"></i> Customer Reservations</h1>
			<div class = "header-actions">
				<a href="${pageContext.request.contextPath}/DashboardServlet" class="btn btn-outline">
                      <i class="fas fa-arrow-left"></i> Dashboard
                </a>
			</div>
		</div>
	</div>
	
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
    
    <c:if test = "${not empty dineInReservations && dineInReservations.size() > 0} ">
    	<div class = "stat-container">
    		<div class  = "stat-card">
    			<div class = "stat-number">${dineInReservations.size()}</div>
    			<div class = "stat-label">Total Reservations</div>
    		</div>
    		
    		<div class = "stat-card">
    			<div class = "stat-number">
	    			<c:set var = "pendingCount" value = "0" />
	    			<c:forEach var = "reservation" items = "${dineInReservations}">
	    				<c:if test = "${reservation.status == 'Pending'}">
	    					<c:set var = "pendingCount" value = "${pendingCount + 1}" />
	    				</c:if>
	    			</c:forEach>
	    			${pendingCount}
	    		</div>
	    		<div class = "stat-label">Pending</div>
    		</div>
    		
    		<div class = "stat-card">
    			<div class = "stat-number">
	    			<c:set var = "confirmCount" value = "0" />
	    			<c:forEach var = "reservation" items = "${dineInReservations}">
	    				<c:if test = "${reservation.status == 'Confirmed'}">
	    					<c:set var = "confirmCount" value = "${confirmCount + 1}" />
	    				</c:if>
	    			</c:forEach>
	    			${confirmCount}
	    		</div>
	    		<div class = "stat-label">Confirmed</div>
    		</div>	
    	</div>
    </c:if>
    
    <c:choose>
    	<c:when test="${not empty dineInReservations && dineInReservations.size() > 0}">
    		<div class = "reservation-grid">
    			<c:forEach var = "reservation" items = "${dineInReservations}">
    				<div class = "reservation-card ${reservation.status.toLowerCase()}">
    					<div class = "reservation-header">
    						<div>
    							<div class = "reservation-code">
    								<i class="fas fa-receipt"></i> ${reservation.reservationCode}
    							</div>
    						</div>
    						
    						<span class = "status-badge status-${reservation.status.toLowerCase()}">
    							<c:choose>
    								<c:when test = "${reservation.status == 'Pending'}">
    									<i class = "fas fa-clock"></i>
    								</c:when>
    								<c:when test = "${reservation.status == 'Confirmed'}">
    									<i class = "fas fa-check"></i>
    								</c:when>
    								<c:when test = "${reservation.status == 'Completed'}">
    									<i class = "fas fa-flag-checkeres"></i>
    								</c:when>
    								<c:when test = "${reservation.status == 'Cancelled'}">
    									<i class = "fas fa-times"></i>
    								</c:when>
    							</c:choose>
    							${reservation.status}	
    						</span>	
    					</div>
    					
    					<div class = "reservation-details">
    						<div class = "detail-item">
    							<span class = "detail-label"><i class = "fas fa-user"></i>Customer Name</span>
    							<span class = "detail-value">${reservation.customerName}</span>
    						</div>
    						<div class = "detail-item">
    							<span class = "detail-label"><i class = "fas fa-phone"></i>Phone Number</span>
    							<span class = "detail-value">${reservation.phoneNumber}</span>
    						</div>
    						<div class = "detail-item">
    							<span class = "detail-label"><i class = "fas fa-evenlope"></i>Email</span>
    							<span class = "detail-value">${reservation.email}</span>
    						</div>
    						<div class = "detail-item">
    							<span class = "detail-label"><i class = "fas fa-calendar"></i>Date & Time</span>
    							<span class = "detail-value">${reservation.formattedReservationDateTime}</span>
    						</div>
    						<div class = "detail-item">
    							<span class = "detail-label"><i class = "fas fa-users"></i>Number of Guests</span>
    							<span class = "detail-value">${reservation.numberOfGuests} people</span>
    						</div>
    						<div class = "detail-item">
    							<span class = "detail-label"><i class = "fas fa-chair"></i>Seating</span>
    							<span class = "detail-value">${reservation.displaySeatingPreference}</span>
    						</div>
    						<div class = "detail-item">
						        <span class = "detail-label"><i class = "fas fa-user-tie"></i>Assigned To</span>
						        <span class = "detail-value">You (${reservation.assignedStaffId})</span>
						    </div>
    					</div>
    					
    					<c:if test = "${not empty reservation.specialOccasion || not empty reservation.dietaryRestrictions}">
    						<div class = "special-info">
    						
    							<c:if test = "${not empty reservation.specialOccasion}">
    								<div class = "detail-item">
    									<span class = "detail-label"><i class = "fas fa-birthday-cake"></i>Special Occasion</span>
    									<span class = "detail-value">${reservation.specialOccasion}</span>
    								</div>
    							</c:if>
    							<c:if test = "${not empty reservation.dietaryRestrictions}">
    								<div class = "detail-item">
    									<span class = "detail-label"><i class = "fas fa-utensils"></i>Dietary Restrictions</span>
    									<span class = "detail-value">${reservation.dietaryRestrictions}</span>
    								</div>
    							</c:if>
    							
    						</div>
    					</c:if>
    					
    					<div class = "action-buttons">
    						<form action = "<%=request.getContextPath()%>/StaffServlet" method = "POST" class = "status-form">
    							<input type = "hidden" name = "action" value = "updateReservationStatus">
    							<input type = "hidden" name = "reservationId" value = "${reservation.id}">
    							<select name="status" class="status-select" onchange="this.form.submit()">
                                        <option value="Pending" ${reservation.status == 'Pending' ? 'selected' : ''}>Pending</option>
                                        <option value="Confirmed" ${reservation.status == 'Confirmed' ? 'selected' : ''}>Confirmed</option>
                                        <option value="Completed" ${reservation.status == 'Completed' ? 'selected' : ''}>Completed</option>
                                        <option value="Cancelled" ${reservation.status == 'Cancelled' ? 'selected' : ''}>Cancelled</option>
                                </select>
                                
                                <button type = "submit" class = "btn"><i class = "fas fa-sync-alt"></i>Update</button>
                                
    						</form>
    					</div>
    				</div>
    			</c:forEach>
    		</div>
    	</c:when>
    	
    	<c:otherwise>
    		<div class ="empty-state">
    			<i class = "fas fa-calendar-times"></i>
    			<h3>No Reservation Found</h3>
    			<p>There are no customer reservations to display at the moment.</p>
    		</div>
    	</c:otherwise>   	
    </c:choose>
</div>

<script>
setTimeout(function(){
	const alerts = document.querySelectorAll('.alert');
	alerts.forEach(alert => {
		alert.style.opacity = '0';
		alert.style.transform = 'translateY(-20px)';
		setTimeout(() => alert.style.display = 'none', 300);
	});
}, 5000);
</script>

</body>
</html>