package com.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.system.model.Admin;
import com.system.model.Staff;
import com.system.utils.DBConnectionFactory;

public class ProfileDao {
    
	public Admin getAdminById(String email) throws SQLException {
	    String query = "SELECT name, email, address, phone_number, profile_picture, admin_id, created_at, updated_at FROM users WHERE email = ? AND role = 'admin'";
	    
	    try(Connection conn = DBConnectionFactory.getConnection();
	        PreparedStatement ps = conn.prepareStatement(query)) {
	        
	        ps.setString(1, email);
	        ResultSet rs = ps.executeQuery();
	        
	        if(rs.next()) {
	            Admin admin = new Admin();
	            admin.setName(rs.getString("name"));
	            admin.setProfilePicture(rs.getString("profile_picture"));
	            admin.setEmail(rs.getString("email"));
	            admin.setAddress(rs.getString("address"));
	            
	            // Format phone number with space for display
	            String phoneNumber = rs.getString("phone_number");
	            if (phoneNumber != null && phoneNumber.startsWith("+94") && phoneNumber.length() == 12) {
	                phoneNumber = phoneNumber.substring(0, 3) + " " + phoneNumber.substring(3);
	            }
	            admin.setPhoneNumber(phoneNumber);
	            
	            admin.setAdminId(rs.getString("admin_id"));
	            admin.setCreatedAt(rs.getTimestamp("created_at"));
	            admin.setUpdatedAt(rs.getTimestamp("updated_at"));
	            return admin;
	        }
	        
	        return null;
	    }
	}
    public Staff getStaffById(String email) throws SQLException {
        String query = "SELECT name, email, address, phone_number, profile_picture, staff_id, created_at, updated_at FROM users WHERE email = ? AND role = 'staff'";
        
        try(Connection conn = DBConnectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                Staff staff = new Staff();
                staff.setName(rs.getString("name"));
                staff.setProfilePicture(rs.getString("profile_picture"));
                staff.setEmail(rs.getString("email"));
                staff.setAddress(rs.getString("address"));
                staff.setPhoneNumber(rs.getString("phone_number"));
                staff.setStaffId(rs.getString("staff_id"));
                staff.setCreatedAt(rs.getTimestamp("created_at"));
                staff.setUpdatedAt(rs.getTimestamp("updated_at"));
                return staff;
            }
            
            return null;
        }
    }
    
    public boolean updateAdminProfile(String email, String name, String address, String phoneNumber) throws SQLException {
        String query = "UPDATE users SET name = ?, address = ?, phone_number = ?, updated_at = ? WHERE email = ? AND role = 'admin'";
        
        try(Connection conn = DBConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, phoneNumber);
            ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            ps.setString(5, email);
            
            return ps.executeUpdate() > 0;
        }    
    }
    
    public boolean updateStaffProfile(String email, String name, String address, String phoneNumber) throws SQLException {
        String query = "UPDATE users SET name = ?, address = ?, phone_number = ?, updated_at = ? WHERE email = ? AND role = 'staff'";
        
        try(Connection conn = DBConnectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, phoneNumber);
            ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            ps.setString(5, email);
            
            return ps.executeUpdate() > 0;
        }    
    }
    
    public boolean updateAdminProfilePicture(String email, String profilePicture) throws SQLException {
        String query = "UPDATE users SET profile_picture = ?, updated_at = ? WHERE email = ? AND role = 'admin'";
        
        try(Connection conn = DBConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, profilePicture);
            ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            ps.setString(3, email);
            
            return ps.executeUpdate() > 0;
        }
    }
    
    public boolean updateStaffProfilePicture(String email, String profilePicture) throws SQLException {
        String query = "UPDATE users SET profile_picture = ?, updated_at = ? WHERE email = ? AND role = 'staff'";
        
        try(Connection conn = DBConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, profilePicture);
            ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            ps.setString(3, email);
            
            return ps.executeUpdate() > 0;
        }
    }
    
    public boolean changeAdminPassword(String email, String newHashPassword) throws SQLException {
        String query = "UPDATE users SET password = ?, updated_at = ? WHERE email = ? AND role = 'admin'";
        
        try(Connection conn = DBConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, newHashPassword);
            ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            ps.setString(3, email);
            
            return ps.executeUpdate() > 0;
        }
    }
    
    public String getAdminPasswordHashed(String email) throws SQLException {
        String query = "SELECT password FROM users WHERE email = ? AND role = 'admin'";
        
        try(Connection conn = DBConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                return rs.getString("password");
            }
            
            return null;
        }
    }
    
    public String getStaffPasswordHashed(String email) throws SQLException {
        String query = "SELECT password FROM users WHERE email = ? AND role = 'staff'";
        
        try(Connection conn = DBConnectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                return rs.getString("password");
            }
            
            return null;
        }
    }
    
    public boolean adminExists(String email) throws SQLException {
        String query = "SELECT COUNT(*) FROM users WHERE email = ? AND role = 'admin'";
        
        try(Connection conn = DBConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                return rs.getInt(1) > 0;
            }
            
            return false;
        }
    }
    
    public boolean staffExists(String email) throws SQLException {
        String query = "SELECT COUNT(*) FROM users WHERE email = ? AND role = 'staff'";
        
        try(Connection conn = DBConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                return rs.getInt(1) > 0;
            }
            
            return false;
        }
    }
}