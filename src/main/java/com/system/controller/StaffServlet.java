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

import com.system.model.Customer;
import com.system.dao.AuthenticationDao;
import com.system.model.Dish;
import com.system.model.Staff;
import com.system.utils.EmailUtil;
import com.system.dao.DishDao;
import com.system.dao.ProfileDao;
import com.system.model.DineInReservation;
import com.system.dao.DineInReservationDao;

@MultipartConfig(
	    maxFileSize = 1024 * 1024 * 5, // 5MB
	    maxRequestSize = 1024 * 1024 * 10 // 10MB
	)

public class StaffServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIR = "assets/dishes/";
	private DishDao dishDao;
	private DineInReservationDao dineInReservationDao;
	private AuthenticationDao authenticationDao;
	private ProfileDao profileDao;

    public StaffServlet() {
        super();
       
    }
    
    public void init() {
    	dishDao = new DishDao();
        if (dishDao != null) {
            System.out.println("[SUCCESS] DishDao initialized in StaffServlet: " + (dishDao != null));
        } else {
            System.out.println("[ERROR] DishDao initialization failed in StaffServlet!");
        }
        
        dineInReservationDao = new DineInReservationDao();
        if (dishDao != null) {
        	System.out.println("[SUCCESS] DineInReservationDao initialized in StaffServlet: " + (dineInReservationDao != null));
        } else {
        	System.out.println("[ERROR] DineInReservationDao initialization failed in StaffServlet!");
        }
        
        authenticationDao = new AuthenticationDao();
        if (authenticationDao != null) {
        	System.out.println("[SUCCESS] AuthenticationDao initialized in StaffServlet: " + (authenticationDao != null));
        } else {
        	System.out.println("[ERROR] AuthenticationDao initialization failed in StaffServlet!");
        }
        
        profileDao = new ProfileDao();
        if (profileDao != null) {
        	System.out.println("[SUCCESS] ProfileDao initialized in StaffServlet: " + (profileDao != null));
        } else {
        	System.out.println("[ERROR] ProfileDao initialization failed in StaffServlet!");
        }
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
        
        HttpSession session = request.getSession();
        String staffName = (String) session.getAttribute("staffName");
        
        if(staffName == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet?action=login");
            System.out.println("[DEBUG] Staff doesn't exist!");
            return;
        }
        
        if(action == null) {
            response.sendRedirect(request.getContextPath() + "/DashboardServlet");
            System.out.println("[DEBUG] Invalid action!!");
            return;
        }
        
        try {
            switch(action) {
                case "manageDishes":
                    manageDishes(request, response);
                    break;
                case "editDish":
                	showEditDishForm(request, response);
                	break;
                case "confirmReservation":
                	getCustomerReservations(request, response);
                	break;
                case "myStaffProfile":
                	myStaffProfile(request, response);
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
        
        HttpSession session = request.getSession();
        String staffName = (String) session.getAttribute("staffName");
        
        if(staffName == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet?action=login");
            System.out.println("[DEBUG] Staff doesn't exist!");
            return;
        }
        
        if(action == null) {
            response.sendRedirect(request.getContextPath() + "/DashboardServlet");
            System.out.println("[DEBUG] Invalid action!!");
            return;
        }
        
        try {
            switch(action) {
                case "addDishes":
                    addDish(request, response);
                    break;
                case "updateDishes":
                	updateDish(request, response);
                	break;
                case "deleteDishes":
                	deleteDish(request, response);
                	break;
                case "updateReservationStatus":
                	updateReservationStatus(request, response);
                	break;
                case "updateStaffProfile":
                	updateStaffProfile(request, response);
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

	
	private void manageDishes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			List<Dish> dishes = dishDao.getAllDishes();
			List<String> categories = dishDao.getAllCategories();
			request.setAttribute("dishes", dishes);
			request.setAttribute("categories", categories);
			request.getRequestDispatcher("/WEB-INF/staff/manageDishes.jsp").forward(request, response);
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Failed to load dishes" + e.getMessage());
			request.getRequestDispatcher("WEB-INF/staff/manageDishes.jsp").forward(request, response);
			System.out.println("[ERROR] Failed to load dishes");
			
		}
	}
	
	private void myStaffProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		loadStaffProfileData(request, response);
		request.getRequestDispatcher("/WEB-INF/profile/myStaffProfile.jsp").forward(request, response);
	}
	
	private void updateStaffProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
	    System.out.println("[DEBUG] updateStaffProfile - Email: " + email);
	    System.out.println("[DEBUG] updateStaffProfile - Name: " + name);
	    System.out.println("[DEBUG] updateStaffProfile - Address: " + address);
	    System.out.println("[DEBUG] updateStaffProfile - Phone: " + phoneNumber);
	    
	    // Remove space from phone number for server-side validation
	    String phoneNumberClean = phoneNumber != null ? phoneNumber.replace(" ", "") : null;
	    
	    // Server-side phone number validation (without space)
	    String phonePattern = "^\\+94[0-9]{9}$";
	    if (phoneNumberClean != null && !phoneNumberClean.matches(phonePattern)) {
	        loadStaffProfileData(request, response);
	        request.setAttribute("error", "Invalid phone number format. Must be +94 followed by a space and 9 digits (e.g., +94 771234567)");
	        request.getRequestDispatcher("/WEB-INF/profile/myStaffProfile.jsp").forward(request, response);
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
	                
	                boolean pictureUpdated = profileDao.updateStaffProfilePicture(email, filePath);
	                
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
	        boolean profileUpdated = profileDao.updateStaffProfile(email, name, address, phoneNumberClean);
	        
	        if(profileUpdated) {
	            // Reload the updated profile data
	            loadStaffProfileData(request, response);
	            request.setAttribute("success", "Profile updated successfully!");
	            System.out.println("[DEBUG] Profile updated successfully for: " + email);
	        } else {
	            // Still load current data even if update failed
	            loadStaffProfileData(request, response);
	            request.setAttribute("error", "Failed to update profile!");
	            System.out.println("[ERROR] Failed to update profile for: " + email);
	        }
	        
	    } catch(Exception e) {
	        e.printStackTrace();
	        // Load current data on error
	        loadStaffProfileData(request, response);
	        request.setAttribute("error", "Error updating profile: " + e.getMessage());
	        System.out.println("[ERROR] Error updating profile! " + e.getMessage());
	    }
	        
	    request.getRequestDispatcher("/WEB-INF/profile/myStaffProfile.jsp").forward(request, response);
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
        		loadStaffProfileData(request, response);
        		request.setAttribute("error", "new password and confirm password do not match!");
        		request.getRequestDispatcher("/WEB-INF/profile/myStaffProfile.jsp").forward(request, response);
        		return;
        	}
        	
        	String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";
			if(!newPassword.matches(passwordPattern)) {
				loadStaffProfileData(request, response);
				request.setAttribute("error", "Password must contain at least 8 characters, including a number and an uppercase letter!");
				request.getRequestDispatcher("/WEB-INF/profile/myStaffProfile.jsp").forward(request, response);
				return;	
			}
			
			String storeHash = profileDao.getStaffPasswordHashed(email);
			if(storeHash == null) {
				request.setAttribute("error", "Staff account not found!");
				System.out.println("[ERROR] Staff account not found!");
				request.getRequestDispatcher("/WEB-INF/profile/myStaffProfile.jsp").forward(request, response);
				return;	
			}
			
			if(BCrypt.checkpw(currentPassword, storeHash)) {
				String newHashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt(12));
				boolean passwordChange = profileDao.changeStaffPassword(email, newHashedPassword);
				
				if(passwordChange) {
					loadStaffProfileData(request, response);
					request.setAttribute("success", "Password change successfully!");
					System.out.println("[DEBUG] Password change successfully!");
				} else {
					loadStaffProfileData(request, response);
					request.setAttribute("error", "Failed to change password!");
					System.out.println("[ERROR] Failed to change password!");
				}
				
			} else {
				loadStaffProfileData(request, response);
				request.setAttribute("error", "Current password is incorrect!");
				System.out.println("[ERROR] Current password is incorrect!");
			}
        	
        } catch(Exception e) {
        	e.printStackTrace();
			System.out.println("[ERROR] Error changing password " + e.getMessage());
        }
		
	}
	
	private void showEditDishForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			Dish dish = dishDao.getDishById(id);
			List<String> categories = dishDao.getAllCategories();
			
			if(dish != null) {
				request.setAttribute("dish", dish);
				request.setAttribute("categories", categories);
				request.getRequestDispatcher("/WEB-INF/staff/editDishes.jsp").forward(request, response);
			} else {
				request.setAttribute("error", "Dish not found");
				System.out.println("[ERROR] Dish not found");
			}
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Failed to load dish" + e.getMessage());
			System.out.println("[ERROR] Failed to load dish");
			manageDishes(request, response);
		}
	}
	
	private void addDish(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String category = request.getParameter("category");
			String name = request.getParameter("name");
			String sizeType = request.getParameter("sizeType");
			boolean isAvailable= request.getParameter("isAvailable") != null;
			
			Dish dish;
			
			if("both".equals(sizeType)) {
				//Both sizes
				double priceN = Double.parseDouble(request.getParameter("priceN"));
				double priceL = Double.parseDouble(request.getParameter("priceL"));
				dish = new Dish(category, name, sizeType, priceN, priceL, null, isAvailable);
			} else {
				//Single size
				String size = request.getParameter("size");
				double price = Double.parseDouble(request.getParameter("price"));
				dish = new Dish(category, name, size, price, null, isAvailable);
			}
			
			//Hangle image upload
			String imagePath = null;
			Part filePart = request.getPart("dishImage");
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = getFileName(filePart);
                String applicationPath = request.getServletContext().getRealPath("");
                String uploadPath = applicationPath + File.separator + UPLOAD_DIR;
                
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                
                String fileExtension = fileName.substring(fileName.lastIndexOf("."));
                String newFileName = System.currentTimeMillis() + "_" + name.replace(" ", "_") + fileExtension;
                imagePath = UPLOAD_DIR + newFileName;
                
                filePart.write(uploadPath + File.separator + newFileName);
            }
            
            dish.setImagePath(imagePath);
            boolean success = dishDao.addDish(dish);
            
            if (success) {
                request.setAttribute("success", "Dish added successfully!");
            } else {
                request.setAttribute("error", "Failed to add dish");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error adding dish: " + e.getMessage());
        }
        
        manageDishes(request, response);
			
	}
	
	private void updateDish(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String category = request.getParameter("category");
            String name = request.getParameter("name");
            String sizeType = request.getParameter("sizeType"); // "single" or "both"
            boolean isAvailable = request.getParameter("isAvailable") != null;
            
            Dish existingDish = dishDao.getDishById(id);
            if (existingDish == null) {
                request.setAttribute("error", "Dish not found");
                manageDishes(request, response);
                return;
            }
            
            Dish dish;
            
            if ("both".equals(sizeType)) {
                // Both sizes selected
                double priceN = Double.parseDouble(request.getParameter("priceN"));
                double priceL = Double.parseDouble(request.getParameter("priceL"));
                dish = new Dish(id, category, name, "Both", priceN, priceL, existingDish.getImagePath(), isAvailable);
            } else {
                // Single size selected
                String size = request.getParameter("size");
                double price = Double.parseDouble(request.getParameter("price"));
                dish = new Dish(id, category, name, size, price, null, existingDish.getImagePath(), isAvailable);
            }
            
            // Handle image upload
            String imagePath = existingDish.getImagePath();
            Part filePart = request.getPart("dishImage");
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = getFileName(filePart);
                String applicationPath = request.getServletContext().getRealPath("");
                String uploadPath = applicationPath + File.separator + UPLOAD_DIR;
                
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                
                String fileExtension = fileName.substring(fileName.lastIndexOf("."));
                String newFileName = System.currentTimeMillis() + "_" + name.replace(" ", "_") + fileExtension;
                imagePath = UPLOAD_DIR + newFileName;
                
                filePart.write(uploadPath + File.separator + newFileName);
            }
            
            dish.setImagePath(imagePath);
            boolean success = dishDao.updateDish(dish);
            
            if (success) {
                request.setAttribute("success", "Dish updated successfully!");
            } else {
                request.setAttribute("error", "Failed to update dish");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error updating dish: " + e.getMessage());
        }
        
        manageDishes(request, response);
    }
    
    private void deleteDish(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int dishId = Integer.parseInt(request.getParameter("dishId"));
            boolean success = dishDao.deleteDish(dishId);
            
            if (success) {
                request.setAttribute("success", "Dish deleted successfully!");
            } else {
                request.setAttribute("error", "Failed to delete dish");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error deleting dish: " + e.getMessage());
        }
        
        manageDishes(request, response);
    }
	
	private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return "";
    }
	
	private void getCustomerReservations(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			String staffId = (String) session.getAttribute("staffId");
			
			if(staffId == null) {
				request.setAttribute("error", "Staff ID not found in session");
	            request.getRequestDispatcher("/WEB-INF/dashboard/staffDashboard.jsp").forward(request, response);
	            return;
			}

			List<DineInReservation> assignedDineInReservations = dineInReservationDao.getReservationByStaff(staffId);
			List<Customer> customers = authenticationDao.getAllCustomers();
			
			request.setAttribute("dineInReservations", assignedDineInReservations);
			request.setAttribute("customers", customers);
			request.getRequestDispatcher("/WEB-INF/staff/confirmReservation.jsp").forward(request, response);
		} catch(Exception e) {
			e.printStackTrace();
            request.setAttribute("error", "Failed to load reservations: " + e.getMessage());
            System.out.println("[ERROR] Failed to load reservations: " + e.getMessage());
			request.getRequestDispatcher("/WEB-INF/dashboard/staffDashboard.jsp").forward(request, response);
		}

	}
	
	private void updateReservationStatus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int reservationId = Integer.parseInt(request.getParameter("reservationId"));
			String status= request.getParameter("status");
			
			System.out.println("[DEBUG] Updating reservation ID: " + reservationId + " to status: " + status);
			
			DineInReservation dineInReservation = dineInReservationDao.getReservationsById(reservationId);
			if(dineInReservation != null) {
				dineInReservation.setStatus(status);
				boolean isSuccess = dineInReservationDao.updateReservation(dineInReservation);
				
				if(isSuccess) {
					
					try {
						EmailUtil.sendReservationStatusUpdateToCustomer(dineInReservation);
						System.out.println("[SUCCESS] Status update email sent to customer");
					} catch(Exception e) {
						 System.out.println("[ERROR] Failed to send status update email: " + e.getMessage());
					}
					
					request.setAttribute("success", "Reservation status updated successfully to: " + status);
					System.out.println("[SUCCESS] Reservation status updated to: " + status);
				} else {
					request.setAttribute("error", "Failed to update reservation status");
					System.out.println("[ERROR] Failed to update reservation status");
				}
				
			} else {
				request.setAttribute("error", "Reservation not found");
				System.out.println("[ERROR] Reservation not found with ID: " + reservationId);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Error updating reservation: " + e.getMessage());
			System.out.println("[ERROR] Error updating reservation: " + e.getMessage());
		}
		 getCustomerReservations(request, response);
	}
	
	private void loadStaffProfileData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			String email = (String) session.getAttribute("email");
			
			if(email != null) {
				Staff staff = profileDao.getStaffById(email);
					if(staff != null) {
						request.setAttribute("staffName", staff.getName());
						request.setAttribute("staffEmail", staff.getEmail());
						request.setAttribute("staffAddress", staff.getAddress());
						request.setAttribute("staffPhone", staff.getPhoneNumber());
						request.setAttribute("staffProfilePic", staff.getProfilePicture());
						
						session.setAttribute("staffName", staff.getName());
						System.out.println("[DEBUG] Load staff profile data for: " + email);
						
					} else {
						System.out.println("[DEBUG] Failed to load staff profile data for: " + email);
					}
				}
			
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("[ERROR] No email in the session! " + e.getMessage());
		}
	}
}
