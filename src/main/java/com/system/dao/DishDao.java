package com.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.system.utils.DBConnectionFactory;

import com.system.model.Dish;

public class DishDao {
	public boolean addDish(Dish dish) throws SQLException {
		String query = "INSERT INTO dishes (dish_code, category, name, size, normal_price, large_price, image_path, is_available, created_at, updated_at)"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (Connection conn = DBConnectionFactory.getConnection();
				PreparedStatement ps = conn.prepareStatement(query)) {

			String dishCode = generateDishCode(conn, dish.getCategory());

			ps.setString(1, dishCode);
			ps.setString(2, dish.getCategory());
			ps.setString(3, dish.getName());
			ps.setString(4, dish.getSize());

			if (dish.getPriceN() != null) {
				ps.setDouble(5, dish.getPriceN());
			} else {
				ps.setNull(5, Types.DECIMAL);
			}

			if (dish.getPriceL() != null) {
				ps.setDouble(6, dish.getPriceL());
			} else {
				ps.setNull(6, Types.DECIMAL);
			}

			ps.setString(7, dish.getImagePath());
			ps.setBoolean(8, dish.getAvailable());
			ps.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
			ps.setTimestamp(10, new Timestamp(System.currentTimeMillis()));

			return ps.executeUpdate() > 0;
		}
	}

	public boolean updateDish(Dish dish) throws SQLException {
		String query = "UPDATE dishes SET category = ?, name = ?, size = ?, normal_price = ?, large_price = ?, image_path = ?, "
				+ "is_available = ?, updated_at = ? WHERE id = ?";

		try (Connection conn = DBConnectionFactory.getConnection();
				PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setString(1, dish.getCategory());
			ps.setString(2, dish.getName());
			ps.setString(3, dish.getSize());

			if (dish.getPriceN() != null) {
				ps.setDouble(4, dish.getPriceN());
			} else {
				ps.setNull(4, Types.DECIMAL);
			}

			if (dish.getPriceL() != null) {
				ps.setDouble(5, dish.getPriceL());
			} else {
				ps.setNull(5, Types.DECIMAL);
			}

			ps.setString(6, dish.getImagePath());
			ps.setBoolean(7, dish.getAvailable());
			ps.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
			ps.setInt(9, dish.getId());

			return ps.executeUpdate() > 0;
		}
	}

	public boolean deleteDish(int id) throws SQLException {
		String query = "UPDATE dishes SET is_available = false, updated_at = ? WHERE id = ? ";

		try (Connection conn = DBConnectionFactory.getConnection();
				PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			ps.setInt(2, id);

			return ps.executeUpdate() > 0;
		}
	}

