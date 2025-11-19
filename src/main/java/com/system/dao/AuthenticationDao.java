package com.system.dao;

import com.system.utils.*;
import com.system.model.Staff;
import com.system.model.Admin;
import com.system.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

public class AuthenticationDao {
	
	public boolean addNewStaffMember(Staff staff, String hashedPassword) throws Exception {
		try(Connection conn = DBConnectionFactory.getConnection()) {
			String staffId = generateStaffId(conn);
			if (staffId == null) {
				throw new ServletException("[ERROR] Failed to generate Staff Id!");
			}
			
			if(emailExists(conn, staff.getEmail())) {
				throw new ServletException("[ERROR] Email is already exists: " + staff.getEmail());
			}
			
			String quary = "INSERT INTO users(username, password, role, name, email, address, phone_number,staff_id, profile_picture, created_at)"
					+ "VALUES (?,?,?,?,?,?,?,?,?,?)"; 
			
			try(PreparedStatement ps = conn.prepareStatement(quary)) {
				ps.setString(1, staff.getEmail());
				ps.setString(2, hashedPassword);
				ps.setString(3, "staff");
				ps.setString(4, staff.getName());
				ps.setString(5, staff.getEmail());
				ps.setString(6, staff.getAddress());
				ps.setString(7, staff.getPhoneNumber());
				ps.setString(8, staffId);
				ps.setString(9, staff.getProfilePicture());
				ps.setTimestamp(10, new Timestamp(System.currentTimeMillis()));
				return ps.executeUpdate() > 0;
				
			} catch(SQLException e) {
				throw new ServletException("[ERROR] Failed to register a new staff member!");
			}
		} catch(SQLException e) {
			throw new ServletException("[ERROR] DB Error: " + e.getMessage(), e);
		}
	}
			
	public boolean addNewAdmin(Admin admin, String hashedPassword) throws Exception {
		try(Connection conn = DBConnectionFactory.getConnection()) {
			String adminId = generateAdminId(conn);
			if (adminId == null) {
				throw new ServletException("[ERROR] Failed to generate Admin Id!");
			}
			
			if(emailExists(conn, admin.getEmail())) {
				throw new ServletException("[ERROR] Email is already exists: " + admin.getEmail());
			}
			
			String quary = "INSERT INTO users(username, password, role, name, email, address, phone_number,admin_id, profile_picture, created_at)"
					+ "VALUES (?,?,?,?,?,?,?,?,?,?)"; 
			
			try(PreparedStatement ps = conn.prepareStatement(quary)) {
				ps.setString(1, admin.getEmail());
				ps.setString(2, hashedPassword);
				ps.setString(3, "admin");
				ps.setString(4, admin.getName());
				ps.setString(5, admin.getEmail());
				ps.setString(6, admin.getAddress());
				ps.setString(7, admin.getPhoneNumber());
				ps.setString(8, adminId);
				ps.setString(9, admin.getProfilePicture());
				ps.setTimestamp(10, new Timestamp(System.currentTimeMillis()));
				return ps.executeUpdate() > 0;
				
			} catch(SQLException e) {
				throw new ServletException("[ERROR] Failed to register a new admin!");
			}
		} catch(SQLException e) {
			throw new ServletException("[ERROR] DB Error: " + e.getMessage(), e);
		}
		
	}
	
	public boolean addNewCustomer(Customer customer, String hashedPassword) throws Exception {
		try(Connection conn = DBConnectionFactory.getConnection()) {
			String customerId = generateCustomerId(conn);
			if (customerId == null) {
				throw new ServletException("[ERROR] Failed to generate Customer Id!");
			}
			
			if(emailExists(conn, customer.getEmail())) {
				throw new ServletException("[ERROR] Email is already exists: " + customer.getEmail());
			}
			
			String quary = "INSERT INTO users(username, password, role, name, email, address, phone_number,customer_id, profile_picture, created_at)"
					+ "VALUES (?,?,?,?,?,?,?,?,?,?)"; 
			
			try(PreparedStatement ps = conn.prepareStatement(quary)) {
				ps.setString(1, customer.getEmail());
				ps.setString(2, hashedPassword);
				ps.setString(3, "customer");
				ps.setString(4, customer.getName());
				ps.setString(5, customer.getEmail());
				ps.setString(6, customer.getAddress());
				ps.setString(7, customer.getPhoneNumber());
				ps.setString(8, customerId);
				ps.setString(9, customer.getProfilePicture());
				ps.setTimestamp(10, new Timestamp(System.currentTimeMillis()));
				return ps.executeUpdate() > 0;
				
			} catch(SQLException e) {
				throw new SQLException("[ERROR] Failed to register a new customer!");
			}
		} catch(SQLException e) {
			throw new SQLException("[ERROR] DB Error: " + e.getMessage(), e);
		}
		
	}
	
	// ID generate methods
	
	private String generateStaffId(Connection conn) throws SQLException {
		return generateUserId(conn, "staff", "staff_id");
	}
	
	private String generateAdminId(Connection conn) throws SQLException {
		return generateUserId(conn, "admin", "admin_id");
	}
	
	private String generateCustomerId(Connection conn) throws SQLException {
		return generateUserId(conn, "customer", "customer_id");
	}
	
