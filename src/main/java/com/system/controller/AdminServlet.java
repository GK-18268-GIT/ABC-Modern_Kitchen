package com.system.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.mindrot.jbcrypt.BCrypt;

import com.system.dao.ProfileDao;
import com.system.dao.AuthenticationDao;
import com.system.model.Admin;
import com.system.model.Customer;
import com.system.model.Staff;

@MultipartConfig(
maxFileSize = 1024 * 1024 * 5, // 5MB max file size
maxRequestSize  = 1024 * 1024 * 10 //10MB max request size
)

public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIR = "assets/profiles/";
	private ProfileDao profileDao;
	private AuthenticationDao authenticationDao;

    public AdminServlet() {
        super();
        
    }
    
    public void init() {
    	profileDao = new ProfileDao();
        if (profileDao != null) {
            System.out.println("[SUCCESS] ProfileDao initialized in AdminServlet: " + (profileDao != null));
        } else {
            System.out.println("[ERROR] ProfileDao initialization failed in AdminServlet!");
        }
        
        authenticationDao = new AuthenticationDao();
        if (authenticationDao != null) {
        	System.out.println("[SUCCESS] AuthenticationDao initialized in AdminServlet: " + (authenticationDao != null));
        } else {
        	System.out.println("[ERROR] AuthenticationDao initialization failed in AdminServlet!");
        }
    }
 

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		HttpSession session = request.getSession();
		String adminName = (String) session.getAttribute("adminName");
		
		if(adminName == null) {
			response.sendRedirect(request.getContextPath() + "/LoginServlet?action=login");
			System.out.println("[DEBUG] Admin doesn't exists!");
			return;
		}
		
		if(action == null) {
			response.sendRedirect(request.getContextPath() + "/DashboardServlet");
			System.out.println("[DEBUG] Invalid action!!");
			return;
		}
		
		switch(action) {
		case "manageStaff":
			manageStaff(request, response);
			break;
		case "myAdminProfile":
			myAdminProfile(request, response);
			break;
		case "manageUsers":
			listUsers(request, response);
			break;
		default:
			response.sendRedirect(request.getContextPath() + "/DashboardServlet");
			
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		HttpSession session = request.getSession();
		String adminName = (String) session.getAttribute("adminName");
		
		if(action == null) {
			response.sendRedirect(request.getContextPath() + "/DashboardServlet");
			System.out.println("[ERROR] Invalid action parameter!");
            return;
		}
		
		if(adminName == null) {
			response.sendRedirect(request.getContextPath() + "/LoginServlet?action=login");
			System.out.println("[ERROR] Admin account not found!");
			return;
		}
		
		try {
			switch(action) {
			case "updateAdminProfile":
				updateAdminProfile(request, response);
				break;
			case "changeMyPassword":
				changeMyPassword(request, response);
				break;
			default:
				response.sendRedirect(request.getContextPath() + "/DashboardServlet");
			}
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/profile/myAdminProfile.jsp").forward(request, response);
		}
		
	}
	
	private void manageStaff(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/admin/staffManagement.jsp").forward(request, response);
	
	}
	
	private void myAdminProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		loadAdminProfileData(request, response);
		request.getRequestDispatcher("/WEB-INF/profile/myAdminProfile.jsp").forward(request, response);
	}
	
	private void loadAdminProfileData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			String email = (String) session.getAttribute("username");
			
			if(email != null) {
				Admin admin = profileDao.getAdminById(email);
				if(admin != null) {
					request.setAttribute("adminName", admin.getName());
					request.setAttribute("adminEmail", admin.getEmail());
					request.setAttribute("adminAddress", admin.getAddress());
					request.setAttribute("adminPhone", admin.getPhoneNumber());
					request.setAttribute("adminProfilePic", admin.getProfilePicture());
					
					session.setAttribute("adminName", admin.getName());
					System.out.println("[DEBUG] Load admin profile data for: " + email);
				} else {
					System.out.println("[DEBUG] Failed to load admin profile data for: " + email);
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("[ERROR] No email in the session! " + e.getMessage());
		}
			
	}
	
	private void updateAdminProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
	    System.out.println("[DEBUG] updateAdminProfile - Email: " + email);
	    System.out.println("[DEBUG] updateAdminProfile - Name: " + name);
	    System.out.println("[DEBUG] updateAdminProfile - Address: " + address);
	    System.out.println("[DEBUG] updateAdminProfile - Phone: " + phoneNumber);
	    
	    // Remove space from phone number for server-side validation
	    String phoneNumberClean = phoneNumber != null ? phoneNumber.replace(" ", "") : null;
	    
	    // Server-side phone number validation (without space)
	    String phonePattern = "^\\+94[0-9]{9}$";
	    if (phoneNumberClean != null && !phoneNumberClean.matches(phonePattern)) {
	        loadAdminProfileData(request, response);
	        request.setAttribute("error", "Invalid phone number format. Must be +94 followed by a space and 9 digits (e.g., +94 771234567)");
	        request.getRequestDispatcher("/WEB-INF/profile/myAdminProfile.jsp").forward(request, response);
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
	                
	                boolean pictureUpdated = profileDao.updateAdminProfilePicture(email, filePath);
	                
	                if(!pictureUpdated) {
	                    System.out.println("[ERROR] Failed to update profile picture for: " + email);
	                } else {
	                    System.out.println("[DEBUG] Profile picture updated successfully for: " + email);
	                }
	            }
	        } else {
	            System.out.println("[DEBUG] No profile picture uploaded or file was empty");
	        }
	        
	        // Use the cleaned phone number (without space) for database storage
	        boolean profileUpdated = profileDao.updateAdminProfile(email, name, address, phoneNumberClean);
	        
	        if(profileUpdated) {
	            // Reload the updated profile data
	            loadAdminProfileData(request, response);
	            request.setAttribute("success", "Profile updated successfully!");
	            System.out.println("[DEBUG] Profile updated successfully for: " + email);
	        } else {
	            // Still load current data even if update failed
	            loadAdminProfileData(request, response);
	            request.setAttribute("error", "Failed to update profile!");
	            System.out.println("[ERROR] Failed to update profile for: " + email);
	        }
	        
	    } catch(Exception e) {
	        e.printStackTrace();
	        // Load current data on error
	        loadAdminProfileData(request, response);
	        request.setAttribute("error", "Error updating profile: " + e.getMessage());
	        System.out.println("[ERROR] Error updating profile! " + e.getMessage());
	    }
	        
	    request.getRequestDispatcher("/WEB-INF/profile/myAdminProfile.jsp").forward(request, response);
	}
	
	private void changeMyPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("username");
		
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
				loadAdminProfileData(request, response);
				request.setAttribute("error", "new password and confirm password do not match!");
				request.getRequestDispatcher("/WEB-INF/profile/myAdminProfile.jsp").forward(request, response);
				return;
			}
			
			String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";
			if(!newPassword.matches(passwordPattern)) {
				loadAdminProfileData(request, response);
				request.setAttribute("error", "Password must contain at least 8 characters, including a number and an uppercase letter!");
				request.getRequestDispatcher("/WEB-INF/profile/myAdminProfile.jsp").forward(request, response);
				return;	
			}
			
			String storeHash = profileDao.getAdminPasswordHashed(email);
			if(storeHash == null) {
				request.setAttribute("error", "Admin account not found!");
				System.out.println("[ERROR] Admin account not found!");
				request.getRequestDispatcher("/WEB-INF/profile/myAdminProfile.jsp").forward(request, response);
				return;	
			}
			
			if(BCrypt.checkpw(currentPassword, storeHash)) {
				String newHashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt(12));
				boolean passwordChange = profileDao.changeAdminPassword(email, newHashedPassword);
				
				if(passwordChange) {
					loadAdminProfileData(request, response);
					request.setAttribute("success", "Password change successfully!");
					System.out.println("[DEBUG] Password change successfully!");
				} else {
					loadAdminProfileData(request, response);
					request.setAttribute("error", "Failed to change password!");
					System.out.println("[ERROR] Failed to change password!");
				}
				
			} else {
				loadAdminProfileData(request, response);
				request.setAttribute("error", "Current password is incorrect!");
				System.out.println("[ERROR] Current password is incorrect!");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("[ERROR] Error changing password " + e.getMessage());
		}
		
		request.getRequestDispatcher("/WEB-INF/profile/myAdminProfile.jsp").forward(request, response);
		
	}
	
	private void listUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try {
	        System.out.println("[DEBUG] Starting listUsers method");
	        
	        List<Customer> customers = authenticationDao.getAllCustomers();
	        List<Staff> staffMembers = authenticationDao.getAllStaff();
	        List<Admin> administrator = authenticationDao.getAllAdmin();
	        
	        // Debug: Check what data we're getting
	        System.out.println("[DEBUG] Customers count: " + (customers != null ? customers.size() : "null"));
	        System.out.println("[DEBUG] Staff count: " + (staffMembers != null ? staffMembers.size() : "null"));
	        System.out.println("[DEBUG] Admin count: " + (administrator != null ? administrator.size() : "null"));
	        
	        // Debug: Print first few records if available
	        if (customers != null && !customers.isEmpty()) {
	            System.out.println("[DEBUG] First customer: " + customers.get(0).getName() + " - " + customers.get(0).getEmail());
	        }
	        if (staffMembers != null && !staffMembers.isEmpty()) {
	            System.out.println("[DEBUG] First staff: " + staffMembers.get(0).getName() + " - " + staffMembers.get(0).getEmail());
	        }
	        if (administrator != null && !administrator.isEmpty()) {
	            System.out.println("[DEBUG] First admin: " + administrator.get(0).getName() + " - " + administrator.get(0).getEmail());
	        }
	        
	        request.setAttribute("customers", customers);
	        request.setAttribute("staffMembers", staffMembers);
	        request.setAttribute("administrator", administrator);
	        
	        request.getRequestDispatcher("/WEB-INF/admin/list.jsp").forward(request, response);
	    } catch(Exception e) {
	        e.printStackTrace();
	        System.out.println("[ERROR] Can't retrieve list: " + e.getMessage());
	        request.setAttribute("error", "Failed to load user data: " + e.getMessage());
	        request.getRequestDispatcher("/WEB-INF/dashboard/adminDashboard.jsp").forward(request, response);
	    }
	}

}
