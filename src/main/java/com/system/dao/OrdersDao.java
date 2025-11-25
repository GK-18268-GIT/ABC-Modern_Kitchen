package com.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.system.utils.DBConnectionFactory;
import com.system.model.TakeawayOrders;
import com.system.model.OrderItems;

public class OrdersDao {

	private String generateOrderCode() {
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuilder orderCode = new StringBuilder("TA - ");

		for (int i = 0; i < 6; i++) {
			orderCode.append(chars.charAt(random.nextInt(chars.length())));
		}

		return orderCode.toString();
	}

	// OrdersDao: (New Method)
	public String createTakeawayOrder(TakeawayOrders orders, List<OrderItems> items) throws SQLException {
		Connection conn = null;
		String orderCode = generateOrderCode(); // Generate code once

		// SQL for Order Header
		String headerQuery = "INSERT INTO takeaway_orders (order_code, customer_name, customer_email, customer_phone, total_amount, "
				+ "assigned_staff_id, status) VALUES (?, ?, ?, ?, ?, ?, ?)";

		// SQL for Order Items
		String itemQuery = "INSERT INTO takeaway_order_items (order_code, dish_code, dish_name, size, price, quantity, "
				+ "image_path) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try {
			// 1. Get Connection and Start Transaction
			conn = DBConnectionFactory.getConnection();
			conn.setAutoCommit(false); // <--- START of Transaction

			// --- 2. Insert Order Header ---
			try (PreparedStatement psHeader = conn.prepareStatement(headerQuery)) {
				psHeader.setString(1, orderCode);
				psHeader.setString(2, orders.getCustomerName());
				psHeader.setString(3, orders.getCustomerEmail());
				psHeader.setString(4, orders.getCustomerPhone());
				psHeader.setDouble(5, orders.getTotalAmount());
				psHeader.setString(6, orders.getAssignedStaffId());
				psHeader.setString(7, "PENDING");

				if (psHeader.executeUpdate() == 0) {
					throw new SQLException("Failed to insert order header.");
				}
			}

			// --- 3. Insert Order Items (Batch) ---
			if (items != null && !items.isEmpty()) {
				try (PreparedStatement psItems = conn.prepareStatement(itemQuery)) {
					for (OrderItems item : items) {
						psItems.setString(1, orderCode);
						psItems.setString(2, item.getDishCode());
						psItems.setString(3, item.getDishName());
						psItems.setString(4, item.getSize());
						psItems.setDouble(5, item.getPrice());
						psItems.setInt(6, item.getQuantity());
						psItems.setString(7, item.getImagePath());
						psItems.addBatch();
					}

					int[] results = psItems.executeBatch();
					// Check if all items were inserted successfully
					for (int result : results) {
						if (result <= 0) {
							throw new SQLException("Failed to insert one or more order items.");
						}
					}
				}
			}

			// 4. Commit Transaction
			conn.commit(); // <--- Successful COMMIT
			return orderCode;

		} catch (SQLException e) {
			// 5. Rollback on Error
			if (conn != null) {
				try {
					System.err.print("Transaction is being rolled back");
					conn.rollback(); // <--- ROLLBACK on failure
				} catch (SQLException ex) {
					// Log exception during rollback
					ex.printStackTrace();
				}
			}
			throw e; // Re-throw the original exception

		} finally {
			// 6. Clean Up
			if (conn != null) {
				try {
					conn.setAutoCommit(true); // Reset to default
					conn.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	public List<TakeawayOrders> getOrderByStaff(String staffId) throws SQLException {
		List<TakeawayOrders> orders = new ArrayList<>();

		String query = "SELECT * FROM takeaway_orders WHERE assigned_staff_id = ? ORDER BY order_date DESC";

		try (Connection conn = DBConnectionFactory.getConnection();
				PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, staffId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				TakeawayOrders order = new TakeawayOrders();
				order.setOrderCode(rs.getString("order_code"));
				order.setCustomerName(rs.getString("customer_name"));
				order.setCustomerEmail(rs.getString("customer_email"));
				order.setCustomerPhone(rs.getString("customer_phone"));
				order.setTotalAmount(rs.getDouble("total_amount"));
				order.setStatus(rs.getString("status"));
				order.setOrderDate(rs.getTimestamp("order_date").toLocalDateTime());

				Timestamp readyTime = rs.getTimestamp("ready_time");
				if (readyTime != null) {
					order.setReadyTime(readyTime.toLocalDateTime());
				}

				order.setAssignedStaffId(rs.getString("assigned_staff_id"));
				orders.add(order);

			}

		}
		return orders;

	}

	public boolean updateOrderStatus(int orderId, String status) throws SQLException {
		String query = "UPDATE takeaway_orders SET status = ? WHERE order_id = ?";

		try (Connection conn = DBConnectionFactory.getConnection();
				PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setString(1, status);
			ps.setInt(2, orderId);

			return ps.executeUpdate() > 0;

		}
	}

	public boolean setOrdeready(int orderId) throws SQLException {
		String query = "UPDATE takeaway_orders SET status = 'Ready', ready_time = CURRENT_TIMESTAMP WHERE order_id = ?";

		try (Connection conn = DBConnectionFactory.getConnection();
				PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setInt(1, orderId);

			return ps.executeUpdate() > 0;
		}

	}

	public List<OrderItems> getOrderItems(int OrderId) throws SQLException {
		List<OrderItems> items = new ArrayList<>();

		String query = "SELECT * FROM takeaway_order_items WHERE order_id = ?";

		try (Connection conn = DBConnectionFactory.getConnection();
				PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setInt(1, OrderId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				OrderItems item = new OrderItems();
				item.setDishCode(rs.getString("dish_code"));
				item.setDishName(rs.getString("dish_name"));
				item.setSize(rs.getString("size"));
				item.setPrice(rs.getDouble("price"));
				item.setQuantity(rs.getInt("quantity"));
				item.setImagePath(rs.getString("image_path"));
				items.add(item);

			}
		}
		return items;

	}

	public TakeawayOrders getOrderByCode(String orderCode) throws SQLException {
		String query = "SELECT * FROM takeaway_orders WHERE order_code = ?";

		try (Connection conn = DBConnectionFactory.getConnection();
				PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setString(1, orderCode);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				TakeawayOrders order = new TakeawayOrders();
				order.setOrderId(rs.getInt("order_id"));
				order.setOrderCode(rs.getString("order_code"));
				order.setCustomerName(rs.getString("customer_name"));
				order.setCustomerEmail(rs.getString("customer_email"));
				order.setCustomerPhone(rs.getString("customer_phone"));
				order.setTotalAmount(rs.getDouble("total_amount"));
				order.setStatus(rs.getString("status"));

				Timestamp orderDate = rs.getTimestamp("order_date");
				if (orderDate != null) {
					order.setOrderDate(orderDate.toLocalDateTime());
				}

				Timestamp readyTime = rs.getTimestamp("ready_time");
				if (readyTime != null) {
					order.setReadyTime(readyTime.toLocalDateTime());
				}

				order.setAssignedStaffId(rs.getString("assigned_staff_id"));
				return order;
			}
		}
		return null;
	}

	public boolean updateOrderStatusByCode(String orderCode, String status) throws SQLException {
		String query = "UPDATE takeaway_orders SET status = ?, ready_time = CURRENT_TIMESTAMP WHERE order_code = ?";

		try (Connection conn = DBConnectionFactory.getConnection();
				PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setString(1, status);
			ps.setString(2, orderCode);

			return ps.executeUpdate() > 0;
		}
	}

	public List<OrderItems> getOrderItemsByCode(String orderCode) throws SQLException {
		List<OrderItems> items = new ArrayList<>();

		String query = "SELECT * FROM takeaway_order_items WHERE order_code = ?";

		try (Connection conn = DBConnectionFactory.getConnection();
				PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setString(1, orderCode);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				OrderItems item = new OrderItems();
				item.setDishCode(rs.getString("dish_code"));
				item.setDishName(rs.getString("dish_name"));
				item.setSize(rs.getString("size"));
				item.setPrice(rs.getDouble("price"));
				item.setQuantity(rs.getInt("quantity"));
				item.setImagePath(rs.getString("image_path"));
				items.add(item);
			}
		}
		return items;
	}

}