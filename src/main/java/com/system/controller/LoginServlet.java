package com.system.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.system.dao.AuthenticationDao;
import com.system.dao.PasswordResetDao;

import org.mindrot.jbcrypt.BCrypt;

import com.system.utils.DBConnectionFactory;
import com.system.utils.EmailUtil;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AuthenticationDao authenticationDao;
    private PasswordResetDao passwordResetDao;

    public LoginServlet() {
        super();
    }
    
    @Override
    public void init() {
        authenticationDao = new AuthenticationDao();
        if(authenticationDao != null) {
            System.out.println("[SUCCESS] AuthenticationDao initialized: " + (authenticationDao != null));
        } else {
            System.out.println("[ERROR] AuthenticationDao initialized failed!");
        }
        
        passwordResetDao = new PasswordResetDao();
        if(passwordResetDao != null) {
        	System.out.println("[SUCCESS] PasswordResetDao initialized: " + (passwordResetDao != null));
        } else {
        	System.out.println("[ERROR] PasswordResetDao initialized failed!");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        
        try {
        	
        	if(action == null) {
        		response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing action parameter");
        		return;
        	}
        	
        	switch(action) {
        	case "showForgotPasswordForm":
        		showForgotPasswordForm(request, response);
        		break;
        	case "showResetPasswordForm":
        		showResetPasswordForm(request, response);
        		break;
        	case "showLoginForm":
        		request.getRequestDispatcher("/WEB-INF/admin/login.jsp").forward(request, response);
        	    break;
        	default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "[ERROR] Invalid action!");
        	}
        	
        } catch(Exception e) {
			e.printStackTrace();
			System.out.println("[Error] An error occured while processing. please try again!");
			request.getRequestDispatcher("/WEB-INF/admin/login.jsp").forward(request, response);
        }
        
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String action = request.getParameter("action");
    	
    	try {
    		if(action == null) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing action parameter");
				return;
			}
    		switch(action) {
    		case "loginUser":
    			userLogin(request, response);
    			break;
    		case "logOutUser":
    			logoutUser(request, response);
    			break;
    		case "requestPasswordReset":
    			requestPasswordReset(request, response);
    			break;
    		case "resetPassword":
    			resetPassword(request, response);
    			break;
    		default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "[ERROR] Invalid action!");
    		}
    	}  catch(Exception e) {
			e.printStackTrace();
			System.out.println("[Error] An error occured while processing. please try again!");
			request.getRequestDispatcher("/WEB-INF/admin/login.jsp").forward(request, response);
		}
    	
    }
    
    private void showForgotPasswordForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.getRequestDispatcher("/WEB-INF/admin/forgotPassword.jsp").forward(request, response);
    }
    
    private void showResetPasswordForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
    	String token = request.getParameter("token");
    	
    	if(token == null || token.trim().isEmpty()) {
    		request.setAttribute("error", "Invalid or Missing token for password reset");
    		request.getRequestDispatcher("/WEB-INF/admin/login.jsp").forward(request, response);
    		return;
    	}
    	
    	try {
    		if(!passwordResetDao.isTokenValid(token)) {
    			request.setAttribute("error", "Invalid or expired reset token");
    			request.getRequestDispatcher("/WEB-INF/admin/login.jsp").forward(request, response);
    			return;
    		}
    		
    		request.setAttribute("token", token);
    		request.getRequestDispatcher("/WEB-INF/admin/resetPassword.jsp").forward(request, response);
    		
    	} catch(Exception e) {
    		e.printStackTrace();
    		request.setAttribute("error", "Database error" + e.getMessage());
    		request.getRequestDispatcher("/WEB-INF/admin/login.jsp").forward(request, response);
    	}
    	
    }
    
    private void requestPasswordReset(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String email = request.getParameter("email");
    	
    	if(email == null || email.trim().isEmpty()) {
    		request.setAttribute("error", "email is required");
    		request.getRequestDispatcher("/WEB-INF/admin/forgotPassword.jsp").forward(request, response);
    		return;
    	}
    	
    	try {
    		if(!passwordResetDao.isEmailExists(email)) {
    			request.setAttribute("success", "If your email is registered, you will receive a password reset link shortly.");
                request.getRequestDispatcher("/WEB-INF/admin/forgotPassword.jsp").forward(request, response);
                return;
    		}
    		
    		String token = passwordResetDao.genetateResetToken(email);
    		
    		if(token != null) {
    			String resetURL = request.getRequestURL().toString().replace("LoginServlet", "") + "LoginServlet?action=showResetPasswordForm&token=" + token;
    			
    			EmailUtil.sendPasswordResetEmail(email, resetURL);
    			
    			request.setAttribute("success", "Password reset email has been sent to your email.");
    			System.out.println("[SUCCESS] Password reset token generated for: " + email);
    		} else {
    			request.setAttribute("error", "Failed to generate reset token. Please try again.");
    		}
    		
    	} catch(Exception e) {
    		e.printStackTrace();
            request.setAttribute("error", "Error processing password reset request: " + e.getMessage());
    	}
    	
    	 request.getRequestDispatcher("/WEB-INF/admin/forgotPassword.jsp").forward(request, response);
    }
    
    private void resetPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String token = request.getParameter("token");
    	String newPassword = request.getParameter("newPassword");
    	String confirmPassword = request.getParameter("confirmPassword");
    	
    	if(token == null || newPassword == null || confirmPassword == null) {
    		request.setAttribute("error", "All fields are required");
    		request.getRequestDispatcher("/WEB-INF/admin/resetPassword.jsp").forward(request, response);
    		return;
    	}
    	
    	if(!newPassword.equals(confirmPassword)) {
    		request.setAttribute("error", "Password do not match");
    		request.setAttribute("token", token);
    		request.getRequestDispatcher("/WEB-INF/admin/resetPassword.jsp").forward(request, response);
    		return;
    	}
    	
    	String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";
    	if(!newPassword.matches(passwordPattern)) {
    		request.setAttribute("error", "Password must contain at least 8 characters, including uppercase, lowercase letters and numbers");
    		request.setAttribute("token", token);
    		request.getRequestDispatcher("/WEB-INF/admin/resetPassword.jsp").forward(request, response);
    		return;
    	}
    	
    	try {
    		String email = passwordResetDao.getEmailByToken(token);
    		
    		if(email == null) {
    			request.setAttribute("error", "Invalid or expired reset token");
                request.getRequestDispatcher("/WEB-INF/admin/login.jsp").forward(request, response);
                return;
    		}
    		
    		String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt(12));
    		
    		boolean updatePassword = passwordResetDao.updatePassword(email, hashedPassword);
    		
    		if(updatePassword) {
    			passwordResetDao.makeTokenAsUsed(token);
    			
    			String name = passwordResetDao.getUserByEmail(email);
    			EmailUtil.sendPasswordResetSuccessEmail(email, name);
    			
    			request.setAttribute("success", "Password has been reset successfully. You can now login with your new password.");
                System.out.println("[SUCCESS] Password reset for: " + email);
    			
    		} else {
    			request.setAttribute("error", "Failed to reset password. Please try again.");
    		}
    		
    	} catch(Exception e) {
    		e.printStackTrace();
            request.setAttribute("error", "Error resetting password: " + e.getMessage());
            request.setAttribute("token", token);
            request.getRequestDispatcher("/WEB-INF/admin/resetPassword.jsp").forward(request, response);
            return;
    	}
    	
    	 request.getRequestDispatcher("/WEB-INF/admin/login.jsp").forward(request, response);
    }
    
    private void userLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || password == null || email.trim().isEmpty() || password.trim().isEmpty()) {
            request.setAttribute("error", "Email and password are required");
            request.getRequestDispatcher("/WEB-INF/admin/login.jsp").forward(request, response);
            return;
        }

        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                 "SELECT password, role, name, staff_id, customer_id, admin_id FROM users WHERE email = ?")) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
            	String storedHash = rs.getString("password");
                String role = rs.getString("role");
                String name = rs.getString("name");
                String staffId = rs.getString("staff_id");
                String customerId = rs.getString("customer_id");
                String adminId = rs.getString("admin_id");

                if (BCrypt.checkpw(password, storedHash)) {
                    HttpSession session = request.getSession();
                    session.setAttribute("username", email);
                    session.setAttribute("email", email);
                    session.setAttribute("role", role);
                    session.setAttribute("name", name);
                 
                    if ("customer".equals(role)) {
                        session.setAttribute("customerName", name);
                        session.setAttribute("customerId", customerId);
                    }
                    
                    if("admin".equals(role)) {
                    	session.setAttribute("adminName", name);
                    	session.setAttribute("adminId", adminId);
                    }
                    
                    if("staff".equals(role)) {
                    	session.setAttribute("staffName", name);
                    	session.setAttribute("staffId", staffId);
                    }
                    
                    String redirectUrl = request.getContextPath() + "/DashboardServlet";

                    // For iframe break-out - return HTML with JavaScript to break out of iframe
                    response.setContentType("text/html");
                    String breakOutScript = 
                        "<html>" +
                        "<head>" +
                        "<script type='text/javascript'>" +
                        "window.top.location.href = '" + redirectUrl + "';" +
                        "</script>" +
                        "</head>" +
                        "<body>" +
                        "<noscript>Login successful! <a href='" + redirectUrl + "'>Click here to continue</a></noscript>" +
                        "</body>" +
                        "</html>";
                    response.getWriter().write(breakOutScript);
                    
                } else {
                    request.setAttribute("error", "Invalid password");
                    request.getRequestDispatcher("/WEB-INF/admin/login.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("error", "User not found");
                request.getRequestDispatcher("/WEB-INF/admin/login.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/admin/login.jsp").forward(request, response);
        }

    }
    
    private void logoutUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);
        
        if(session != null) {
            String role = (String) session.getAttribute("role");
            session.invalidate();
            
            // Redirect based on role
            if("customer".equals(role)) {
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }
}