	private String generateUserId(Connection conn, String prefix, String idColumn) throws SQLException {
		String quary = "SELECT " + idColumn + " FROM users WHERE " + idColumn + " LIKE ? ORDER BY " + idColumn + " DESC LIMIT 1";
		
		try(PreparedStatement ps = conn.prepareStatement(quary)) {
			ps.setString(1, prefix + "%");
			
			try(ResultSet rs = ps.executeQuery()) {
				int nextNumber = 1;
				if(rs.next()) {
					String lastId = rs.getString(idColumn);
					if(lastId != null) {
						String numberPart = lastId.replace(prefix, "");
						try {
							nextNumber = Integer.parseInt(numberPart) + 1;
						} catch(NumberFormatException e ) {
							throw new NumberFormatException("[ERROR] number format exception!");
						}
					}
				}
				
				return String.format("%s%03d", prefix, nextNumber);
			} catch(SQLException e) {
				throw new SQLException("[ERROR] Can't retrieved Id data!");
			}
			
		} catch(SQLException e) {
			throw new SQLException("[ERROR] DB error: " + e.getMessage(), e);
		}
		
	}
	
	private boolean emailExists(Connection conn, String email) throws Exception {
		String quary = "SELECT COUNT(*) FROM users WHERE email = ?";
		
		try(PreparedStatement ps = conn.prepareStatement(quary)) {
			ps.setString(1, email);
			
			try(ResultSet rs = ps.executeQuery()) {
				if(rs.next()) {
					return rs.getInt(1) > 0;
				}
			} catch(SQLException e) {
				throw new SQLException("[ERROR] Can't retrive data!");
			}
			
		} catch(SQLException e) {
			throw new SQLException("[ERROR] DB error: " + e.getMessage(), e);
		}
		return false;	
	}
	
	public String[] getUserCredentials(String email) throws Exception {
		String quary = "SELECT password, role FROM users WHERE email = ?";
		
		try(Connection conn = DBConnectionFactory.getConnection(); 
			PreparedStatement ps = conn.prepareStatement(quary)) {
				ps.setString(1, email);
				
				try (ResultSet rs = ps.executeQuery()) {
					if(rs.next()) {
						return new String[]{rs.getString("password"), rs.getString("role")};
					}
					
				}catch(SQLException e) {
					throw new SQLException("[ERROR] Can't retrive data");
				}
			} catch(SQLException e) {
				throw new SQLException("[ERROR] DB error" + e.getMessage(), e);
			}
		
		return null;
	}

	public List<Customer> getAllCustomers() throws Exception {
	    List<Customer> customers = new ArrayList<>();
	    String query = "SELECT * FROM users WHERE role = 'customer'";
	    
	    try(Connection conn = DBConnectionFactory.getConnection();
	        PreparedStatement ps = conn.prepareStatement(query);
	        ResultSet rs = ps.executeQuery()) {
	        while(rs.next()) {
	            Customer customer = new Customer(
	                rs.getString("name"),           
	                rs.getString("profile_picture"), 
	                rs.getString("email"),         
	                rs.getString("password"),      
	                rs.getString("address"),       
	                rs.getString("phone_number"),   
	                rs.getString("customer_id"),   
	                rs.getTimestamp("created_at"),  
	                rs.getTimestamp("updated_at")  
	            );
	            customers.add(customer);
	        }
	        System.out.println("[DEBUG] Retrieved " + customers.size() + " customers from database");
	    } catch(SQLException e) {
	         e.printStackTrace();
	         System.out.println("[ERROR]: Failed to retrieve customers: " + e.getMessage());
	    }
	    return customers;
	}

	public List<Staff> getAllStaff() throws Exception {
	    List<Staff> staffMembers = new ArrayList<>();
	    String query = "SELECT * FROM users WHERE role = 'staff'";
	    
	    try(Connection conn = DBConnectionFactory.getConnection();
	        PreparedStatement ps = conn.prepareStatement(query);
	        ResultSet rs = ps.executeQuery()) {
	        while(rs.next()) {
	            Staff staff = new Staff(
	                rs.getString("name"),           
	                rs.getString("profile_picture"), 
	                rs.getString("email"),         
	                rs.getString("password"),      
	                rs.getString("address"),       
	                rs.getString("phone_number"),   
	                rs.getString("staff_id"),   // Fixed: make sure this column exists  
	                rs.getTimestamp("created_at"),  
	                rs.getTimestamp("updated_at")  
	            );
	            staffMembers.add(staff);
	        }
	        System.out.println("[DEBUG] Retrieved " + staffMembers.size() + " staff members from database");
	    } catch(SQLException e) {
	         e.printStackTrace();
	         System.out.println("[ERROR]: Failed to retrieve staff members: " + e.getMessage());
	    }
	    return staffMembers;
	}

	public List<Admin> getAllAdmin() throws Exception {
	    List<Admin> administrator = new ArrayList<>();
	    String query = "SELECT * FROM users WHERE role = 'admin'";
	    
	    try(Connection conn = DBConnectionFactory.getConnection();
	        PreparedStatement ps = conn.prepareStatement(query);
	        ResultSet rs = ps.executeQuery()) {
	        while(rs.next()) {
	            Admin admin = new Admin(
	                rs.getString("name"),           
	                rs.getString("profile_picture"), 
	                rs.getString("email"),         
	                rs.getString("password"),      
	                rs.getString("address"),       
	                rs.getString("phone_number"),   
	                rs.getString("admin_id"),   // Fixed: make sure this column exists  
	                rs.getTimestamp("created_at"),  
	                rs.getTimestamp("updated_at")  
	            );
	            administrator.add(admin);
	        }
	        System.out.println("[DEBUG] Retrieved " + administrator.size() + " admins from database");
	    } catch(SQLException e) {
	         e.printStackTrace();
	         System.out.println("[ERROR]: Failed to retrieve administrators: " + e.getMessage());
	    }
	    return administrator;
	}
	
	
}


