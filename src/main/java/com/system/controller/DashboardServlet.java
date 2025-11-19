package com.system.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		if(session == null || session.getAttribute("username") == null) {
			response.sendRedirect(request.getContextPath() + "/index.jsp");
			return;
		}
		
		String role = (String) session.getAttribute("role");
		String dashBoardPage = "";
		
		switch(role) {
		case "admin": 
			dashBoardPage = "/WEB-INF/dashboard/adminDashboard.jsp";
			break;
		case "staff": 
			dashBoardPage = "/WEB-INF/dashboard/staffDashboard.jsp";
			break;
		case "customer": 
			dashBoardPage = "/WEB-INF/dashboard/customerDashboard.jsp";
			break;
		default:
			response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
		}
		
		request.getRequestDispatcher(dashBoardPage).forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
}
