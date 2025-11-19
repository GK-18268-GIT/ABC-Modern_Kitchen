package com.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.system.utils.DBConnectionFactory;
import com.system.model.DineInReservation;

public class DineInReservationDao {
	public boolean addReservation(DineInReservation dineInReservation) throws SQLException {
	    String query = "INSERT INTO dinein (reservation_code, customer_name, phone_number, email, reservation_dateTime, numberOf_guests, dietary_restrictions,"
	            + "special_occasion, seating_preference, status, created_at, updated_at)" // Added status
	            + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; // Now 12 parameters
	    
	    try(Connection conn = DBConnectionFactory.getConnection();
	        PreparedStatement ps = conn.prepareStatement(query)) {
	        
	        String reservationCode = generateReservationCode();
	        
	        ps.setString(1, reservationCode);
	        ps.setString(2, dineInReservation.getCustomerName());
	        ps.setString(3, dineInReservation.getPhoneNumber());
	        ps.setString(4, dineInReservation.getEmail());
	        ps.setTimestamp(5, Timestamp.valueOf(dineInReservation.getReservationDateTime()));
	        ps.setInt(6, dineInReservation.getNumberOfGuests());
	        ps.setString(7, dineInReservation.getDietaryRestrictions());
	        ps.setString(8, dineInReservation.getSpecialOccasion());
	        ps.setString(9, dineInReservation.getSeatingPreference());
	        ps.setString(10, dineInReservation.getStatus()); // status
	        ps.setTimestamp(11, new Timestamp(System.currentTimeMillis())); // created_at
	        ps.setTimestamp(12, new Timestamp(System.currentTimeMillis())); // updated_at
	        
	        return ps.executeUpdate() > 0;
	    }
	}
	
	public boolean updateReservation(DineInReservation dineInReservation) throws SQLException {
	    String query = "UPDATE dinein SET customer_name = ?, phone_number = ?, email = ?, reservation_dateTime = ?, "
	            + "numberOf_guests = ?, dietary_restrictions = ?, special_occasion = ?, seating_preference = ?, "
	            + "status = ?, updated_at = ? WHERE id = ?"; // Added status field
	    
	    try(Connection conn = DBConnectionFactory.getConnection();
	        PreparedStatement ps = conn.prepareStatement(query)) {
	        
	        ps.setString(1, dineInReservation.getCustomerName());
	        ps.setString(2, dineInReservation.getPhoneNumber());
	        ps.setString(3, dineInReservation.getEmail());
	        ps.setTimestamp(4, Timestamp.valueOf(dineInReservation.getReservationDateTime()));
	        ps.setInt(5, dineInReservation.getNumberOfGuests());
	        ps.setString(6, dineInReservation.getDietaryRestrictions());
	        ps.setString(7, dineInReservation.getSpecialOccasion());
	        ps.setString(8, dineInReservation.getSeatingPreference());
	        ps.setString(9, dineInReservation.getStatus()); // status
	        ps.setTimestamp(10, new Timestamp(System.currentTimeMillis())); // updated_at
	        ps.setInt(11, dineInReservation.getId()); // id
	        
	        return ps.executeUpdate() > 0;
	    }
	}
	
	public boolean deleteReservation(int id) throws SQLException {
		String query = "UPDATE dinein SET status = 'Cancelled', updated_at = ? WHERE id = ?";
		
		try(Connection conn = DBConnectionFactory.getConnection();
				PreparedStatement ps = conn.prepareStatement(query)) {
				
				ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
				ps.setInt(2, id);
				
				return ps.executeUpdate() > 0;
			}
	}
	
	private String generateReservationCode() throws SQLException {
		String prefix = "DIN";
		String datePart = java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        int randomNum = 1000 + new Random().nextInt(9000);
        return prefix + "-" + datePart + "-" + randomNum;
	}
	
