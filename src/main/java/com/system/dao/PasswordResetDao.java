package com.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

import com.system.utils.DBConnectionFactory;

public class PasswordResetDao {
	public String genetateResetToken(String email) throws SQLException {
		
		deleteExistingTokens(email);
		
		String token = UUID.randomUUID().toString();
		Timestamp expiry_date = new Timestamp(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
		
		String query = "INSERT INTO password_reset_tokens (email, token, expiry_date) VALUES (?, ?, ?)";
		
		try(Connection conn = DBConnectionFactory.getConnection();
			PreparedStatement ps = conn.prepareStatement(query)) {
			
			ps.setString(1, email);
			ps.setString(2, token);
			ps.setTimestamp(3, expiry_date);
			
			if(ps.executeUpdate() > 0) {
				return token;
			}
			
		}
		
		return null;
		
	}
	
	public boolean isTokenValid(String token) throws SQLException {
		String query = "SELECT email FROM password_reset_tokens WHERE token = ? AND expiry_date > NOW() AND used = FALSE";
		
		try(Connection conn = DBConnectionFactory.getConnection();
			PreparedStatement ps = conn.prepareStatement(query)) {
			
			ps.setString(1, token);
			
			ResultSet rs = ps.executeQuery();
			
			return rs.next();
		}
		
	}
	
	public String getEmailByToken(String token) throws SQLException {
		String query = "SELECT email FROM password_reset_tokens WHERE token = ? AND expiry_date > NOW() AND used = FALSE";
		
		try(Connection conn = DBConnectionFactory.getConnection();
			PreparedStatement ps = conn.prepareStatement(query)) {
			
			ps.setString(1, token);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				return rs.getString("email");
			}
			
		}
		return null;
	}
	
	public boolean makeTokenAsUsed(String token) throws SQLException {
		String query = "UPDATE password_reset_tokens SET used = TRUE WHERE token = ? ";
		
		try(Connection conn = DBConnectionFactory.getConnection();
			PreparedStatement ps = conn.prepareStatement(query)) {
			
			ps.setString(1, token);
			return ps.executeUpdate() > 0;
		}
		
	}
	
	private void deleteExistingTokens(String email) throws SQLException {
		String query = "DELETE FROM password_reset_tokens WHERE email = ?";
		
		try(Connection conn = DBConnectionFactory.getConnection();
			PreparedStatement ps = conn.prepareStatement(query)) {
			
			ps.setString(1, email);;
			ps.executeUpdate();
			
		}
		
	}
	
	public boolean updatePassword(String email, String newHashPassword) throws SQLException {
		String query = "UPDATE users SET password = ?, updated_at = ? WHERE email = ?";
		
		try(Connection conn = DBConnectionFactory.getConnection();
			PreparedStatement ps = conn.prepareStatement(query)) {
			
			ps.setString(1, newHashPassword);
			ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			ps.setString(3, email);
			
			return ps.executeUpdate() > 0;
			
		}
		
	}
	
	public boolean isEmailExists(String email) throws SQLException {
		String query = "SELECT COUNT(*) FROM users WHERE email = ?";
		
		try(Connection conn = DBConnectionFactory.getConnection();
			PreparedStatement ps = conn.prepareStatement(query)) {
			
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				return rs.getInt(1) > 0;
			}
		}
		return false;
	}
	
	public String getUserByEmail(String email) throws SQLException {
		String query = "SELECT name FROM users WHERE email = ?";
		
		try(Connection conn = DBConnectionFactory.getConnection();
			PreparedStatement ps = conn.prepareStatement(query)) {
			
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				return rs.getString("name");
			}
		}
		return null;
	}

}
