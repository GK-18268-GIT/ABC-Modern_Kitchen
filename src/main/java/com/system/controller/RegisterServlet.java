package com.system.controller;

import java.io.IOException;
import java.io.File;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import java.sql.Timestamp;
import org.mindrot.jbcrypt.BCrypt;

import com.system.model.Admin;
import com.system.model.Staff;
import com.system.utils.EmailUtil;
import com.system.model.Customer;
import com.system.dao.AuthenticationDao;

@MultipartConfig(maxFileSize = 1024 * 1024 * 5, // 5MB max file size
                 maxRequestSize = 1024 * 1024 * 10) // 10MB max request size
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String UPLOAD_DIR = "assets/profiles/";
    private AuthenticationDao authenticationDao;

    public RegisterServlet() {
        super();
    }

    @Override
    public void init() {
        authenticationDao = new AuthenticationDao();
        if (authenticationDao != null) {
            System.out.println("[SUCCESS] AuthenticationDao initialized: " + (authenticationDao != null));
        } else {
            System.out.println("[ERROR] AuthenticationDao initialization failed!");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "[ERROR] Missing action parameter");
            return;
        }

        try {
            switch (action) {
                case "staffRegister":
                    request.getRequestDispatcher("/WEB-INF/admin/registerStaff.jsp").forward(request, response);
                    break;
                case "login":
                    request.getRequestDispatcher("/WEB-INF/admin/login.jsp").forward(request, response);
                    break;
                case "adminRegister":
                	request.getRequestDispatcher("/WEB-INF/admin/registerAdmin.jsp").forward(request, response);
                	break;
                case "customerRegister":
                	request.getRequestDispatcher("/WEB-INF/customer/registerCustomer.jsp").forward(request, response);
                	break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "[ERROR] Invalid action!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[Error] An error occurred while processing. Please try again!");
            request.getRequestDispatcher("/WEB-INF/customer/registerCustomer.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "[ERROR] Missing action parameter");
            return;
        }
        
        if(!"customerRegister".equals(action)) {
        	HttpSession session = request.getSession(false);
        	if(session == null || "admin".equals(session.getAttribute("role"))) {
        		response.sendRedirect("access-denied.jsp");
        		return;
        	}
        }

        try {
            switch (action) {
                case "staffRegister":
                    addNewStaffMember(request, response);
                    break;
                case "adminRegister":
                	addNewAdmin(request, response);
                	break;
                case "customerRegister":
                	addNewCustomer(request, response);
                	break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[Error] An error occurred while processing. Please try again!");
            request.setAttribute("error", "An error occurred. Please try again.");
            
            if("customerRegister".equals(action)) {
            	request.getRequestDispatcher("/WEB-INF/customer/registerCustomer.jsp").forward(request, response);
            } else {
            	request.getRequestDispatcher("/WEB-INF/customer/registerStaff.jsp").forward(request, response); 	
            }
        }
    }
    
    private String generateTempPassword() {
    	String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%";
    	StringBuilder sb = new StringBuilder();
    	java.util.Random random = new java.util.Random();
    	
    	sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(random.nextInt(26)));
        sb.append("abcdefghijklmnopqrstuvwxyz".charAt(random.nextInt(26)));
        sb.append("0123456789".charAt(random.nextInt(10)));
        sb.append("!@#$%".charAt(random.nextInt(5)));
        
        for(int i = 4; i < 12; i++ ) {
        	sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        
        char[] passwordArray = sb.toString().toCharArray();
        for(int i = passwordArray.length - 1; i > 0; i--) {
        	int j = random.nextInt(i + 1);
        	char temp = passwordArray[i];
        	passwordArray[i] = passwordArray[j];
        	passwordArray[j] = temp;
        }
        
        return new String(passwordArray);
    }

    private void addNewStaffMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phone-number");

        String applicationPath = request.getServletContext().getRealPath("");
        String filePath = null;

        // Handle file upload
        Part filePart = request.getPart("profile-picture");
        String fileName = filePart.getSubmittedFileName();
        if (filePart != null && filePart.getSize() > 0) {
            String extension = fileName.substring(fileName.lastIndexOf("."));
            fileName = System.currentTimeMillis() + "_" + name.replace(" ", "_") + extension;
            filePath = UPLOAD_DIR + fileName;

            File uploadFile = new File(applicationPath + File.separator + filePath);
            uploadFile.getParentFile().mkdirs();
            filePart.write(uploadFile.getAbsolutePath());
        }

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        String emailPattern = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";

        if (name == null || email == null || address == null || phoneNumber == null || filePath == null) {
            request.setAttribute("error", "Required fields are missing.");
            request.getRequestDispatcher("/WEB-INF/admin/registerStaff.jsp").forward(request, response);
            return;
        }

        if (!email.matches(emailPattern)) {
            request.setAttribute("error", "Invalid email format");
            request.getRequestDispatcher("/WEB-INF/admin/registerStaff.jsp").forward(request, response);
            return;
        }
        
        String tempPassword = generateTempPassword();
        String hashedPassword = BCrypt.hashpw(tempPassword, BCrypt.gensalt(12));
        
        Staff staff = new Staff(name, filePath, email, hashedPassword, address, phoneNumber, null, currentTime, currentTime);

        boolean isSuccess = authenticationDao.addNewStaffMember(staff, hashedPassword);
        if (isSuccess) {
        	
        	EmailUtil.sendCredentials(email, name, tempPassword, "staff");
        	request.setAttribute("success", "Staff member registered successfully! Credentials sent via email.");
        	request.getRequestDispatcher("/WEB-INF/admin/staffList.jsp").forward(request, response);
        } else {
        	request.setAttribute("error", "Registration failed. Email may already exist.");
            if (filePath != null) {
                new File(applicationPath + File.separator + filePath).delete(); // Cleanup on failure
            }
        }

        request.getRequestDispatcher("/WEB-INF/admin/registerStaff.jsp").forward(request, response);

        System.out.println("[DEBUG] Received Parameters:");
        System.out.println("[DEBUG] Name: " + name);
        System.out.println("[DEBUG] Address: " + address);
        System.out.println("[DEBUG] Email: " + email);
        System.out.println("[DEBUG] Phone Number: " + phoneNumber);
        System.out.println("[DEBUG] Profile Picture Path: " + filePath);
    }
    
    private void addNewAdmin(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String name = request.getParameter("name");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phone-number");

        String applicationPath = request.getServletContext().getRealPath("");
        String filePath = null;

        // Handle file upload
        Part filePart = request.getPart("profile-picture");
        String fileName = filePart.getSubmittedFileName();
        if (filePart != null && filePart.getSize() > 0) {
            String extension = fileName.substring(fileName.lastIndexOf("."));
            fileName = System.currentTimeMillis() + "_" + name.replace(" ", "_") + extension;
            filePath = UPLOAD_DIR + fileName;

            File uploadFile = new File(applicationPath + File.separator + filePath);
            uploadFile.getParentFile().mkdirs();
            filePart.write(uploadFile.getAbsolutePath());
        }

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        String emailPattern = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";

        if (name == null || email == null || address == null || phoneNumber == null || filePath == null) {
            request.setAttribute("error", "Required fields are missing.");
            request.getRequestDispatcher("/WEB-INF/admin/registerStaff.jsp").forward(request, response);
            return;
        }

        if (!email.matches(emailPattern)) {
            request.setAttribute("error", "Invalid email format");
            request.getRequestDispatcher("/WEB-INF/admin/registerStaff.jsp").forward(request, response);
            return;
        }

        String tempPassword = generateTempPassword();
        String hashedPassword = BCrypt.hashpw(tempPassword, BCrypt.gensalt(12));
        
        Admin admin = new Admin(name, filePath, email, hashedPassword, address, phoneNumber, null, currentTime, currentTime);

        boolean isSuccess = authenticationDao.addNewAdmin(admin, hashedPassword);
        if (isSuccess) {
        	EmailUtil.sendCredentials(email, name, tempPassword, "admin");
        	request.setAttribute("success", "Admin registered successfully! Credentials sent via email.");
        	request.getRequestDispatcher("/WEB-INF/admin/adminList.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Registration failed. Email may already exist.");
            if (filePath != null) {
                new File(applicationPath + File.separator + filePath).delete(); // Cleanup on failure
            }

        }
	    request.getRequestDispatcher("/WEB-INF/admin/registerAdmin.jsp").forward(request, response);
	
	    System.out.println("[DEBUG] Received Parameters:");
	    System.out.println("[DEBUG] Name: " + name);
	    System.out.println("[DEBUG] Address: " + address);
	    System.out.println("[DEBUG] Email: " + email);
	    System.out.println("[DEBUG] Phone Number: " + phoneNumber);
	    System.out.println("[DEBUG] Profile Picture Path: " + filePath);
    
    }
    
    private void addNewCustomer(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String name = request.getParameter("name");
    	String address = request.getParameter("address");
    	String email = request.getParameter("email");
    	String phoneNumber = request.getParameter("phone-number");
    	String password = request.getParameter("password");
    	String confirmPassword = request.getParameter("confirmpassword");
    	
    	String applicationPath = request.getServletContext().getRealPath("");
    	String filePath = null;
    	
    	// Handle file upload
    	Part filePart = request.getPart("profile-picture");
    	String fileName = filePart.getSubmittedFileName();
    	if (filePart != null && filePart.getSize() > 0) {
    		String extension = fileName.substring(fileName.lastIndexOf("."));
    		fileName = System.currentTimeMillis() + "_" + name.replace(" ", "_") + extension;
    		filePath = UPLOAD_DIR + fileName;
    		
    		File uploadFile = new File(applicationPath + File.separator + filePath);
    		uploadFile.getParentFile().mkdirs();
    		filePart.write(uploadFile.getAbsolutePath());
    	}
    	
    	Timestamp currentTime = new Timestamp(System.currentTimeMillis());
    	
    	String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";
    	String emailPattern = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";
    	
    	if (name == null || email == null || password == null || address == null || phoneNumber == null || filePath == null) {
    		request.setAttribute("error", "Required fields are missing.");
    		request.getRequestDispatcher("/WEB-INF/admin/registerCustomer.jsp").forward(request, response);
    		return;
    	}
    	
    	if (!email.matches(emailPattern)) {
    		request.setAttribute("error", "Invalid email format");
    		request.getRequestDispatcher("/WEB-INF/customer/registerCustomer.jsp").forward(request, response);
    		return;
    	}
    	
    	if (!password.matches(passwordPattern)) {
    		request.setAttribute("error", "Password must contain at least 8 characters, including a number and an uppercase letter!");
    		request.getRequestDispatcher("/WEB-INF/customer/registerCustomer.jsp").forward(request, response);
    		return;
    	}
    	
    	if (!password.equals(confirmPassword)) {
    		request.setAttribute("error", "Passwords do not match!");
    		request.getRequestDispatcher("/WEB-INF/customer/registerCustomer.jsp").forward(request, response);
    		return;
    	}
    	
    	String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
    	Customer customer = new Customer(name, filePath, email, hashedPassword, address, phoneNumber, null, currentTime, currentTime);
    	
    	boolean isSuccess = authenticationDao.addNewCustomer(customer, hashedPassword);
    	if (isSuccess) {
    		
    		HttpSession session = request.getSession();
        	session.setAttribute("username", email);
        	session.setAttribute("role", "customer");
        	session.setAttribute("customer", customer);
        	session.setAttribute("customerName", name);	
    		
    		EmailUtil.sendWelcomeEmail(email, name, "customer");
    		request.setAttribute("success", "Customer registered successfully! Credentials sent via email.");
    		response.sendRedirect(request.getContextPath() + "/DashboardServlet?role=customer");
    		return;
    	} else {
    		request.setAttribute("error", "Registration failed. Email may already exist.");
    		if (filePath != null) {
    			new File(applicationPath + File.separator + filePath).delete(); // Cleanup on failure
    		}
    		
    	}
    	request.getRequestDispatcher("/WEB-INF/dashboard/registerCustomer.jsp").forward(request, response);
    	
    	System.out.println("[DEBUG] Received Parameters:");
    	System.out.println("[DEBUG] Name: " + name);
    	System.out.println("[DEBUG] Address: " + address);
    	System.out.println("[DEBUG] Email: " + email);
    	System.out.println("[DEBUG] Phone Number: " + phoneNumber);
    	System.out.println("[DEBUG] Profile Picture Path: " + filePath);
    	
    }
    
}