	public List<DineInReservation> getAllReservations() throws SQLException {
		List<DineInReservation> dineInReservations = new ArrayList<>();
		String query = "SELECT * FROM dinein ORDER BY reservation_code";
		
		try(Connection conn = DBConnectionFactory.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery()) {
			
			while(rs.next()) {
				DineInReservation dineInReservation = new DineInReservation();
				
				dineInReservation.setId(rs.getInt("id"));
				dineInReservation.setReservationCode(rs.getString("reservation_code"));
				dineInReservation.setCustomerName(rs.getString("customer_name"));
				dineInReservation.setPhoneNumber(rs.getString("phone_number"));
				dineInReservation.setEmail(rs.getString("email"));
				
				Timestamp ts = rs.getTimestamp("reservation_datetime");
				if(ts != null) {
					dineInReservation.setReservationDateTime(ts.toLocalDateTime());
				}

				dineInReservation.setNumberOfGuests(rs.getInt("numberOf_guests"));
				dineInReservation.setDietaryRestrictions(rs.getString("dietary_restrictions"));
				dineInReservation.setSpecialOccasion(rs.getString("special_occasion"));
				dineInReservation.setSeatingPreference(rs.getString("seating_preference"));
				dineInReservation.setStatus(rs.getString("status"));
				dineInReservation.setCreatedAt(rs.getTimestamp("created_at"));
				dineInReservation.setUpdatedAt(rs.getTimestamp("updated_at"));
				
				dineInReservations.add(dineInReservation);
			}
		}
		return dineInReservations;
	}
	
	public DineInReservation getReservationsById(int id) throws SQLException {
		String query = "SELECT * FROM dinein WHERE id = ?";
		
		try(Connection conn = DBConnectionFactory.getConnection();
			PreparedStatement ps = conn.prepareStatement(query)) {
			
			ps.setInt(1, id);
			try(ResultSet rs = ps.executeQuery()) {
				if(rs.next()) {
					
					DineInReservation dineInReservation = new DineInReservation();
					
					dineInReservation.setId(rs.getInt("id"));
					dineInReservation.setReservationCode(rs.getString("reservation_code"));
					dineInReservation.setCustomerName(rs.getString("customer_name"));
					dineInReservation.setPhoneNumber(rs.getString("phone_number"));
					dineInReservation.setEmail(rs.getString("email"));
					
					Timestamp ts = rs.getTimestamp("reservation_datetime");
					if(ts != null) {
						dineInReservation.setReservationDateTime(ts.toLocalDateTime());
					}
					
					dineInReservation.setNumberOfGuests(rs.getInt("numberOf_guests"));
					dineInReservation.setDietaryRestrictions(rs.getString("dietary_restrictions"));
					dineInReservation.setSpecialOccasion(rs.getString("special_occasion"));
					dineInReservation.setSeatingPreference(rs.getString("seating_preference"));
					dineInReservation.setStatus(rs.getString("status"));
					dineInReservation.setAssignedStaffId(rs.getString("assigned_staff_id"));
					dineInReservation.setCreatedAt(rs.getTimestamp("created_at"));
					dineInReservation.setUpdatedAt(rs.getTimestamp("updated_at"));
					
					return dineInReservation;
				}
			}
		}
		return null;
	}
	
	public DineInReservation getDineInReservationByReservationCode(String reservationCode) throws SQLException {
		String query = "SELECT * FROM dinein WHERE reservation_code = ?";
		
		try(Connection conn = DBConnectionFactory.getConnection();
			PreparedStatement ps = conn.prepareStatement(query)) {
			
			ps.setString(1, reservationCode);
			try(ResultSet rs = ps.executeQuery()) {
				if(rs.next()) {
					
					DineInReservation dineInReservation = new DineInReservation();
					
					dineInReservation.setId(rs.getInt("id"));
					dineInReservation.setReservationCode(rs.getString("reservation_code"));
					dineInReservation.setCustomerName(rs.getString("customer_name"));
					dineInReservation.setPhoneNumber(rs.getString("phone_number"));
					dineInReservation.setEmail(rs.getString("email"));
					
					Timestamp ts = rs.getTimestamp("reservation_datetime");
					if(ts != null) {
						dineInReservation.setReservationDateTime(ts.toLocalDateTime());
					}
					
					dineInReservation.setNumberOfGuests(rs.getInt("numberOf_guests"));
					dineInReservation.setDietaryRestrictions(rs.getString("dietary_restrictions"));
					dineInReservation.setSpecialOccasion(rs.getString("special_occasion"));
					dineInReservation.setSeatingPreference(rs.getString("seating_preference"));
					dineInReservation.setCreatedAt(rs.getTimestamp("created_at"));
					dineInReservation.setUpdatedAt(rs.getTimestamp("updated_at"));
					
					return dineInReservation;
				}
			}
		}
		return null;
	}
	