	public List<Dish> getAllDishes() throws SQLException {
		List<Dish> dishes = new ArrayList<>();
		String query = "SELECT * FROM dishes ORDER BY category, name";

		try (Connection conn = DBConnectionFactory.getConnection();
				PreparedStatement ps = conn.prepareStatement(query);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				Dish dish = new Dish();
				dish.setId(rs.getInt("id"));
				dish.setDishCode(rs.getString("dish_code"));
				dish.setCategory(rs.getString("category"));
				dish.setName(rs.getString("name"));
				dish.setSize(rs.getString("size"));

				Double priceN = rs.getDouble("normal_price");
				if (rs.wasNull())
					priceN = null;
				dish.setPriceN(priceN);

				Double priceL = rs.getDouble("large_price");
				if (rs.wasNull())
					priceL = null;
				dish.setPriceL(priceL);

				dish.setImagePath(rs.getString("image_path"));
				dish.setAvailable(rs.getBoolean("is_available"));
				dish.setCreatedAt(rs.getTimestamp("created_at"));
				dish.setUpdatedAt(rs.getTimestamp("updated_at"));
				dishes.add(dish);
			}
		}
		return dishes;
	}

	public List<String> getAllCategories() throws SQLException {
		List<String> categories = new ArrayList<>();
		String query = "SELECT DISTINCT category FROM dishes WHERE is_available = true ORDER BY category";

		try (Connection conn = DBConnectionFactory.getConnection();
				PreparedStatement ps = conn.prepareStatement(query);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				categories.add(rs.getString("category"));
			}
		}

		return categories;
	}

	public Dish getDishById(int id) throws SQLException {
		String query = "SELECT * FROM dishes WHERE id = ?";

		try (Connection conn = DBConnectionFactory.getConnection();
				PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					Dish dish = new Dish();
					dish.setId(rs.getInt("id"));
					dish.setDishCode(rs.getString("dish_code"));
					dish.setCategory(rs.getString("category"));
					dish.setName(rs.getString("name"));
					dish.setSize(rs.getString("size"));

					Double priceN = rs.getDouble("normal_price");
					if (rs.wasNull())
						priceN = null;
					dish.setPriceN(priceN);

					Double priceL = rs.getDouble("large_price");
					if (rs.wasNull())
						priceL = null;
					dish.setPriceL(priceL);

					dish.setImagePath(rs.getString("image_path"));
					dish.setAvailable(rs.getBoolean("is_available"));
					dish.setCreatedAt(rs.getTimestamp("created_at"));
					dish.setUpdatedAt(rs.getTimestamp("updated_at"));
					return dish;
				}
			}
		}
		return null;
	}

	private String generateDishCode(Connection conn, String category) throws SQLException {
		String prefix = category.substring(0, Math.min(category.length(), 3)).toUpperCase();

		String query = "SELECT dish_code FROM dishes WHERE dish_code LIKE ? ORDER BY dish_code DESC LIMIT 1";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, prefix + "-%");

			try (ResultSet rs = ps.executeQuery()) {
				int nextNumber = 1;
				if (rs.next()) {
					String lastDishCode = rs.getString("dish_code");
					if (lastDishCode != null && lastDishCode.startsWith(prefix + "-")) {
						try {
							// Extract the number part after the prefix and dash (e.g., "SOU-001" -> "001")
							String numberPart = lastDishCode.substring(prefix.length() + 1);
							nextNumber = Integer.parseInt(numberPart) + 1;
						} catch (NumberFormatException e) {
							System.out.println(
									"[WARNING] Invalid dish code format: " + lastDishCode + ". Starting from 1.");
							nextNumber = 1;
						}
					}
				}

				// Generate code and check if it already exists (handle race conditions)
				String newDishCode;
				int maxAttempts = 100; // Prevent infinite loop
				int attempts = 0;

				do {
					newDishCode = String.format("%s-%03d", prefix, nextNumber);

					// Check if this code already exists
					String checkQuery = "SELECT COUNT(*) FROM dishes WHERE dish_code = ?";
					try (PreparedStatement checkPs = conn.prepareStatement(checkQuery)) {
						checkPs.setString(1, newDishCode);
						try (ResultSet checkRs = checkPs.executeQuery()) {
							if (checkRs.next() && checkRs.getInt(1) > 0) {
								// Code exists, try next number
								nextNumber++;
								attempts++;
								if (attempts >= maxAttempts) {
									throw new SQLException(
											"Unable to generate unique dish code after " + maxAttempts + " attempts");
								}
								continue;
							}
						}
					}
					break; // Found unique code
				} while (true);

				return newDishCode;
			}
		}
	}

	public Dish getDishByCode(String dishCode) throws SQLException {
		String query = "SELECT * FROM dishes WHERE dish_code = ?";

		try (Connection conn = DBConnectionFactory.getConnection();
				PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setString(1, dishCode);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					Dish dish = new Dish();
					dish.setId(rs.getInt("id"));
					dish.setCategory(rs.getString("category"));
					dish.setName(rs.getString("name"));
					dish.setSize(rs.getString("size"));

					// Handle potential null values for prices
					Double normalPrice = rs.getDouble("normal_price");
					if (rs.wasNull())
						normalPrice = null;
					dish.setPriceN(normalPrice);

					Double largePrice = rs.getDouble("large_price");
					if (rs.wasNull())
						largePrice = null;
					dish.setPriceL(largePrice);

					dish.setImagePath(rs.getString("image_path"));
					dish.setAvailable(rs.getBoolean("is_available"));
					dish.setCreatedAt(rs.getTimestamp("created_at"));
					dish.setUpdatedAt(rs.getTimestamp("updated_at"));
					return dish;
				}
			}
		}
		return null;
	}

}
