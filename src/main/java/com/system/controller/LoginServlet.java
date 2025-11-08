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

import org.mindrot.jbcrypt.BCrypt;

import com.system.utils.DBConnectionFactory;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AuthenticationDao authenticationDao;

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
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/admin/login.jsp").forward(request, response);
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String action = request.getParameter("action");
    	
    	try {
    		if(action == null) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing action parameter");
			}
    		switch(action) {
    		case "loginUser":
    			userLogin(request, response);
    			break;
    		case "logOutUser":
    			logoutUser(request, response);
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
                 "SELECT password, role, name FROM users WHERE email = ?")) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password");
                String role = rs.getString("role");
                String name = rs.getString("name");

                if (BCrypt.checkpw(password, storedHash)) {
                    HttpSession session = request.getSession();
                    session.setAttribute("username", email);
                    session.setAttribute("role", role);
                    session.setAttribute("name", name);
                 
                    if ("customer".equals(role)) {
                        session.setAttribute("customerName", name);
                    }
                    
                    if("admin".equals(role)) {
                    	session.setAttribute("adminName", name);
                    }
                    
                    if("staff".equals(role)) {
                    	session.setAttribute("staffName", name);
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