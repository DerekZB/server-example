package com.netease.server.example.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 *
 */
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 4607606190625660785L;

	/**
	 * Logger for this class.
	 */
	private static Logger logger = Logger.getLogger(UserServlet.class);

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		process(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.info("UserServlet post method is invoked.");
		response.setContentType("text/html;charset=UTF-8");
		process(request, response);
	}

	protected void process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = null;
		String user = request.getParameter("user");
		String password = request.getParameter("password");

		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("user");

		if (name != null) {
			session.invalidate();
			if (!name.equals(user))
			{
				System.out.println("second login: " + name);
			}
		}

		session.setAttribute("user", user);

		Cookie userNameCookie = new Cookie("user", user);
		Cookie pwdCookie = new Cookie("password", password);

		userNameCookie.setMaxAge(30 * 60);
		pwdCookie.setMaxAge(30 * 60);

		response.addCookie(userNameCookie);
		response.addCookie(pwdCookie);

		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("user")) {
					user = cookie.getValue();
				}
				if (cookie.getName().equals("password")) {
					password = cookie.getValue();
				}
			}
		}

		try {
			if (user.equals("123") && password.equals("123")) {
				PrintWriter writer = response.getWriter();
				writer.println("<html>");
				writer.println("<head><title>用户中心</title></head>");
				writer.println("<body>");
				writer.println("<p>用户名：" + user + "</p>");
				writer.println("<p>用户密码：" + password + "</p>");
				writer.println("</body>");
				writer.println("</html>");
				writer.close();
			} else {
				dispatcher = request.getRequestDispatcher("/error.html");
				dispatcher.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			dispatcher = request.getRequestDispatcher("/error.html");
			dispatcher.forward(request, response);
		}

	}
	
	protected void login(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String user = request.getParameter("user");
		String password = request.getParameter("password");

		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("user");

		if (name != null) {
			session.invalidate();
			if (!name.equals(user))
			{
				System.out.println("second login: " + name);
			}
		}
		
		session.setAttribute("user", user);

		Cookie userNameCookie = new Cookie("user", user);
		Cookie pwdCookie = new Cookie("password", password);

		userNameCookie.setMaxAge(30 * 60);
		pwdCookie.setMaxAge(30 * 60);

		response.addCookie(userNameCookie);
		response.addCookie(pwdCookie);
	}
}
