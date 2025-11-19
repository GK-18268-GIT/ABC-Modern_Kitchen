package com.system.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import com.system.dao.ProfileDao;
import com.system.dao.AuthenticationDao;
import com.system.dao.DineInReservationDao;
import com.system.model.Dish;
import com.system.model.Staff;
import com.system.utils.EmailUtil;
import com.system.model.Customer;
import com.system.model.DineInReservation;

@MultipartConfig(
	    maxFileSize = 1024 * 1024 * 5, // 5MB
	    maxRequestSize = 1024 * 1024 * 10 // 10MB
	)

public class CustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIR = "assets/profiles/";
	private DishDao dishDao;
	private DineInReservationDao dineInReservationDao;
	private ProfileDao profileDao;

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
        	System.out.println("[SUCCESS] DineInReservationDao initialized in AdminServlet: " + (dineInReservationDao != null));
        } else {
        	System.out.println("[ERROR] DineInReservationDao initialization failed in CustomerServlet!");
        }
        
        profileDao = new ProfileDao();
        if (dineInReservationDao != null) {
        	System.out.println("[SUCCESS] ProfileDao initialized in AdminServlet: " + (profileDao != null));
        } else {
        	System.out.println("[ERROR] ProfileDao initialization failed in CustomerServlet!");
        }
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        
        if(customerName == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet?action=login");
            System.out.println("[DEBUG] Customer doesn't exist!");
            return;
        }
        
        if(action == null) {
            response.sendRedirect(request.getContextPath() + "/DashboardServlet");
            System.out.println("[DEBUG] Invalid action!");
            return;
        }
        
        try {
        	switch(action) {
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
            default:
                response.sendRedirect(request.getContextPath() + "/DashboardServlet");
            }
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("[ERROR] Error processing action: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		
		if(customerName == null) {
			response.sendRedirect(request.getContextPath() + "/LoginServlet?action=login");
			System.out.println("[DEBUG] Customer doesn't exists!");
			return;
		}
		
		if(action == null) {
			response.sendRedirect(request.getContextPath() + "/DashboardServlet");
			System.out.println("[DEBUG] Invalid action!");
			return;
		}
		
		
		try {
			switch(action) {
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
			default:
				response.sendRedirect(request.getContextPath() + "/DashboardServlet");
			}
		} catch(Exception e) {
			e.printStackTrace();
            System.out.println("[ERROR] Error processing action: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/error.jsp");
		}
		
	}
	
	private void takeaway(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			List<Dish> dishes = dishDao.getAllDishes();
			List<String> categories = dishDao.getAllCategories();
			request.setAttribute("dishes", dishes);
			request.setAttribute("categories", categories);
			request.getRequestDispatcher("/WEB-INF/customer/takeaway.jsp").forward(request, response);
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Failed to load takeaway menu" + e.getMessage());
			request.getRequestDispatcher("/WEB-INF/customer/takeaway.jsp").forward(request, response);
		}
		
	}
	
	private void myCustomerProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		loadCustomerProfileData(request, response);
		request.getRequestDispatcher("/WEB-INF/profile/myCustomerProfile.jsp").forward(request, response);
	}
	
	private void updateCustomerProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
	    String email = (String) session.getAttribute("username");
	    
	    if(email == null) {
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
	        request.setAttribute("error", "Invalid phone number format. Must be +94 followed by a space and 9 digits (e.g., +94 771234567)");
	        request.getRequestDispatcher("/WEB-INF/profile/myCustomerProfile.jsp").forward(request, response);
	        return;
	    }
	    
	    try {
	        String applicationPath = request.getServletContext().getRealPath("");
	        String filePath = null;
	        
	        Part filePart = request.getPart("profile-picture");
	        if(filePart != null && filePart.getSize() > 0) {
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
	                
	                if(!pictureUpdated) {
	                    System.out.println("[ERROR] Failed to update profile picture for: " + email);
	                } else {
	                    System.out.println("[DEBUG] Profile picture updated successfully for: " + email);
	                }
	            }
	        } else {
	            System.out.println("[DEBUG] No profile picture uploaded or file was empty");
	        }
	        
	        boolean profileUpdated = profileDao.updateCustomerProfile(email, name, address, phoneNumberClean);
	        
	        if(profileUpdated) {
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
	        
	    } catch(Exception e) {
	        e.printStackTrace();
	        // Load current data on error
	        loadCustomerProfileData(request, response);
	        request.setAttribute("error", "Error updating profile: " + e.getMessage());
	        System.out.println("[ERROR] Error updating profile! " + e.getMessage());
	    }
	        
	    request.getRequestDispatcher("/WEB-INF/profile/myCustomerProfile.jsp").forward(request, response);
	}
	
	private void changeMyPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("email");
		
		if(email == null) {
			response.sendRedirect(request.getContextPath() + "/LoginServlet?action=login");
			System.out.println("[ERROR] Email doesn't existe!");
			return;
		}
		
		String currentPassword = request.getParameter("current-password");
		String newPassword = request.getParameter("new-password");
		String confirmPassword = request.getParameter("confirm-password");
		
		System.out.println("[DEBUG] changeMyPassword - Email: " + email);
        System.out.println("[DEBUG] changeMyPassword - Current password provided: " + (currentPassword != null ? "Yes" : "No"));
        
        try {
        	if(!newPassword.equals(confirmPassword)) {
        		loadCustomerProfileData(request, response);
        		request.setAttribute("error", "new password and confirm password do not match!");
        		request.getRequestDispatcher("/WEB-INF/profile/myCustomerProfile.jsp").forward(request, response);
        		return;
        	}
        	
        	String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";
			if(!newPassword.matches(passwordPattern)) {
				loadCustomerProfileData(request, response);
				request.setAttribute("error", "Password must contain at least 8 characters, including a number and an uppercase letter!");
				request.getRequestDispatcher("/WEB-INF/profile/myAdminProfile.jsp").forward(request, response);
				return;	
			}
			
			String storeHash = profileDao.getCustomerPasswordHashed(email);
			if(storeHash == null) {
				request.setAttribute("error", "Customer account not found!");
				System.out.println("[ERROR] Customer account not found!");
				request.getRequestDispatcher("/WEB-INF/profile/myCustomerProfile.jsp").forward(request, response);
				return;	
			}
			
			if(BCrypt.checkpw(currentPassword, storeHash)) {
				String newHashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt(12));
				boolean passwordChange = profileDao.changeCustomerPassword(email, newHashedPassword);
				
				if(passwordChange) {
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
        	
        } catch(Exception e) {
        	e.printStackTrace();
			System.out.println("[ERROR] Error changing password " + e.getMessage());
        }
		
	}
	
	private void addDineInReservation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String customerName = (String) request.getSession().getAttribute("customerName");
			String phoneNumber = request.getParameter("phone-number");
			String email = (String) request.getSession().getAttribute("email");
			
			String dateTimeStr = request.getParameter("reservationDateTime");
			LocalDateTime reservationDateTime = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
			
			int numberOfGuests = Integer.parseInt(request.getParameter("numberOfGuests"));
			String dietaryRestrictions = request.getParameter("dietaryRestrictions");
			String specialOccasion= request.getParameter("specialOccasion");
			String seatingPreference= request.getParameter("seatingPreference");
			
			DineInReservation dineInReservation = new DineInReservation(
				customerName, phoneNumber, email, reservationDateTime, numberOfGuests, dietaryRestrictions, specialOccasion, seatingPreference);
			
			
			AuthenticationDao authenticationDao = new AuthenticationDao();
			List<Staff> allStaffMembers = authenticationDao.getAllStaff();
			
			if(allStaffMembers.isEmpty()) {
				throw new ServletException("[DEBUG} No staff member available to assign reservation!");
			}
			
			Random random = new Random();
			Staff assignedStaff = allStaffMembers.get(random.nextInt(allStaffMembers.size()));
			String assignedStaffId = assignedStaff.getStaffId();
	
			boolean isSuccess = dineInReservationDao.addReservationWithStaff(dineInReservation, assignedStaffId);
			
			if (isSuccess) {
				try {
					DineInReservation savedReservation = dineInReservationDao.getDineInReservationByReservationCode
							(dineInReservationDao.getReservationsByCustomer(customerName, email).get(0).getReservationCode());
					EmailUtil.sendNewReservationNotification(assignedStaff.getEmail(), savedReservation);
					System.out.println("[SUCCESS] New reservation assigned to staff: " + assignedStaff.getEmail());
				} catch(Exception e) {
					System.out.println("[ERROR] Failed to send new reservation notification: " + e.getMessage());
				}
				
                request.setAttribute("success", "Reservation added successfully!");
                response.sendRedirect(request.getContextPath() + "/CustomerServlet?action=viewReservations");
            } else {
                request.setAttribute("error", "Failed to add reservation");
                request.getRequestDispatcher("/WEB-INF/customer/dineInReservation.jsp").forward(request, response);
            }
			
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Error adding reservation: " + e.getMessage());
			request.getRequestDispatcher("/WEB-INF/customer/dineInReservation.jsp").forward(request, response);
		}
	}
	
	private void updateReservation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			String customerName = request.getParameter("customerName");
			String phoneNumber = request.getParameter("phone-number");
			String email = request.getParameter("email");
			
			String dateTimeStr = request.getParameter("reservationDateTime");
			LocalDateTime reservationDateTime = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
			
			int numberOfGuests = Integer.parseInt(request.getParameter("numberOfGuests"));
			String dietaryRestrictions = request.getParameter("dietaryRestrictions");
			String specialOccasion= request.getParameter("specialOccasion");
			String seatingPreference= request.getParameter("seatingPreference");
			String status = request.getParameter("status");
			String assignedStaff = request.getParameter("assignedStaff");
			
			DineInReservation existDineInReservation = dineInReservationDao.getReservationsById(id);
			if(existDineInReservation == null) {
				request.setAttribute("error", "Reservation not found!");
				response.sendRedirect(request.getContextPath() + "/CustomerServlet?action=viewReservations");
				return;

			}
			
			DineInReservation dineInReservation = new DineInReservation(
				id, existDineInReservation.getReservationCode(), customerName, phoneNumber, email, 
				reservationDateTime, numberOfGuests, dietaryRestrictions, specialOccasion, seatingPreference, status, assignedStaff, existDineInReservation.getCreatedAt(), null);
			
			boolean isSuccess = dineInReservationDao.updateReservation(dineInReservation);
			if(isSuccess) {
				request.setAttribute("success", "Reservation updated successfully!");
				System.out.println("Reservation updated successfully!");
			} else {
				request.setAttribute("error", "Failed to update reservation!");
				System.out.println("Failed to update reservation!");
			}
			
			 response.sendRedirect(request.getContextPath() + "/CustomerServlet?action=viewReservations");
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Error updating reservation: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/customer/editReservation.jsp").forward(request, response);
		}
	}
	
	private void deleteReservation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
	
	private void showDineInReservationForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String customerName = (String) session.getAttribute("customerName");
		String email = (String) session.getAttribute("email");
		
		if(customerName != null) {
			request.setAttribute("customerName", customerName);
		}
		
		if(email != null) {
			request.setAttribute("email", email);
		}
		
		request.getRequestDispatcher("/WEB-INF/customer/dineInReservation.jsp").forward(request, response);		
	}
	
	private void viewReservations(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		try {
			HttpSession session = request.getSession();
			String customerName = (String) session.getAttribute("customerName");
			String email = (String) session.getAttribute("email");
			
			List<DineInReservation> customerReservations = dineInReservationDao.getReservationsByCustomer(customerName, email);
			System.out.println("[DEBUG] Found " + customerReservations.size() + " reservations for " + customerName);
			
			request.setAttribute("reservations", customerReservations);
			request.getRequestDispatcher("/WEB-INF/customer/viewReservations.jsp").forward(request, response);
			
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Failed to load reservations");
			System.out.println("[ERROR] Failed to load reservations");
			request.getRequestDispatcher("/WEB-INF/dashboard/customerDashboard.jsp").forward(request, response);
		}
	}
	
	private void loadCustomerProfileData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			String email = (String) session.getAttribute("email");
			
			if(email != null) {
				Customer customer = profileDao.getCustomerById(email);
					if(customer != null) {
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
			
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("[ERROR] No email in the session! " + e.getMessage());
		}
	}

}
