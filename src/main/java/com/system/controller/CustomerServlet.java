package com.system.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.mindrot.jbcrypt.BCrypt;

import com.system.dao.DishDao;
import com.system.dao.OrdersDao;
import com.system.dao.ProfileDao;
import com.system.dao.AuthenticationDao;
import com.system.dao.DineInReservationDao;
import com.system.model.Dish;
import com.system.model.OrderItems;
import com.system.model.Staff;
import com.system.model.TakeawayOrders;
import com.system.model.Customer;
import com.system.model.DineInReservation;
import com.system.utils.EmailUtil;

@MultipartConfig(maxFileSize = 1024 * 1024 * 5, // 5MB
		maxRequestSize = 1024 * 1024 * 10 // 10MB
)

public class CustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIR = "assets/profiles/";
	private DishDao dishDao;
	private DineInReservationDao dineInReservationDao;
	private ProfileDao profileDao;
	private OrdersDao ordersDao;

	public CustomerServlet() {
		super();

	}

	public void init() {
		dishDao = new DishDao();
		if (dishDao != null) {
			System.out.println("[SUCCESS] DishDao initialized in AdminServlet: " + (dishDao != null));
		} else {
			System.out.println("[ERROR] DishDao initialization failed in CustomerServlet!");
		}

		dineInReservationDao = new DineInReservationDao();
		if (dineInReservationDao != null) {
			System.out.println(
					"[SUCCESS] DineInReservationDao initialized in AdminServlet: " + (dineInReservationDao != null));
		} else {
			System.out.println("[ERROR] DineInReservationDao initialization failed in CustomerServlet!");
		}

		profileDao = new ProfileDao();
		if (dineInReservationDao != null) {
			System.out.println("[SUCCESS] ProfileDao initialized in AdminServlet: " + (profileDao != null));
		} else {
			System.out.println("[ERROR] ProfileDao initialization failed in CustomerServlet!");
		}

		ordersDao = new OrdersDao();
		if (ordersDao != null) {
			System.out.println("[SUCCESS] OrdersDao initialized in AdminServlet: " + (ordersDao != null));
		} else {
			System.out.println("[ERROR] OrdersDao initialization failed in CustomerServlet!");
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		System.out.println("[DEBUG] CustomerServlet doPost - Action parameter: " + action);
		System.out.println("[DEBUG] CustomerServlet doPost - Request URL: " + request.getRequestURL());
		System.out.println("[DEBUG] CustomerServlet doPost - Query String: " + request.getQueryString());

		// Log all parameters for debugging
		java.util.Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			String paramValue = request.getParameter(paramName);
			System.out.println("[DEBUG] Parameter - " + paramName + ": " + paramValue);
		}

		HttpSession session = request.getSession();
		String customerName = (String) session.getAttribute("customerName");

		if (customerName == null) {
			response.sendRedirect(request.getContextPath() + "/LoginServlet?action=login");
			System.out.println("[DEBUG] Customer doesn't exist!");
			return;
		}

		if (action == null) {
			response.sendRedirect(request.getContextPath() + "/DashboardServlet");
			System.out.println("[DEBUG] Invalid action!");
			return;
		}

		try {
			switch (action) {
				case "takeAway":
					takeaway(request, response);
					break;
				case "dineIn":
					showDineInReservationForm(request, response);
					break;
				case "viewReservations":
					viewReservations(request, response);
					break;
				case "editReservation":
					showDineInReservationForm(request, response);
					break;
				case "deleteReservation":
					deleteReservation(request, response);
					break;
				case "myCustomerProfile":
					myCustomerProfile(request, response);
					break;
				case "viewCart":
					viewCart(request, response);
					break;
				case "confirmOrder":
					confirmOrder(request, response);
					break;
				default:
					response.sendRedirect(request.getContextPath() + "/DashboardServlet");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[ERROR] Error processing action: " + e.getMessage());
			response.sendRedirect(request.getContextPath() + "/error.jsp");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		System.out.println("[DEBUG] CustomerServlet doPost - Action parameter: " + action);
		System.out.println("[DEBUG] CustomerServlet doPost - Request URL: " + request.getRequestURL());
		System.out.println("[DEBUG] CustomerServlet doPost - Query String: " + request.getQueryString());

		// Log all parameters for debugging
		java.util.Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			String paramValue = request.getParameter(paramName);
			System.out.println("[DEBUG] Parameter - " + paramName + ": " + paramValue);
		}

		HttpSession session = request.getSession();
		String customerName = (String) session.getAttribute("customerName");

		if (customerName == null) {
			response.sendRedirect(request.getContextPath() + "/LoginServlet?action=login");
			System.out.println("[DEBUG] Customer doesn't exists!");
			return;
		}

		if (action == null) {
			response.sendRedirect(request.getContextPath() + "/DashboardServlet");
			System.out.println("[DEBUG] Invalid action!");
			return;
		}

		try {
			switch (action) {
				case "dineIn":
					addDineInReservation(request, response);
					break;
				case "updateReservation":
					updateReservation(request, response);
					break;
				case "updateCustomerProfile":
					updateCustomerProfile(request, response);
					break;
				case "changeMyPassword":
					changeMyPassword(request, response);
					break;
				case "addToCart":
					addToCart(request, response);
					break;
				case "updateCart":
					updateCart(request, response);
					break;
				case "removeFromCart":
					removeFromCart(request, response);
					break;
				case "confirmTakeawayOrder":
					confirmTakeawayOrder(request, response);
					break;
				default:
					response.sendRedirect(request.getContextPath() + "/DashboardServlet");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[ERROR] Error processing action: " + e.getMessage());
			response.sendRedirect(request.getContextPath() + "/error.jsp");
		}

	}

	private void takeaway(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			List<Dish> dishes = dishDao.getAllDishes();
			List<String> categories = dishDao.getAllCategories();
			request.setAttribute("dishes", dishes);
			request.setAttribute("categories", categories);
			request.getRequestDispatcher("/WEB-INF/customer/takeaway.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Failed to load takeaway menu" + e.getMessage());
			request.getRequestDispatcher("/WEB-INF/customer/takeaway.jsp").forward(request, response);
		}

	}

	private void myCustomerProfile(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		loadCustomerProfileData(request, response);
		request.getRequestDispatcher("/WEB-INF/profile/myCustomerProfile.jsp").forward(request, response);
	}

	private void updateCustomerProfile(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("username");

		if (email == null) {
			response.sendRedirect(request.getContextPath() + "/LoginServlet?action=login");
			return;
		}

		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String phoneNumber = request.getParameter("phoneNumber");

		// Debug logging
		System.out.println("[DEBUG] updateCustomerProfile - Email: " + email);
		System.out.println("[DEBUG] updateCustomerProfile - Name: " + name);
		System.out.println("[DEBUG] updateCustomerProfile - Address: " + address);
		System.out.println("[DEBUG] updateCustomerProfile - Phone: " + phoneNumber);

		String phoneNumberClean = phoneNumber != null ? phoneNumber.replace(" ", "") : null;

		// Server-side phone number validation
		String phonePattern = "^\\+94[0-9]{9}$";
		if (phoneNumberClean != null && !phoneNumberClean.matches(phonePattern)) {
			loadCustomerProfileData(request, response);
			request.setAttribute("error",
					"Invalid phone number format. Must be +94 followed by a space and 9 digits (e.g., +94 771234567)");
			request.getRequestDispatcher("/WEB-INF/profile/myCustomerProfile.jsp").forward(request, response);
			return;
		}

		try {
			String applicationPath = request.getServletContext().getRealPath("");
			String filePath = null;

			Part filePart = request.getPart("profile-picture");
			if (filePart != null && filePart.getSize() > 0) {
				String fileName = filePart.getSubmittedFileName();
				if (fileName != null && !fileName.isEmpty()) {
					String extension = fileName.substring(fileName.lastIndexOf("."));
					fileName = System.currentTimeMillis() + "_" + name.replace(" ", "_") + extension;
					filePath = UPLOAD_DIR + fileName;

					File uploadFile = new File(applicationPath + File.separator + filePath);
					uploadFile.getParentFile().mkdirs();
					filePart.write(uploadFile.getAbsolutePath());

					System.out.println("[DEBUG] Profile picture uploaded to: " + filePath);

					boolean pictureUpdated = profileDao.updateCustomerProfilePicture(email, filePath);

					if (!pictureUpdated) {
						System.out.println("[ERROR] Failed to update profile picture for: " + email);
					} else {
						System.out.println("[DEBUG] Profile picture updated successfully for: " + email);
					}
				}
			} else {
				System.out.println("[DEBUG] No profile picture uploaded or file was empty");
			}

			boolean profileUpdated = profileDao.updateCustomerProfile(email, name, address, phoneNumberClean);

			if (profileUpdated) {
				// Reload the updated profile data
				loadCustomerProfileData(request, response);
				request.setAttribute("success", "Profile updated successfully!");
				System.out.println("[DEBUG] Profile updated successfully for: " + email);
			} else {
				// Still load current data even if update failed
				loadCustomerProfileData(request, response);
				request.setAttribute("error", "Failed to update profile!");
				System.out.println("[ERROR] Failed to update profile for: " + email);
			}

		} catch (Exception e) {
			e.printStackTrace();
			// Load current data on error
			loadCustomerProfileData(request, response);
			request.setAttribute("error", "Error updating profile: " + e.getMessage());
			System.out.println("[ERROR] Error updating profile! " + e.getMessage());
		}

		request.getRequestDispatcher("/WEB-INF/profile/myCustomerProfile.jsp").forward(request, response);
	}

	private void changeMyPassword(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("email");

		if (email == null) {
			response.sendRedirect(request.getContextPath() + "/LoginServlet?action=login");
			System.out.println("[ERROR] Email doesn't existe!");
			return;
		}

		String currentPassword = request.getParameter("current-password");
		String newPassword = request.getParameter("new-password");
		String confirmPassword = request.getParameter("confirm-password");

		System.out.println("[DEBUG] changeMyPassword - Email: " + email);
		System.out.println(
				"[DEBUG] changeMyPassword - Current password provided: " + (currentPassword != null ? "Yes" : "No"));

		try {
			if (!newPassword.equals(confirmPassword)) {
				loadCustomerProfileData(request, response);
				request.setAttribute("error", "new password and confirm password do not match!");
				request.getRequestDispatcher("/WEB-INF/profile/myCustomerProfile.jsp").forward(request, response);
				return;
			}

			String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";
			if (!newPassword.matches(passwordPattern)) {
				loadCustomerProfileData(request, response);
				request.setAttribute("error",
						"Password must contain at least 8 characters, including a number and an uppercase letter!");
				request.getRequestDispatcher("/WEB-INF/profile/myAdminProfile.jsp").forward(request, response);
				return;
			}

			String storeHash = profileDao.getCustomerPasswordHashed(email);
			if (storeHash == null) {
				request.setAttribute("error", "Customer account not found!");
				System.out.println("[ERROR] Customer account not found!");
				request.getRequestDispatcher("/WEB-INF/profile/myCustomerProfile.jsp").forward(request, response);
				return;
			}

			if (BCrypt.checkpw(currentPassword, storeHash)) {
				String newHashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt(12));
				boolean passwordChange = profileDao.changeCustomerPassword(email, newHashedPassword);

				if (passwordChange) {
					loadCustomerProfileData(request, response);
					request.setAttribute("success", "Password change successfully!");
					System.out.println("[DEBUG] Password change successfully!");
				} else {
					loadCustomerProfileData(request, response);
					request.setAttribute("error", "Failed to change password!");
					System.out.println("[ERROR] Failed to change password!");
				}

			} else {
				loadCustomerProfileData(request, response);
				request.setAttribute("error", "Current password is incorrect!");
				System.out.println("[ERROR] Current password is incorrect!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[ERROR] Error changing password " + e.getMessage());
		}

	}

	private void addDineInReservation(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String customerName = (String) request.getSession().getAttribute("customerName");
			String phoneNumber = request.getParameter("phone-number");
			String email = (String) request.getSession().getAttribute("email");

			String dateTimeStr = request.getParameter("reservationDateTime");
			LocalDateTime reservationDateTime = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

			int numberOfGuests = Integer.parseInt(request.getParameter("numberOfGuests"));
			String dietaryRestrictions = request.getParameter("dietaryRestrictions");
			String specialOccasion = request.getParameter("specialOccasion");
			String seatingPreference = request.getParameter("seatingPreference");

			DineInReservation dineInReservation = new DineInReservation(
					customerName, phoneNumber, email, reservationDateTime, numberOfGuests, dietaryRestrictions,
					specialOccasion, seatingPreference);

			AuthenticationDao authenticationDao = new AuthenticationDao();
			List<Staff> allStaffMembers = authenticationDao.getAllStaff();

			if (allStaffMembers.isEmpty()) {
				throw new ServletException("[DEBUG} No staff member available to assign reservation!");
			}

			Random random = new Random();
			Staff assignedStaff = allStaffMembers.get(random.nextInt(allStaffMembers.size()));
			String assignedStaffId = assignedStaff.getStaffId();

			boolean isSuccess = dineInReservationDao.addReservationWithStaff(dineInReservation, assignedStaffId);

			if (isSuccess) {
				try {
					DineInReservation savedReservation = dineInReservationDao
							.getDineInReservationByReservationCode(dineInReservationDao
									.getReservationsByCustomer(customerName, email).get(0).getReservationCode());
					EmailUtil.sendNewReservationNotification(assignedStaff.getEmail(), savedReservation);
					System.out.println("[SUCCESS] New reservation assigned to staff: " + assignedStaff.getEmail());
				} catch (Exception e) {
					System.out.println("[ERROR] Failed to send new reservation notification: " + e.getMessage());
				}

				request.setAttribute("success", "Reservation added successfully!");
				response.sendRedirect(request.getContextPath() + "/CustomerServlet?action=viewReservations");
			} else {
				request.setAttribute("error", "Failed to add reservation");
				request.getRequestDispatcher("/WEB-INF/customer/dineInReservation.jsp").forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Error adding reservation: " + e.getMessage());
			request.getRequestDispatcher("/WEB-INF/customer/dineInReservation.jsp").forward(request, response);
		}
	}

	private void updateReservation(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			String customerName = request.getParameter("customerName");
			String phoneNumber = request.getParameter("phone-number");
			String email = request.getParameter("email");

			String dateTimeStr = request.getParameter("reservationDateTime");
			LocalDateTime reservationDateTime = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

			int numberOfGuests = Integer.parseInt(request.getParameter("numberOfGuests"));
			String dietaryRestrictions = request.getParameter("dietaryRestrictions");
			String specialOccasion = request.getParameter("specialOccasion");
			String seatingPreference = request.getParameter("seatingPreference");
			String status = request.getParameter("status");
			String assignedStaff = request.getParameter("assignedStaff");

			DineInReservation existDineInReservation = dineInReservationDao.getReservationsById(id);
			if (existDineInReservation == null) {
				request.setAttribute("error", "Reservation not found!");
				response.sendRedirect(request.getContextPath() + "/CustomerServlet?action=viewReservations");
				return;

			}

			DineInReservation dineInReservation = new DineInReservation(
					id, existDineInReservation.getReservationCode(), customerName, phoneNumber, email,
					reservationDateTime, numberOfGuests, dietaryRestrictions, specialOccasion, seatingPreference,
					status, assignedStaff, existDineInReservation.getCreatedAt(), null);

			boolean isSuccess = dineInReservationDao.updateReservation(dineInReservation);
			if (isSuccess) {
				request.setAttribute("success", "Reservation updated successfully!");
				System.out.println("Reservation updated successfully!");
			} else {
				request.setAttribute("error", "Failed to update reservation!");
				System.out.println("Failed to update reservation!");
			}

			response.sendRedirect(request.getContextPath() + "/CustomerServlet?action=viewReservations");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Error updating reservation: " + e.getMessage());
			request.getRequestDispatcher("/WEB-INF/customer/editReservation.jsp").forward(request, response);
		}
	}

	private void deleteReservation(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			boolean success = dineInReservationDao.deleteReservation(id);

			if (success) {
				request.setAttribute("success", "Reservation cancelled successfully!");
			} else {
				request.setAttribute("error", "Failed to cancelled reservation");
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Error cancelling reservation: " + e.getMessage());
		}

		response.sendRedirect(request.getContextPath() + "/CustomerServlet?action=viewReservations");
	}

	private void showDineInReservationForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String customerName = (String) session.getAttribute("customerName");
		String email = (String) session.getAttribute("email");

		if (customerName != null) {
			request.setAttribute("customerName", customerName);
		}

		if (email != null) {
			request.setAttribute("email", email);
		}

		request.getRequestDispatcher("/WEB-INF/customer/dineInReservation.jsp").forward(request, response);
	}

	private void viewReservations(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		try {
			HttpSession session = request.getSession();
			String customerName = (String) session.getAttribute("customerName");
			String email = (String) session.getAttribute("email");

			List<DineInReservation> customerReservations = dineInReservationDao.getReservationsByCustomer(customerName,
					email);
			System.out.println("[DEBUG] Found " + customerReservations.size() + " reservations for " + customerName);

			request.setAttribute("reservations", customerReservations);
			request.getRequestDispatcher("/WEB-INF/customer/viewReservations.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Failed to load reservations");
			System.out.println("[ERROR] Failed to load reservations");
			request.getRequestDispatcher("/WEB-INF/dashboard/customerDashboard.jsp").forward(request, response);
		}
	}

	private void loadCustomerProfileData(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			String email = (String) session.getAttribute("email");

			if (email != null) {
				Customer customer = profileDao.getCustomerById(email);
				if (customer != null) {
					request.setAttribute("customerName", customer.getName());
					request.setAttribute("customerEmail", customer.getEmail());
					request.setAttribute("customerAddress", customer.getAddress());
					request.setAttribute("customerPhone", customer.getPhoneNumber());
					request.setAttribute("customerProfilePic", customer.getProfilePicture());

					session.setAttribute("customerName", customer.getName());
					System.out.println("[DEBUG] Load customer profile data for: " + email);

				} else {
					System.out.println("[DEBUG] Failed to load customer profile data for: " + email);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[ERROR] No email in the session! " + e.getMessage());
		}
	}

	private void viewCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/customer/cart.jsp").forward(request, response);
	}

	@SuppressWarnings("unchecked")
	private void addToCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String dishCode = request.getParameter("dishCode");
			String size = request.getParameter("size");

			System.out.println("[DEBUG] addToCart - dishCode: " + dishCode);
			System.out.println("[DEBUG] addToCart - size: " + size);

			if (dishCode == null || dishCode.trim().isEmpty()) {
				System.out.println("[ERROR] addToCart - Invalid dishCode");
				response.sendRedirect(request.getContextPath() + "/CustomerServlet?action=takeAway");
				return;
			}

			DishDao dishDao = new DishDao();
			Dish dish = dishDao.getDishByCode(dishCode);

			if (dish == null) {
				System.out.println("[ERROR] addToCart - Dish not found for code: " + dishCode);
				response.sendRedirect(request.getContextPath() + "/CustomerServlet?action=takeAway");
				return;
			}

			System.out.println("[DEBUG] Found dish: " + dish.getName() + ", Code: " + dish.getDishCode());

			// Determine price based on size
			double price = 0.0;
			if ("Large".equals(size)) {
				price = dish.getPriceL() != null ? dish.getPriceL() : 0.0;
			} else {
				price = dish.getPriceN() != null ? dish.getPriceN() : 0.0;
			}

			HttpSession session = request.getSession();
			List<OrderItems> cart = (List<OrderItems>) session.getAttribute("cart");

			if (cart == null) {
				cart = new ArrayList<>();
			}

			// Check if item already exists in cart
			boolean itemExists = false;
			for (OrderItems item : cart) {
				if (item.getDishCode() != null && item.getDishCode().equals(dishCode) && item.getSize().equals(size)) {
					item.setQuantity(item.getQuantity() + 1);
					itemExists = true;
					System.out.println("[DEBUG] Increased quantity for existing item: " + dishCode);
					break;
				}
			}

			if (!itemExists) {
				OrderItems newItem = new OrderItems(
						dish.getName(),
						size,
						price,
						1,
						dish.getImagePath(),
						dish.getDishCode());
				cart.add(newItem);
				System.out.println("[DEBUG] Added new item to cart: " + dish.getDishCode());
			}

			session.setAttribute("cart", cart);
			System.out.println("[DEBUG] Cart updated - total items: " + cart.size());
			response.sendRedirect(request.getContextPath() + "/CustomerServlet?action=takeAway");

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/CustomerServlet?action=takeAway");
		}
	}

	@SuppressWarnings("unchecked")
	private void updateCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String dishCode = request.getParameter("dishCode");
			String size = request.getParameter("size");
			int quantity = Integer.parseInt(request.getParameter("quantity"));

			System.out.println("[DEBUG] updateCart - dishCode: " + dishCode);
			System.out.println("[DEBUG] updateCart - size: " + size);
			System.out.println("[DEBUG] updateCart - quantity: " + quantity);

			HttpSession session = request.getSession();
			List<OrderItems> cart = (List<OrderItems>) session.getAttribute("cart");

			if (cart != null && dishCode != null && !dishCode.trim().isEmpty()) {
				System.out.println("[DEBUG] Before update - Cart size: " + cart.size());

				if (quantity <= 0) {
					// Remove item if quantity is 0 or less
					boolean removed = cart.removeIf(item -> item.getDishCode() != null &&
							item.getDishCode().equals(dishCode) &&
							item.getSize().equals(size));
					System.out.println("[DEBUG] Item removed due to zero quantity: " + removed);
				} else {
					// Update quantity
					boolean updated = false;
					for (OrderItems item : cart) {
						if (item.getDishCode() != null &&
								item.getDishCode().equals(dishCode) &&
								item.getSize().equals(size)) {
							item.setQuantity(quantity);
							updated = true;
							System.out.println("[DEBUG] Item quantity updated to: " + quantity);
							break;
						}
					}
					if (!updated) {
						System.out.println("[DEBUG] Item not found for update");
					}
				}
				session.setAttribute("cart", cart);
				System.out.println("[DEBUG] After update - Cart size: " + cart.size());
			} else {
				System.out.println("[DEBUG] Cannot update - Invalid parameters");
			}

			response.sendRedirect(request.getContextPath() + "/CustomerServlet?action=viewCart");

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/CustomerServlet?action=viewCart");
		}
	}

	@SuppressWarnings("unchecked")
	private void removeFromCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String dishCode = request.getParameter("dishCode");
			String size = request.getParameter("size");

			System.out.println("[DEBUG] removeFromCart - dishCode: " + dishCode);
			System.out.println("[DEBUG] removeFromCart - size: " + size);

			HttpSession session = request.getSession();
			List<OrderItems> cart = (List<OrderItems>) session.getAttribute("cart");

			if (cart != null && dishCode != null && !dishCode.trim().isEmpty()) {

				System.out.println("[DEBUG] Before removal - Cart size: " + cart.size());

				boolean removed = cart.removeIf(item -> item.getDishCode() != null
						&& item.getDishCode().equals(dishCode) && item.getSize().equals(size));

				System.out.println("[DEBUG] Item removed: " + removed);
				System.out.println("[DEBUG] After removal - Cart size: " + cart.size());

				session.setAttribute("cart", cart);
			} else {
				System.out.println("[DEBUG] Cannot remove - cart: " + (cart != null) +
						", dishCode: " + dishCode +
						", dishCode valid: " + (dishCode != null && !dishCode.trim().isEmpty()));
			}

			response.sendRedirect(request.getContextPath() + "/CustomerServlet?action=viewCart");

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/CustomerServlet?action=viewCart");
		}
	}

	@SuppressWarnings("unchecked")
	private void confirmTakeawayOrder(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		List<OrderItems> cart = (List<OrderItems>) session.getAttribute("cart");

		System.out.println("[DEBUG] confirmTakeawayOrder - Cart: " + (cart != null ? cart.size() + " items" : "null"));

		if (cart == null || cart.isEmpty()) {
			request.setAttribute("error", "Your cart is empty");
			request.getRequestDispatcher("/WEB-INF/customer/cart.jsp").forward(request, response);
			return;
		}

		for (OrderItems item : cart) {
			if (item.getDishCode() == null || item.getDishCode().trim().isEmpty()) {
				System.out.println("[DEBUG] Cart item - Name: " + item.getDishName() +
						", Code: " + item.getDishCode() +
						", Size: " + item.getSize() +
						", Quantity: " + item.getQuantity());
				System.out.println("[ERROR] Invalid item in cart - missing dishCode");
				request.setAttribute("error", "Invalid item in cart. Please try adding items again.");
				request.getRequestDispatcher("/WEB-INF/customer/cart.jsp").forward(request, response);
				return;
			}
		}

		try {
			String customerName = (String) session.getAttribute("customerName");
			String customerEmail = (String) session.getAttribute("email");
			String customerPhone = request.getParameter("phoneNumber");

			// Validate phone number
			String phonePattern = "^\\+94[0-9]{9}$";
			String phoneNumberClean = customerPhone != null ? customerPhone.replace(" ", "") : null;

			if (phoneNumberClean == null || !phoneNumberClean.matches(phonePattern)) {
				request.setAttribute("error",
						"Invalid phone number format. Must be +94 followed by 9 digits (e.g., +94771234567)");
				request.getRequestDispatcher("/WEB-INF/customer/cart.jsp").forward(request, response);
				return;
			}

			// Calculate total amount
			double totalAmount = cart.stream().mapToDouble(OrderItems::getTotalPrice).sum();

			// Assign random staff member
			AuthenticationDao authenticationDao = new AuthenticationDao();
			List<Staff> allStaffMembers = authenticationDao.getAllStaff();

			if (allStaffMembers.isEmpty()) {
				throw new ServletException("No staff member available to assign order!");
			}

			Random random = new Random();
			Staff assignedStaff = allStaffMembers.get(random.nextInt(allStaffMembers.size()));
			String assignedStaffId = assignedStaff.getStaffId();

			// Create TakeawayOrders object
			TakeawayOrders order = new TakeawayOrders();
			order.setCustomerName(customerName);
			order.setCustomerEmail(customerEmail);
			order.setCustomerPhone(phoneNumberClean);
			order.setTotalAmount(totalAmount);
			order.setAssignedStaffId(assignedStaffId);
			order.setOrderDate(java.time.LocalDateTime.now());

			// Convert CartItems to OrderItems
			List<OrderItems> orderItems = new ArrayList<>();
			for (OrderItems cartItem : cart) {
				OrderItems orderItem = new OrderItems();
				orderItem.setDishCode(cartItem.getDishCode());
				orderItem.setDishName(cartItem.getDishName());
				orderItem.setSize(cartItem.getSize());
				orderItem.setPrice(cartItem.getPrice());
				orderItem.setQuantity(cartItem.getQuantity());
				orderItem.setImagePath(cartItem.getImagePath());
				orderItem.setDishCode(cartItem.getDishCode());
				orderItems.add(orderItem);
			}

			// Create order using OrdersDao
			OrdersDao ordersDao = new OrdersDao();
			String orderCode = ordersDao.createTakeawayOrder(order, orderItems);

			if (orderCode != null) {
				// Set order code for email
				order.setOrderCode(orderCode);

				// Send email notification to staff
				try {
					String scheme = request.getScheme();
					String serverName = request.getServerName();
					int serverPort = request.getServerPort();
					String contextPath = request.getContextPath();

					StringBuilder baseUrlBuilder = new StringBuilder();
					baseUrlBuilder.append(scheme).append("://").append(serverName);
					if (!(("http".equalsIgnoreCase(scheme) && serverPort == 80)
							|| ("https".equalsIgnoreCase(scheme) && serverPort == 443))) {
						baseUrlBuilder.append(":" + serverPort);
					}
					if (contextPath != null && !contextPath.trim().isEmpty()) {
						if (!contextPath.startsWith("/")) {
							baseUrlBuilder.append('/');
						}
						baseUrlBuilder.append(contextPath);
					}

					String baseUrl = baseUrlBuilder.toString();

					EmailUtil.sendTakeawayOrderNotification(assignedStaff.getEmail(), order, cart, baseUrl);
					System.out.println(
							"[SUCCESS] Takeaway order notification sent to staff: " + assignedStaff.getEmail());
				} catch (Exception e) {
					System.out.println("[ERROR] Failed to send order notification: " + e.getMessage());
				}

				// Clear cart
				session.removeAttribute("cart");
				request.setAttribute("success", "Order placed successfully! Your order code is: " + orderCode);
				request.setAttribute("orderCode", orderCode);
				request.getRequestDispatcher("/WEB-INF/customer/orderConfirmation.jsp").forward(request, response);
			} else {
				request.setAttribute("error", "Failed to place order. Please try again.");
				request.getRequestDispatcher("/WEB-INF/customer/cart.jsp").forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Error placing order: " + e.getMessage());
			request.getRequestDispatcher("/WEB-INF/customer/cart.jsp").forward(request, response);
		}
	}

	private void confirmOrder(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		String orderCode = request.getParameter("orderCode");

		if (orderCode == null || orderCode.trim().isEmpty()) {
			request.setAttribute("error", "Invalid order code");
			request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
			return;
		}

		orderCode = orderCode.trim();

		TakeawayOrders order = ordersDao.getOrderByCode(orderCode);

		if (order == null) {
			request.setAttribute("error", "Order not found");
			request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
			return;
		}

		if ("READY".equalsIgnoreCase(order.getStatus()) || "CONFIRMED".equalsIgnoreCase(order.getStatus())) {
			request.setAttribute("message", "Order " + orderCode + " has already been confirmed as ready.");
			request.getRequestDispatcher("/WEB-INF/orderConfirmationSuccess.jsp").forward(request, response);
			return;
		}

		boolean isSuccess = ordersDao.updateOrderStatusByCode(orderCode, "READY");

		if (isSuccess) {
			List<OrderItems> items = ordersDao.getOrderItemsByCode(orderCode);

			order = ordersDao.getOrderByCode(orderCode);

			try {
				EmailUtil.sendOrderReadyNotification(order.getCustomerEmail(), order, items);
				System.out.println("[SUCCESS] Order ready notification sent to customer: " + order.getCustomerEmail());
			} catch (Exception e) {
				System.out.println("[ERROR] Failed to send order ready notification: " + e.getMessage());
			}

			request.setAttribute("success", "Order " + orderCode + " has been confirmed as ready!");
			request.setAttribute("orderCode", orderCode);
			request.getRequestDispatcher("/WEB-INF/customer/orderConfirmationSuccess.jsp").forward(request, response);
		} else {
			request.setAttribute("error", "Failed to confirm order. Please try again.");
			request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
		}

	}

}
