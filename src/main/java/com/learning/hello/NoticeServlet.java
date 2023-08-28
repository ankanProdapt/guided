package com.learning.hello;

import java.io.IOException;
import java.sql.SQLException;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import com.learning.hello.contoller.NoticeboardController;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/notices")
public class NoticeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private NoticeboardController nc = new NoticeboardController();
	private JakartaServletWebApplication application;
	private TemplateEngine templateEngine;

	static {

		try {

			// Load the MySQL JDBC driver

			Class.forName("com.mysql.cj.jdbc.Driver");

		} catch (ClassNotFoundException e) {

			e.printStackTrace();

		}

	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		application = JakartaServletWebApplication.buildApplication(getServletContext());
		final WebApplicationTemplateResolver templateResolver = new WebApplicationTemplateResolver(application);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setPrefix("/WEB-INF/templates/");
		templateResolver.setSuffix(".html");
		templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var out = resp.getWriter();
		final IWebExchange webExchange = this.application.buildExchange(req, resp);
		final WebContext ctx = new WebContext(webExchange);

		String btn = req.getParameter("btn");

		if (btn != null) {
			String title = req.getParameter("title");
			String content = req.getParameter("content");
			String name = req.getParameter("name");
			String phone = req.getParameter("phone");

			try {
				nc.addNotice(title, content, name, phone);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Error!!!");
			}
		}

		try {
			ctx.setVariable("notices", nc.getNotices());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		templateEngine.process("notices", ctx, out);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		final IWebExchange webExchange = this.application.buildExchange(req, resp);
		final WebContext ctx = new WebContext(webExchange);
		templateEngine.process("notices", ctx, resp.getWriter());
	}

}