	public List<DineInReservation> getReservationsByCustomer(String customerName, String email) throws SQLException {
		List<DineInReservation> dineInReservations = new ArrayList<>();
		String query = "SELECT * FROM dinein WHERE customer_name = ? AND email = ? ORDER BY reservation_datetime DESC";
		
		try(Connection conn = DBConnectionFactory.getConnection();
			PreparedStatement ps = conn.prepareStatement(query)) {
			
			ps.setString(1, customerName);
			ps.setString(2, email);
			
			try(ResultSet rs = ps.executeQuery()) {
				while(rs.next()) {
	                DineInReservation dineInReservation = new DineInReservation();
	                
	                dineInReservation.setId(rs.getInt("id"));
	                dineInReservation.setReservationCode(rs.getString("reservation_code"));
	                dineInReservation.setCustomerName(rs.getString("customer_name"));
	                dineInReservation.setPhoneNumber(rs.getString("phone_number"));
	                dineInReservation.setEmail(rs.getString("email"));
	                
	                Timestamp ts = rs.getTimestamp("reservation_datetime");
	                if(ts != null) {
	                    dineInReservation.setReservationDateTime(ts.toLocalDateTime());
	                }

	                dineInReservation.setNumberOfGuests(rs.getInt("numberOf_guests"));
	                dineInReservation.setDietaryRestrictions(rs.getString("dietary_restrictions"));
	                dineInReservation.setSpecialOccasion(rs.getString("special_occasion"));
	                dineInReservation.setSeatingPreference(rs.getString("seating_preference"));
	                dineInReservation.setStatus(rs.getString("status"));
	                dineInReservation.setAssignedStaffId(rs.getString("assigned_staff_id"));;
	                dineInReservation.setCreatedAt(rs.getTimestamp("created_at"));
	                dineInReservation.setUpdatedAt(rs.getTimestamp("updated_at"));
	                
	                dineInReservations.add(dineInReservation);
				}
			}
		}
			return dineInReservations;
	}
	
	public boolean addReservationWithStaff(DineInReservation dineInReservation, String assignedStaffId) throws SQLException {
		String query = "INSERT INTO dinein (reservation_code, customer_name, phone_number, email, reservation_datetime, numberOf_guests, "
				+ "dietary_restrictions, special_occasion, seating_preference, status, assigned_staff_id)"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try(Connection conn = DBConnectionFactory.getConnection();
			PreparedStatement ps = conn.prepareStatement(query)) {
			
			String reservationCode = generateReservationCode();
			
			ps.setString(1, reservationCode);
			ps.setString(2, dineInReservation.getCustomerName());
			ps.setString(3, dineInReservation.getPhoneNumber());
			ps.setString(4, dineInReservation.getEmail());
			ps.setTimestamp(5, Timestamp.valueOf(dineInReservation.getReservationDateTime()));
			ps.setInt(6, dineInReservation.getNumberOfGuests());
			ps.setString(7, dineInReservation.getDietaryRestrictions());
			ps.setString(8, dineInReservation.getSpecialOccasion());
			ps.setString(9, dineInReservation.getSeatingPreference());
			ps.setString(10, dineInReservation.getStatus());
			ps.setString(11, assignedStaffId);
			
			return ps.executeUpdate() > 0;
		} 
	}
	
	public List<DineInReservation> getReservationByStaff(String staffId) throws SQLException {
		List<DineInReservation> dineInReservations = new ArrayList<>();
		String query = "SELECT * FROM dinein WHERE assigned_staff_id = ? ORDER BY reservation_datetime DESC";
		
		try(Connection conn = DBConnectionFactory.getConnection();
			PreparedStatement ps = conn.prepareStatement(query)) {
			
			ps.setString(1, staffId);
			
			try(ResultSet rs = ps.executeQuery()) {
				while(rs.next()) {
					DineInReservation dineInReservation = new DineInReservation(
							rs.getInt("id"),
							rs.getString("reservation_code"),
							rs.getString("customer_name"),
							rs.getString("phone_number"),
							rs.getString("email"),
							rs.getTimestamp("reservation_datetime").toLocalDateTime(),
							rs.getInt("numberOf_guests"),
							rs.getString("dietary_restrictions"),
							rs.getString("special_occasion"),
							rs.getString("seating_preference"),
							rs.getString("status"),
							rs.getString("assigned_staff_id"),
							rs.getTimestamp("created_at"),
		                    rs.getTimestamp("updated_at")
					);
					dineInReservations.add(dineInReservation);
				}
			}
			
			return dineInReservations;
			
		}
		
	}
	
}
