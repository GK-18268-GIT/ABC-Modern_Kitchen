<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Management - ABC Modern Kitchen</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/list.css">
    <style>
        /* Inline styles as backup */
        .user-management-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        
        .header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 30px;
            border-radius: 15px;
            margin-bottom: 30px;
            text-align: center;
        }
        
        .header h1 {
            margin: 0;
            font-size: 2.5em;
            font-weight: 300;
        }
        
        .header p {
            margin: 10px 0 0 0;
            font-size: 1.1em;
            opacity: 0.9;
        }
        
        .tabs {
            display: flex;
            margin-bottom: 20px;
            background: #f8f9fa;
            border-radius: 10px;
            padding: 5px;
        }
        
        .tab {
            padding: 12px 24px;
            cursor: pointer;
            border-radius: 8px;
            transition: all 0.3s ease;
            font-weight: 500;
        }
        
        .tab.active {
            background: #667eea;
            color: white;
        }
        
        .tab-content {
            display: none;
        }
        
        .tab-content.active {
            display: block;
        }
        
        .user-table {
            width: 100%;
            background: white;
            border-radius: 10px;
            overflow: hidden;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            margin-bottom: 30px;
        }
        
        .user-table table {
            width: 100%;
            border-collapse: collapse;
        }
        
        .user-table th {
            background: #667eea;
            color: white;
            padding: 15px;
            text-align: left;
            font-weight: 500;
        }
        
        .user-table td {
            padding: 15px;
            border-bottom: 1px solid #eee;
        }
        
        .user-table tr:hover {
            background: #f8f9fa;
        }
        
        .user-avatar {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            background: #667eea;
            color: white;
            display: flex;
            align-items: center;
            justify-content: center;
            font-weight: bold;
            margin-right: 10px;
        }
        
        .user-info {
            display: flex;
            align-items: center;
        }
        
        .user-details h4 {
            margin: 0;
            font-weight: 500;
        }
        
        .user-details p {
            margin: 2px 0 0 0;
            color: #666;
            font-size: 0.9em;
        }
        
        .badge {
            padding: 4px 8px;
            border-radius: 20px;
            font-size: 0.8em;
            font-weight: 500;
        }
        
        .badge-admin {
            background: #ff6b6b;
            color: white;
        }
        
        .badge-staff {
            background: #4ecdc4;
            color: white;
        }
        
        .badge-customer {
            background: #45b7d1;
            color: white;
        }
        
        .action-buttons {
            display: flex;
            gap: 8px;
        }
        
        .btn {
            padding: 6px 12px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 0.85em;
            transition: all 0.3s ease;
        }
        
        .btn-edit {
            background: #ffeaa7;
            color: #2d3436;
        }
        
        .btn-delete {
            background: #ff7675;
            color: white;
        }
        
        .btn:hover {
            transform: translateY(-1px);
            box-shadow: 0 2px 4px rgba(0,0,0,0.2);
        }
        
        .back-btn {
            display: inline-flex;
            align-items: center;
            gap: 8px;
            padding: 10px 20px;
            background: #6c757d;
            color: white;
            text-decoration: none;
            border-radius: 8px;
            transition: all 0.3s ease;
            margin-bottom: 20px;
        }
        
        .back-btn:hover {
            background: #5a6268;
            transform: translateY(-1px);
        }
        
        .stats-container {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }
        
        .stat-card {
            background: white;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            text-align: center;
        }
        
        .stat-card i {
            font-size: 2.5em;
            margin-bottom: 15px;
        }
        
        .stat-card.admin { border-top: 4px solid #ff6b6b; }
        .stat-card.staff { border-top: 4px solid #4ecdc4; }
        .stat-card.customer { border-top: 4px solid #45b7d1; }
        
        .stat-number {
            font-size: 2em;
            font-weight: bold;
            margin: 10px 0;
        }
        
        .empty-state {
            text-align: center;
            padding: 60px 20px;
            color: #666;
        }
        
        .empty-state i {
            font-size: 4em;
            margin-bottom: 20px;
            opacity: 0.5;
        }
    </style>
</head>
<body>
    <div class="user-management-container">
        <!-- Header -->
        <div class="header">
            <h1><i class="fas fa-users-cog"></i> User Management</h1>
            <p>Manage all users of ABC Modern Kitchen</p>
        </div>
        
        <!-- Back Button -->
        <a href="<%= request.getContextPath() %>/DashboardServlet" class="back-btn">
            <i class="fas fa-arrow-left"></i> Back to Dashboard
        </a>
        
        <!-- Statistics -->
        <div class="stats-container">
            <div class="stat-card admin">
                <i class="fas fa-crown"></i>
                <div class="stat-number">${not empty administrator ? administrator.size() : 0}</div>
                <div class="stat-label">Administrators</div>
            </div>
            <div class="stat-card staff">
                <i class="fas fa-user-tie"></i>
                <div class="stat-number">${not empty staffMembers ? staffMembers.size() : 0}</div>
                <div class="stat-label">Staff Members</div>
            </div>
            <div class="stat-card customer">
                <i class="fas fa-user"></i>
                <div class="stat-number">${not empty customers ? customers.size() : 0}</div>
                <div class="stat-label">Customers</div>
            </div>
        </div>
        
        <!-- Tabs -->
        <div class="tabs">
            <div class="tab active" onclick="showTab('customers')">
                <i class="fas fa-users"></i> Customers
            </div>
            <div class="tab" onclick="showTab('staff')">
                <i class="fas fa-user-tie"></i> Staff
            </div>
            <div class="tab" onclick="showTab('admins')">
                <i class="fas fa-crown"></i> Administrators
            </div>
        </div>
        
        <!-- Customers Tab -->
        <div id="customers" class="tab-content active">
            <div class="user-table">
                <table>
                    <thead>
                        <tr>
                            <th>User</th>
                            <th>Contact</th>
                            <th>Address</th>
                            <th>Member Since</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty customers}">
                                <c:forEach var="customer" items="${customers}">
                                    <tr>
                                        <td>
                                            <div class="user-info">
                                                <div class="user-avatar">
                                                    <c:choose>
                                                        <c:when test="${not empty customer.profilePicture}">
                                                            <img src="<%=request.getContextPath()%>/${customer.profilePicture}" 
                                                                 alt="${customer.name}" style="width:100%;height:100%;border-radius:50%;object-fit:cover;">
                                                        </c:when>
                                                        <c:otherwise>
                                                            ${fn:substring(customer.name, 0, 1)}${fn:substring(customer.name, fn:indexOf(customer.name, ' ') + 1, fn:indexOf(customer.name, ' ') + 2)}
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                                <div class="user-details">
                                                    <h4>${customer.name}</h4>
                                                    <p>ID: ${customer.customerId}</p>
                                                </div>
                                            </div>
                                        </td>
                                        <td>
                                            <div>${customer.email}</div>
                                            <div style="color:#666;font-size:0.9em;">${customer.phoneNumber}</div>
                                        </td>
                                        <td>${customer.address}</td>
                                        <td>
                                            <fmt:formatDate value="${customer.createdAt}" pattern="MMM dd, yyyy"/>
                                        </td>
                                        <td>
                                            <div class="action-buttons">
                                                <button class="btn btn-edit">
                                                    <i class="fas fa-edit"></i> Edit
                                                </button>
                                                <button class="btn btn-delete">
                                                    <i class="fas fa-trash"></i> Delete
                                                </button>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="5">
                                        <div class="empty-state">
                                            <i class="fas fa-users"></i>
                                            <h3>No Customers Found</h3>
                                            <p>There are no customer accounts in the system yet.</p>
                                        </div>
                                    </td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
        </div>
        
        <!-- Staff Tab -->
        <div id="staff" class="tab-content">
            <div class="user-table">
                <table>
                    <thead>
                        <tr>
                            <th>Staff Member</th>
                            <th>Contact</th>
                            <th>Address</th>
                            <th>Member Since</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty staffMembers}">
                                <c:forEach var="staff" items="${staffMembers}">
                                    <tr>
                                        <td>
                                            <div class="user-info">
                                                <div class="user-avatar">
                                                    <c:choose>
                                                        <c:when test="${not empty staff.profilePicture}">
                                                            <img src="<%=request.getContextPath()%>/${staff.profilePicture}" 
                                                                 alt="${staff.name}" style="width:100%;height:100%;border-radius:50%;object-fit:cover;">
                                                        </c:when>
                                                        <c:otherwise>
                                                            ${fn:substring(staff.name, 0, 1)}${fn:substring(staff.name, fn:indexOf(staff.name, ' ') + 1, fn:indexOf(staff.name, ' ') + 2)}
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                                <div class="user-details">
                                                    <h4>${staff.name}</h4>
                                                    <p>ID: ${staff.staffId}</p>
                                                    <span class="badge badge-staff">Staff</span>
                                                </div>
                                            </div>
                                        </td>
                                        <td>
                                            <div>${staff.email}</div>
                                            <div style="color:#666;font-size:0.9em;">${staff.phoneNumber}</div>
                                        </td>
                                        <td>${staff.address}</td>
                                        <td>
                                            <fmt:formatDate value="${staff.createdAt}" pattern="MMM dd, yyyy"/>
                                        </td>
                                        <td>
                                            <div class="action-buttons">
                                                <button class="btn btn-edit">
                                                    <i class="fas fa-edit"></i> Edit
                                                </button>
                                                <button class="btn btn-delete">
                                                    <i class="fas fa-trash"></i> Delete
                                                </button>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="5">
                                        <div class="empty-state">
                                            <i class="fas fa-user-tie"></i>
                                            <h3>No Staff Members Found</h3>
                                            <p>There are no staff accounts in the system yet.</p>
                                        </div>
                                    </td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
        </div>
        
        <!-- Admins Tab -->
        <div id="admins" class="tab-content">
            <div class="user-table">
                <table>
                    <thead>
                        <tr>
                            <th>Administrator</th>
                            <th>Contact</th>
                            <th>Address</th>
                            <th>Member Since</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty administrator}">
                                <c:forEach var="admin" items="${administrator}">
                                    <tr>
                                        <td>
                                            <div class="user-info">
                                                <div class="user-avatar">
                                                    <c:choose>
                                                        <c:when test="${not empty admin.profilePicture}">
                                                            <img src="<%=request.getContextPath()%>/${admin.profilePicture}" 
                                                                 alt="${admin.name}" style="width:100%;height:100%;border-radius:50%;object-fit:cover;">
                                                        </c:when>
                                                        <c:otherwise>
                                                            ${fn:substring(admin.name, 0, 1)}${fn:substring(admin.name, fn:indexOf(admin.name, ' ') + 1, fn:indexOf(admin.name, ' ') + 2)}
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                                <div class="user-details">
                                                    <h4>${admin.name}</h4>
                                                    <p>ID: ${admin.adminId}</p>
                                                    <span class="badge badge-admin">Admin</span>
                                                </div>
                                            </div>
                                        </td>
                                        <td>
                                            <div>${admin.email}</div>
                                            <div style="color:#666;font-size:0.9em;">${admin.phoneNumber}</div>
                                        </td>
                                        <td>${admin.address}</td>
                                        <td>
                                            <fmt:formatDate value="${admin.createdAt}" pattern="MMM dd, yyyy"/>
                                        </td>
                                        <td>
                                            <div class="action-buttons">
                                                <button class="btn btn-edit">
                                                    <i class="fas fa-edit"></i> Edit
                                                </button>
                                                <button class="btn btn-delete">
                                                    <i class="fas fa-trash"></i> Delete
                                                </button>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="5">
                                        <div class="empty-state">
                                            <i class="fas fa-crown"></i>
                                            <h3>No Administrators Found</h3>
                                            <p>There are no administrator accounts in the system yet.</p>
                                        </div>
                                    </td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <script>
        function showTab(tabName) {
            // Hide all tab contents
            document.querySelectorAll('.tab-content').forEach(tab => {
                tab.classList.remove('active');
            });
            
            // Remove active class from all tabs
            document.querySelectorAll('.tab').forEach(tab => {
                tab.classList.remove('active');
            });
            
            // Show selected tab content
            document.getElementById(tabName).classList.add('active');
            
            // Add active class to clicked tab
            event.target.classList.add('active');
        }
        
        // Add some interactive features
        document.querySelectorAll('.btn-edit').forEach(btn => {
            btn.addEventListener('click', function() {
                alert('Edit functionality would be implemented here');
            });
        });
        
        document.querySelectorAll('.btn-delete').forEach(btn => {
            btn.addEventListener('click', function() {
                if(confirm('Are you sure you want to delete this user?')) {
                    alert('Delete functionality would be implemented here');
                }
            });
        });
        
        console.log('User Management Page Loaded');
        console.log('Customers: ${not empty customers ? customers.size() : 0}');
        console.log('Staff: ${not empty staffMembers ? staffMembers.size() : 0}');
        console.log('Admins: ${not empty administrator ? administrator.size() : 0}');
    </script>
</body>
</html>